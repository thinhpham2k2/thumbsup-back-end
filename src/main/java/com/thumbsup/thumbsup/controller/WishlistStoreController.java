package com.thumbsup.thumbsup.controller;

import com.thumbsup.thumbsup.dto.wishlist.WishlistStoreDTO;
import com.thumbsup.thumbsup.service.interfaces.IWishlistStoreService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Wishlist store API")
@RequestMapping("/api/v1/wishlist-stores")
@SecurityRequirement(name = "Authorization")
public class WishlistStoreController {

    public static final String ADMIN = "ROLE_Admin";

    public static final String STORE = "ROLE_Store";

    public static final String CUSTOMER = "ROLE_Customer";

    private final IWishlistStoreService wishlistStoreService;

    @GetMapping("")
    @Secured({CUSTOMER})
    @Operation(summary = "Get wishlist store")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", array =
                    @ArraySchema(schema = @Schema(implementation = WishlistStoreDTO.class)))}),
    })
    public ResponseEntity<?> getWishlistStore() {
        List<WishlistStoreDTO> wishlistStoreList = wishlistStoreService.getWishlistStore(true);
        if (!wishlistStoreList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(wishlistStoreList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found wishlist store !");
        }
    }
}
