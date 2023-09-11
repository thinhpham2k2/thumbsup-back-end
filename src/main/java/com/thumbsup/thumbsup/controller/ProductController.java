package com.thumbsup.thumbsup.controller;

import com.thumbsup.thumbsup.dto.ProductDTO;
import com.thumbsup.thumbsup.dto.ProductExtraDTO;
import com.thumbsup.thumbsup.dto.ReviewDTO;
import com.thumbsup.thumbsup.service.interfaces.IJwtService;
import com.thumbsup.thumbsup.service.interfaces.IProductService;
import com.thumbsup.thumbsup.service.interfaces.IReviewService;
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
@Tag(name = "Product API")
@RequestMapping("/api/v1/products")
@SecurityRequirement(name = "Authorization")
public class ProductController {

    public static final String ADMIN = "ROLE_Admin";

    public static final String STORE = "ROLE_Store";

    public static final String CUSTOMER = "ROLE_Customer";

    private final IProductService productService;

    private final IJwtService jwtService;

    private final IReviewService reviewService;

    @GetMapping("")
    @Secured({ADMIN, STORE, CUSTOMER})
    @Operation(summary = "Get product list")
    public ResponseEntity<?> getProductList(@RequestParam(defaultValue = "") String search,
                                            @RequestParam(defaultValue = "0") Optional<Integer> page,
                                            @RequestParam(defaultValue = "productName,desc") String sort,
                                            @RequestParam(defaultValue = "10") Optional<Integer> limit,
                                            @RequestParam(defaultValue = "") @Parameter(description = "<b>Filter by store ID<b>") List<Long> storeIds,
                                            @RequestParam(defaultValue = "") @Parameter(description = "<b>Filter by category ID<b>") List<Long> categoryIds,
                                            @RequestParam(defaultValue = "") @Parameter(description = "<b>Filter by brand ID<b>") List<Long> brandIds,
                                            @RequestParam(defaultValue = "") @Parameter(description = "<b>Filter by country ID<b>") List<Long> countryIds) throws MethodArgumentTypeMismatchException {
        Page<ProductDTO> productList = productService.getProductList(true, storeIds, categoryIds, brandIds, countryIds, search, sort, page.orElse(0), limit.orElse(10));
        if (!productList.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(productList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found product list !");
        }
    }

    @GetMapping("/{id}/reviews")
    @Secured({ADMIN, STORE, CUSTOMER})
    @Operation(summary = "Get reviews by product id")
    public ResponseEntity<?> getReviewListByProductId(@PathVariable(value = "id") Long productId,
                                                      @RequestParam(defaultValue = "") String search,
                                                      @RequestParam(defaultValue = "0") Optional<Integer> page,
                                                      @RequestParam(defaultValue = "product,desc") String sort,
                                                      @RequestParam(defaultValue = "10") Optional<Integer> limit) throws MethodArgumentTypeMismatchException {
        Page<ReviewDTO> reviewList = reviewService.getReviewListByProductId(true, productId, search, sort, page.orElse(0), limit.orElse(10));
        if (!reviewList.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(reviewList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found review list !");
        }
    }

    @GetMapping("/{id}")
    @Secured({ADMIN, STORE, CUSTOMER})
    @Operation(summary = "Get product by id")
    public ResponseEntity<?> getProductById(@PathVariable(value = "id") Long productId) {
        ProductExtraDTO product = productService.getProductById(true, productId);
        if (product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found product !");
        }
    }
}
