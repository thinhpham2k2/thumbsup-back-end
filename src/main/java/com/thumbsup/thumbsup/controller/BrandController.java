package com.thumbsup.thumbsup.controller;

import com.thumbsup.thumbsup.dto.brand.BrandDTO;
import com.thumbsup.thumbsup.service.interfaces.IBrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name = "Brand API")
@RequestMapping("/api/v1/brands")
public class BrandController {

    public static final String ADMIN = "ROLE_Admin";

    public static final String STORE = "ROLE_Store";

    public static final String CUSTOMER = "ROLE_Customer";

    private final IBrandService brandService;

    @GetMapping("")
    @Operation(summary = "Get brand list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = Page.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getBrandList(@RequestParam(defaultValue = "0") Optional<Integer> page,
                                          @RequestParam(defaultValue = "brand,asc") String sort,
                                          @RequestParam(defaultValue = "100") Optional<Integer> limit)
            throws MethodArgumentTypeMismatchException {
        Page<BrandDTO> brandList = brandService.getBrandList(true, sort, page.orElse(0), limit.orElse(100));
        if (!brandList.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(brandList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found brand list !");
        }
    }
}
