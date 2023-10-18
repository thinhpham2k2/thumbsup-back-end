package com.thumbsup.thumbsup.controller;

import com.thumbsup.thumbsup.dto.order.OrderDTO;
import com.thumbsup.thumbsup.service.interfaces.IOrderService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name = "Order API")
@RequestMapping("/api/v1/orders")
@SecurityRequirement(name = "Authorization")
public class OrderController {

    public static final String ADMIN = "ROLE_Admin";

    public static final String STORE = "ROLE_Store";

    public static final String CUSTOMER = "ROLE_Customer";

    private final IOrderService orderService;

    @GetMapping("")
    @Secured({ADMIN})
    @Operation(summary = "Get order list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = Page.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getCustomerList(@RequestParam(defaultValue = "") String search,
                                             @RequestParam(defaultValue = "0") Optional<Integer> page,
                                             @RequestParam(defaultValue = "id,desc") String sort,
                                             @RequestParam(defaultValue = "10") Optional<Integer> limit,
                                             @RequestParam(defaultValue = "")
                                                 @Parameter(description = "<b>Filter by customer ID<b>")
                                                 List<Long> customerIds,
                                             @RequestParam(defaultValue = "")
                                                 @Parameter(description = "<b>Filter by state ID<b>")
                                                 List<Long> stateIds,
                                             @RequestParam(defaultValue = "")
                                                 @Parameter(description = "<b>Filter by store ID<b>")
                                                 List<Long> storeIds)
            throws MethodArgumentTypeMismatchException {
        Page<OrderDTO> orderList = orderService.getOrderList(true, customerIds, stateIds, storeIds, search, sort,
                page.orElse(0), limit.orElse(10));
        if (!orderList.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(orderList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found order list");
        }
    }
}
