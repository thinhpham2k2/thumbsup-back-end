package com.thumbsup.thumbsup.controller;

import com.thumbsup.thumbsup.service.interfaces.ICustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Customer API")
@RequestMapping("/api/v1/customers")
@SecurityRequirement(name = "Authorization")
public class CustomerController {

    public static final String ADMIN = "ROLE_Admin";

    public static final String PARTNER = "ROLE_Partner";

    public static final String CUSTOMER = "ROLE_Customer";

    private final ICustomerService customerService;
}
