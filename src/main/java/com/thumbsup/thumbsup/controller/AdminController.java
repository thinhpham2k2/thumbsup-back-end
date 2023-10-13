package com.thumbsup.thumbsup.controller;

import com.thumbsup.thumbsup.dto.admin.AdminDTO;
import com.thumbsup.thumbsup.service.interfaces.IAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name = "Admin API")
@RequestMapping("/api/v1/admins")
@SecurityRequirement(name = "Authorization")
public class AdminController {

    public static final String ADMIN = "ROLE_Admin";

    private final IAdminService adminService;

    @GetMapping("/{id}")
    @Secured({ADMIN})
    @Operation(summary = "Get admin by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = AdminDTO.class)) }),
    })
    public ResponseEntity<?> getAdminById(@PathVariable(value = "id") Long productId) throws MethodArgumentTypeMismatchException {
        AdminDTO admin = adminService.getAdminById(productId, true);
        if (admin != null) {
            return ResponseEntity.status(HttpStatus.OK).body(admin);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found admin !");
        }
    }

    @GetMapping("")
    @Secured({ADMIN})
    @Operation(summary = "Get admin list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = Page.class)) }),
    })
    public ResponseEntity<?> getAdminList(@RequestParam(defaultValue = "") String search,
                                          @RequestParam(defaultValue = "0") Optional<Integer> page,
                                          @RequestParam(defaultValue = "fullName,desc") String sort,
                                          @RequestParam(defaultValue = "10") Optional<Integer> limit) throws MethodArgumentTypeMismatchException {
        Page<AdminDTO> adminList = adminService.getAdminList(true, search, sort, page.orElse(0), limit.orElse(10));
        if (!adminList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(adminList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found admin list !");
        }
    }
}
