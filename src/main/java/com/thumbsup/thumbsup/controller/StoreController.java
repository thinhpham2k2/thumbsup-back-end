package com.thumbsup.thumbsup.controller;

import com.thumbsup.thumbsup.dto.ads.AdvertisementDTO;
import com.thumbsup.thumbsup.dto.order.OrderDTO;
import com.thumbsup.thumbsup.dto.payment.PaymentAccountDTO;
import com.thumbsup.thumbsup.dto.product.ProductDTO;
import com.thumbsup.thumbsup.dto.request.RequestDTO;
import com.thumbsup.thumbsup.dto.store.CreateStoreDTO;
import com.thumbsup.thumbsup.dto.store.StoreDTO;
import com.thumbsup.thumbsup.dto.store.StoreExtraDTO;
import com.thumbsup.thumbsup.dto.store.UpdateStoreDTO;
import com.thumbsup.thumbsup.dto.transaction.TransactionOrderDTO;
import com.thumbsup.thumbsup.service.interfaces.*;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name = "Store API")
@RequestMapping("/api/v1/stores")
@SecurityRequirement(name = "Authorization")
public class StoreController {

    public static final String ADMIN = "ROLE_Admin";

    public static final String STORE = "ROLE_Store";

    public static final String CUSTOMER = "ROLE_Customer";

    private final IOrderService orderService;

    private final IStoreService storeService;

    private final IRequestService requestService;

    private final IProductService productService;

    private final IAdvertisementService advertisementService;

    private final IPaymentAccountService paymentAccountService;

    private final ITransactionOrderService transactionOrderService;

    @GetMapping("/{id}/orders/{orderId}")
    @Secured({STORE})
    @Operation(summary = "Get order detail by id for store")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = OrderDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getOrderDetailByIdForCustomer(@PathVariable(value = "id") Long storeId,
                                                           @PathVariable(value = "orderId") Long orderId)
            throws MethodArgumentTypeMismatchException {
        OrderDTO order = orderService.getOrderByIdForStore(orderId, storeId);
        if (order != null) {
            return ResponseEntity.status(HttpStatus.OK).body(order);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found order");
        }
    }

