package com.thumbsup.thumbsup.controller;

import com.thumbsup.thumbsup.dto.payment.PaymentAccountDTO;
import com.thumbsup.thumbsup.dto.transaction.TransactionOrderDTO;
import com.thumbsup.thumbsup.service.interfaces.ITransactionOrderService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name = "Transaction order API")
@RequestMapping("/api/v1/transactions")
@SecurityRequirement(name = "Authorization")
public class TransactionOrderController {

    public static final String ADMIN = "ROLE_Admin";

    public static final String STORE = "ROLE_Store";

    public static final String CUSTOMER = "ROLE_Customer";

    private final ITransactionOrderService transactionOrderService;

    @GetMapping("")
    @Secured({ADMIN})
    @Operation(summary = "Get transaction list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = Page.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getTransactionList(@RequestParam(defaultValue = "") String search,
                                            @RequestParam(defaultValue = "0") Optional<Integer> page,
                                            @RequestParam(defaultValue = "id,desc") String sort,
                                            @RequestParam(defaultValue = "10") Optional<Integer> limit,
                                            @RequestParam(defaultValue = "")
                                            @Parameter(description = "<b>Filter by store ID<b>")
                                            List<Long> storeIds)
            throws MethodArgumentTypeMismatchException {
        Page<TransactionOrderDTO> transactionList = transactionOrderService.getTransactionList(true, storeIds,
                search, sort, page.orElse(0), limit.orElse(10));
        if (!transactionList.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(transactionList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found transaction list");
        }
    }

    @GetMapping("/{id}")
    @Secured({ADMIN, STORE})
    @Operation(summary = "Get transaction by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = PaymentAccountDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getTransactionById(@PathVariable(value = "id") Long transactionId)
            throws MethodArgumentTypeMismatchException {
        TransactionOrderDTO transaction = transactionOrderService.getTransactionById(transactionId);
        if (transaction != null) {
            return ResponseEntity.status(HttpStatus.OK).body(transaction);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found transaction");
        }
    }
}
