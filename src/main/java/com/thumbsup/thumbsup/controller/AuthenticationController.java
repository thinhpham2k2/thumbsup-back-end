package com.thumbsup.thumbsup.controller;

import com.thumbsup.thumbsup.dto.JwtResponseDTO;
import com.thumbsup.thumbsup.dto.LoginFormDTO;
import com.thumbsup.thumbsup.entity.CustomUserDetails;
import com.thumbsup.thumbsup.jwt.JwtTokenProvider;
import com.thumbsup.thumbsup.service.CustomUserDetailsService;
import com.thumbsup.thumbsup.service.interfaces.IJwtService;
import io.swagger.v3.oas.annotations.Operation;
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

    @PostMapping("/admin/sign-in")
    @Operation(summary = "Admin login to system")
    public ResponseEntity<?> loginAdminAccount(@RequestBody LoginFormDTO loginFormDTO) throws MethodArgumentTypeMismatchException {
        return accountAuthentication(loginFormDTO, "Admin");
    }

    @PostMapping("/store/sign-in")
    @Operation(summary = "Store login to system")
    public ResponseEntity<?> loginStoreAccount(@RequestBody LoginFormDTO loginFormDTO) throws MethodArgumentTypeMismatchException {
        return accountAuthentication(loginFormDTO, "Store");
    }

    @PostMapping("/customer/sign-in")
    @Operation(summary = "Customer login to system")
    public ResponseEntity<?> loginCustomerAccount(@RequestBody LoginFormDTO loginFormDTO) throws MethodArgumentTypeMismatchException {
        return accountAuthentication(loginFormDTO, "Customer");
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
            CustomUserDetailsService.role = role;
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
            String token = jwtTokenProvider.generateToken(user, 17280000000L);
            JwtResponseDTO jwtResponseDTO = jwtService.validJwtResponse(token, user);
            if (jwtResponseDTO != null && jwtResponseDTO.getRole().equals(role)) {
                return ResponseEntity.status(HttpStatus.OK).body(jwtResponseDTO);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user name or password");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user name or password");
        }
    }
}