    @GetMapping("/{id}/orders")
    @Secured({STORE})
    @Operation(summary = "Get order list by store id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = Page.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getOrderListByStore(@RequestParam(defaultValue = "") String search,
                                                 @RequestParam(defaultValue = "0") Optional<Integer> page,
                                                 @RequestParam(defaultValue = "id,desc") String sort,
                                                 @RequestParam(defaultValue = "10") Optional<Integer> limit,
                                                 @PathVariable(value = "id") Long storeId,
                                                 @RequestParam(defaultValue = "")
                                                    @Parameter(description = "<b>Filter by customer ID<b>")
                                                    List<Long> customerIds,
                                                 @RequestParam(defaultValue = "")
                                                    @Parameter(description = "<b>Filter by state ID<b>")
                                                    List<Long> stateIds)
            throws MethodArgumentTypeMismatchException {
        Page<OrderDTO> orderList = orderService.getOrderList(true, customerIds, stateIds, Collections.singletonList(storeId), search, sort,
                page.orElse(0), limit.orElse(10));
        if (!orderList.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(orderList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found order list");
        }
    }

    @GetMapping("")
    @Secured({ADMIN})
    @Operation(summary = "Get store list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = Page.class))}),
    })
    public ResponseEntity<?> getStoreList(@RequestParam(defaultValue = "") String search,
                                          @RequestParam(defaultValue = "0") Optional<Integer> page,
                                          @RequestParam(defaultValue = "id,desc") String sort,
                                          @RequestParam(defaultValue = "10") Optional<Integer> limit,
                                          @RequestParam(defaultValue = "")
                                          @Parameter(description = "<b>Filter by city ID<b>")
                                          List<Long> cityIds)
            throws MethodArgumentTypeMismatchException {
        Page<StoreDTO> storeList = storeService.getStoreList(true, cityIds, search, sort, page.orElse(0), limit.orElse(10));
        if (!storeList.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(storeList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found store list !");
        }
    }

    @GetMapping("/{id}")
    @Secured({ADMIN, STORE, CUSTOMER})
    @Operation(summary = "Get store by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = StoreExtraDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getStoreById(@PathVariable(value = "id") Long storeId) throws MethodArgumentTypeMismatchException {
        StoreExtraDTO store = storeService.getStoreById(true, storeId);
        if (store != null) {
            return ResponseEntity.status(HttpStatus.OK).body(store);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found store !");
        }
    }

    @GetMapping("/{id}/products")
    @Secured({ADMIN, STORE})
    @Operation(summary = "Get product list by store id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = Page.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getProductListByStoreId(@PathVariable(value = "id") Long storeId,
                                                     @RequestParam(defaultValue = "") String search,
                                                     @RequestParam(defaultValue = "0") Optional<Integer> page,
                                                     @RequestParam(defaultValue = "id,desc") String sort,
                                                     @RequestParam(defaultValue = "10") Optional<Integer> limit,
                                                     @RequestParam(defaultValue = "")
                                                     @Parameter(description = "<b>Filter by category ID<b>")
                                                     List<Long> categoryIds,
                                                     @RequestParam(defaultValue = "")
                                                     @Parameter(description = "<b>Filter by brand ID<b>")
                                                     List<Long> brandIds,
                                                     @RequestParam(defaultValue = "")
                                                     @Parameter(description = "<b>Filter by country ID<b>")
                                                     List<Long> countryIds)
            throws MethodArgumentTypeMismatchException {
        Page<ProductDTO> productList = productService.getProductListByStoreId(true, storeId, categoryIds, brandIds, countryIds, search, sort, page.orElse(0), limit.orElse(10));
        if (!productList.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(productList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found product list");
        }
    }

    @GetMapping("/{id}/payments")
    @Secured({ADMIN, STORE})
    @Operation(summary = "Get payment list by store id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = Page.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getPaymentListByStoreId(@PathVariable(value = "id") Long storeId,
                                                     @RequestParam(defaultValue = "") String search,
                                                     @RequestParam(defaultValue = "0") Optional<Integer> page,
                                                     @RequestParam(defaultValue = "id,desc") String sort,
                                                     @RequestParam(defaultValue = "10") Optional<Integer> limit)
            throws MethodArgumentTypeMismatchException {
        Page<PaymentAccountDTO> paymentList = paymentAccountService.getPaymentListByStoreId(true, storeId, search, sort, page.orElse(0), limit.orElse(10));
        if (!paymentList.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(paymentList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found payment list");
        }
    }

    @GetMapping("/{id}/transactions")
    @Secured({ADMIN, STORE})
    @Operation(summary = "Get payment list by store id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = Page.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getTransactionListByStoreId(@PathVariable(value = "id") Long storeId,
                                                     @RequestParam(defaultValue = "") String search,
                                                     @RequestParam(defaultValue = "0") Optional<Integer> page,
                                                     @RequestParam(defaultValue = "id,desc") String sort,
                                                     @RequestParam(defaultValue = "10") Optional<Integer> limit)
            throws MethodArgumentTypeMismatchException {
        Page<TransactionOrderDTO> transactionList = transactionOrderService.getTransactionListByStoreId(true, storeId,
                search, sort, page.orElse(0), limit.orElse(10));
        if (!transactionList.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(transactionList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found transaction list");
        }
    }

    @GetMapping("/{id}/advertisements")
    @Secured({ADMIN, STORE})
    @Operation(summary = "Get advertisements list by store id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = Page.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getAdvertisementsListByStoreId(@PathVariable(value = "id") Long storeId,
                                                     @RequestParam(defaultValue = "") String search,
                                                     @RequestParam(defaultValue = "0") Optional<Integer> page,
                                                     @RequestParam(defaultValue = "id,desc") String sort,
                                                     @RequestParam(defaultValue = "10") Optional<Integer> limit)
            throws MethodArgumentTypeMismatchException {
        Page<AdvertisementDTO> adsList = advertisementService.getAdvertisementListByStoreId(true, storeId,
                LocalDateTime.now(), search, sort, page.orElse(0), limit.orElse(10));
        if (!adsList.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(adsList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found advertisements list");
        }
    }

    @GetMapping("/{id}/requests")
    @Secured({ADMIN, STORE})
    @Operation(summary = "Get request list by store id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = Page.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getRequestListByStoreId(@PathVariable(value = "id") Long storeId,
                                                     @RequestParam(defaultValue = "") String search,
                                                     @RequestParam(defaultValue = "0") Optional<Integer> page,
                                                     @RequestParam(defaultValue = "id,desc") String sort,
                                                     @RequestParam(defaultValue = "10") Optional<Integer> limit)
            throws MethodArgumentTypeMismatchException {
        Page<RequestDTO> requestList = requestService.getRequestListByStoreId(true, storeId, search, sort, page.orElse(0), limit.orElse(10));
        if (!requestList.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(requestList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found request list");
        }
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Secured({ADMIN})
    @Operation(summary = "Create store")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = StoreExtraDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> createStore(@ModelAttribute @Validated CreateStoreDTO create)
            throws MethodArgumentTypeMismatchException {
        StoreExtraDTO store = storeService.createStore(create);
        if (store != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(store);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Create fail");
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Secured({ADMIN, STORE})
    @Operation(summary = "Update store")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = StoreExtraDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> updateStore(@PathVariable(value = "id") Long id,
                                         @ModelAttribute @Validated UpdateStoreDTO update)
            throws MethodArgumentTypeMismatchException {
        StoreExtraDTO store = storeService.updateStore(update, id);
        if (store != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(store);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Update fail");
        }
    }

    @DeleteMapping("/{id}")
    @Secured({ADMIN})
    @Operation(summary = "Delete store")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> deleteStore(@PathVariable(value = "id") Long id)
            throws MethodArgumentTypeMismatchException {
        storeService.deleteStore(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Delete success");
    }
}
