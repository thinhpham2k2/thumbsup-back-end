package com.thumbsup.thumbsup.controller;

import com.thumbsup.thumbsup.dto.ads.AdvertisementDTO;
import com.thumbsup.thumbsup.dto.store.StoreExtraDTO;
import com.thumbsup.thumbsup.service.interfaces.IAdvertisementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name = "Advertisement API")
@RequestMapping("/api/v1/advertisements")
@SecurityRequirement(name = "Authorization")
public class AdvertisementController {

    public static final String ADMIN = "ROLE_Admin";

    public static final String STORE = "ROLE_Store";

    public static final String CUSTOMER = "ROLE_Customer";

    private final IAdvertisementService advertisementService;

    @GetMapping("/ads-stores")
    @Secured({ADMIN, STORE, CUSTOMER})
    @Operation(summary = "Get advertisement store list")
    public ResponseEntity<?> getAdvertisementStoreList(@RequestParam(defaultValue = "") String search,
                                                       @RequestParam(defaultValue = "0") Optional<Integer> page,
                                                       @RequestParam(defaultValue = "price,desc") String sort,
                                                       @RequestParam(defaultValue = "100") Optional<Integer> limit,
                                                       @RequestParam(defaultValue = "") @Parameter(description = "<b>Filter by store ID<b>") List<Long> storeIds) throws MethodArgumentTypeMismatchException {
        Page<StoreExtraDTO> storeExtraList = advertisementService.getAdvertisementStoreList(true, storeIds, LocalDateTime.now(), search, sort, page.orElse(0), limit.orElse(100));
        if (!storeExtraList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(storeExtraList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found advertisement store list !");
        }
    }

    @GetMapping("")
    @Secured({ADMIN})
    @Operation(summary = "Get advertisement list")
    public ResponseEntity<?> getAdvertisementList(@RequestParam(defaultValue = "") String search,
                                                       @RequestParam(defaultValue = "0") Optional<Integer> page,
                                                       @RequestParam(defaultValue = "id,desc") String sort,
                                                       @RequestParam(defaultValue = "10") Optional<Integer> limit,
                                                       @RequestParam(defaultValue = "") @Parameter(description = "<b>Filter by store ID<b>") List<Long> storeIds) throws MethodArgumentTypeMismatchException {
        Page<AdvertisementDTO> advertisementList = advertisementService.getAdvertisementList(true, storeIds, LocalDateTime.now(), search, sort, page.orElse(0), limit.orElse(10));
        if (!advertisementList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(advertisementList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found advertisement list !");
        }
    }
}
