package com.thumbsup.thumbsup.controller;

import com.thumbsup.thumbsup.dto.payment.CreatePaymentAccountDTO;
import com.thumbsup.thumbsup.dto.payment.PaymentAccountDTO;
import com.thumbsup.thumbsup.service.interfaces.IPaymentAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name = "Payment account API")
@RequestMapping("/api/v1/payments")
@SecurityRequirement(name = "Authorization")
public class PaymentAccountController {

    public static final String ADMIN = "ROLE_Admin";

    public static final String STORE = "ROLE_Store";

    public static final String CUSTOMER = "ROLE_Customer";

    private final IPaymentAccountService paymentAccountService;

    @GetMapping("")
    @Secured({ADMIN})
    @Operation(summary = "Get payment list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = Page.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getPaymentList(@RequestParam(defaultValue = "") String search,
                                            @RequestParam(defaultValue = "0") Optional<Integer> page,
                                            @RequestParam(defaultValue = "id,desc") String sort,
                                            @RequestParam(defaultValue = "10") Optional<Integer> limit,
                                            @RequestParam(defaultValue = "")
                                            @Parameter(description = "<b>Filter by store ID<b>")
                                            List<Long> storeIds)
            throws MethodArgumentTypeMismatchException {
        Page<PaymentAccountDTO> paymentList = paymentAccountService.getPaymentList(true, storeIds, search, sort,
                page.orElse(0), limit.orElse(10));
        if (!paymentList.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(paymentList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found payment list");
        }
    }

    @GetMapping("/{id}")
    @Secured({ADMIN, STORE})
    @Operation(summary = "Get payment by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = PaymentAccountDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getPaymentById(@PathVariable(value = "id") Long paymentId)
            throws MethodArgumentTypeMismatchException {
        PaymentAccountDTO payment = paymentAccountService.getPaymentById(paymentId);
        if (payment != null) {
            return ResponseEntity.status(HttpStatus.OK).body(payment);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found payment");
        }
    }

    @PostMapping("")
    @Secured({ADMIN, STORE})
    @Operation(summary = "Create payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = PaymentAccountDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> createPayment(@RequestBody @Validated CreatePaymentAccountDTO create)
            throws MethodArgumentTypeMismatchException {
        PaymentAccountDTO payment = paymentAccountService.createPayment(create);
        if (payment != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(payment);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Create fail");
        }
    }
}
