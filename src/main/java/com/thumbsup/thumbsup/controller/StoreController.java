package com.thumbsup.thumbsup.controller;

import com.thumbsup.thumbsup.dto.StoreDTO;
import com.thumbsup.thumbsup.dto.StoreExtraDTO;
import com.thumbsup.thumbsup.service.interfaces.IStoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name = "Store API")
@RequestMapping("/api/v1/stores")
@SecurityRequirement(name = "Authorization")
public class StoreController {

    public static final String ADMIN = "ROLE_Admin";

    public static final String STORE = "ROLE_Store";

    public static final String CUSTOMER = "ROLE_Customer";

    private final IStoreService storeService;

    @GetMapping("")
    @Secured({ADMIN})
    @Operation(summary = "Get store list")
    public ResponseEntity<?> getStoreList(@RequestParam(defaultValue = "") String search,
                                          @RequestParam(defaultValue = "0") Optional<Integer> page,
                                          @RequestParam(defaultValue = "storeName,desc") String sort,
                                          @RequestParam(defaultValue = "10") Optional<Integer> limit,
                                          @RequestParam(defaultValue = "") @Parameter(description = "<b>Filter by city ID<b>") List<Long> cityIds) throws MethodArgumentTypeMismatchException {
        Page<StoreDTO> storeList = storeService.getStoreList(true, cityIds, search, sort, page.orElse(0), limit.orElse(10));
        if (!storeList.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(storeList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found store list !");
        }
    }

    @GetMapping("/{id}")
    @Secured({ADMIN, STORE, CUSTOMER})
    @Operation(summary = "Get store by id")
    public ResponseEntity<?> getStoreById(@PathVariable(value = "id") Long storeId) throws MethodArgumentTypeMismatchException {
        StoreExtraDTO store = storeService.getStoreById(true, storeId);
        if (store != null) {
            return ResponseEntity.status(HttpStatus.OK).body(store);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found store !");
        }
    }
}
