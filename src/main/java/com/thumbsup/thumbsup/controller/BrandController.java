package com.thumbsup.thumbsup.controller;

import com.thumbsup.thumbsup.service.interfaces.IBrandService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Brand API")
@RequestMapping("/api/v1/brands")
@SecurityRequirement(name = "Authorization")
public class BrandController {

    public static final String ADMIN = "ROLE_Admin";

    public static final String STORE = "ROLE_Store";

    public static final String CUSTOMER = "ROLE_Customer";

    private final IBrandService brandService;
}
