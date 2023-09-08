package com.thumbsup.thumbsup.controller;

import com.thumbsup.thumbsup.service.interfaces.IPaymentAccountService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Payment account API")
@RequestMapping("/api/v1/payment-accounts")
@SecurityRequirement(name = "Authorization")
public class PaymentAccountController {

    public static final String ADMIN = "ROLE_Admin";

    public static final String PARTNER = "ROLE_Partner";

    public static final String CUSTOMER = "ROLE_Customer";

    private final IPaymentAccountService paymentAccountService;
}
