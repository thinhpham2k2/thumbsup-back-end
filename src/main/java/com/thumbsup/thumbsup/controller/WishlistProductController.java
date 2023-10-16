package com.thumbsup.thumbsup.controller;

import com.thumbsup.thumbsup.dto.wishlist.UpdateWishlistProductDTO;
import com.thumbsup.thumbsup.dto.wishlist.WishlistProductDTO;
import com.thumbsup.thumbsup.service.interfaces.IWishlistProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Wishlist product API")
@RequestMapping("/api/v1/wishlist-products")
@SecurityRequirement(name = "Authorization")
public class WishlistProductController {

    public static final String ADMIN = "ROLE_Admin";

    public static final String STORE = "ROLE_Store";

    public static final String CUSTOMER = "ROLE_Customer";

    private final IWishlistProductService wishlistProductService;

    @GetMapping("")
    @Secured({CUSTOMER})
    @Operation(summary = "Get wishlist product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", array =
                    @ArraySchema(schema = @Schema(implementation = WishlistProductDTO.class)))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getWishlistProduct() throws MethodArgumentTypeMismatchException {
        List<WishlistProductDTO> wishlistProductList = wishlistProductService.getWishlistProduct(true);
        if (!wishlistProductList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(wishlistProductList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found wishlist product !");
        }
    }

    @PutMapping("")
    @Secured({CUSTOMER})
    @Operation(summary = "Updating product wishlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> updateWishlist(@RequestBody UpdateWishlistProductDTO update)
            throws MethodArgumentTypeMismatchException {
        WishlistProductDTO wishlist = wishlistProductService.updateWishlistProduct(update);
        if (wishlist != null) {
            return ResponseEntity.status(HttpStatus.OK).body("Update success");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Update fail");
        }
    }
}
