package com.thumbsup.thumbsup.controller;

import com.thumbsup.thumbsup.service.interfaces.IStoreService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
