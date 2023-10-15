package com.thumbsup.thumbsup.controller;

import com.thumbsup.thumbsup.common.Common;
import com.thumbsup.thumbsup.dto.jwt.JwtResponseDTO;
import com.thumbsup.thumbsup.dto.jwt.LoginFormDTO;
import com.thumbsup.thumbsup.entity.CustomUserDetails;
import com.thumbsup.thumbsup.jwt.JwtTokenProvider;
import com.thumbsup.thumbsup.service.interfaces.IJwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication API")
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final IJwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/admin/login")
    @Operation(summary = "Admin login to system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = JwtResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> loginAdminAccount(@RequestBody LoginFormDTO loginFormDTO)
            throws MethodArgumentTypeMismatchException {
        return accountAuthentication(loginFormDTO, "Admin");
    }

    @PostMapping("/store/login")
    @Operation(summary = "Store login to system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = JwtResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> loginStoreAccount(@RequestBody LoginFormDTO loginFormDTO)
            throws MethodArgumentTypeMismatchException {
        return accountAuthentication(loginFormDTO, "Store");
    }

    @PostMapping("/customer/login")
    @Operation(summary = "Customer login to system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = JwtResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> loginCustomerAccount(@RequestBody LoginFormDTO loginFormDTO)
            throws MethodArgumentTypeMismatchException {
        return accountAuthentication(loginFormDTO, "Customer");
    }

    @PostMapping("/mobile/login")
    @Operation(summary = "Customer or Store login to system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = JwtResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> loginMobileAccount(@RequestBody LoginFormDTO loginFormDTO)
            throws MethodArgumentTypeMismatchException {
        return accountAuthentication(loginFormDTO, "Mobile");
    }

    private ResponseEntity<?> accountAuthentication(LoginFormDTO loginFormDTO, String role) {
        String userName = loginFormDTO.getUserName();
        String password = loginFormDTO.getPassword();

        if (userName == null || userName.isEmpty()) {
            return ResponseEntity.badRequest().body("Missing UserName");
        }

        if (password == null || password.isEmpty()) {
            return ResponseEntity.badRequest().body("Missing password");
        }
        try {
            Common.role = role;
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
            String token = jwtTokenProvider.generateToken(user, 17280000000L);
            JwtResponseDTO jwtResponseDTO = jwtService.validJwtResponse(token, user);
            if (jwtResponseDTO != null) {
                return ResponseEntity.status(HttpStatus.OK).body(jwtResponseDTO);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user name or password");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user name or password");
        }
    }
}
