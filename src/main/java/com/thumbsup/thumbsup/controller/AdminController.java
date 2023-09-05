package com.thumbsup.thumbsup.controller;

import com.thumbsup.thumbsup.service.interfaces.IAdminService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Admin API")
@RequestMapping("/api/v1/admins")
@SecurityRequirement(name = "Authorization")
public class AdminController {

    public static final String ADMIN = "ROLE_Admin";

    private final IAdminService adminService;
}
