package com.thumbsup.thumbsup.controller;

import com.thumbsup.thumbsup.dto.CustomerDTO;
import com.thumbsup.thumbsup.service.interfaces.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@Tag(name = "Customer API")
@RequestMapping("/api/v1/customers")
@SecurityRequirement(name = "Authorization")
public class CustomerController {

    public static final String ADMIN = "ROLE_Admin";

    public static final String STORE = "ROLE_Store";

    public static final String CUSTOMER = "ROLE_Customer";

    private final ICustomerService customerService;

    @GetMapping("")
    @Secured({ADMIN})
    @Operation(summary = "Get customer list")
    public ResponseEntity<?> getCustomerList(@RequestParam(defaultValue = "") String search,
                                            @RequestParam(defaultValue = "0") Optional<Integer> page,
                                            @RequestParam(defaultValue = "id,desc") String sort,
                                            @RequestParam(defaultValue = "10") Optional<Integer> limit,
                                            @RequestParam(defaultValue = "") @Parameter(description = "<b>Filter by city ID<b>") List<Long> cityIds) throws MethodArgumentTypeMismatchException {
        Page<CustomerDTO> customerList = customerService.getCustomerList(true, cityIds, search, sort, page.orElse(0), limit.orElse(10));
        if (!customerList.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(customerList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found customer list !");
        }
    }

    @GetMapping("/{id}")
    @Secured({ADMIN, STORE, CUSTOMER})
    @Operation(summary = "Get customer by id")
    public ResponseEntity<?> getCustomerById(@PathVariable(value = "id") Long productId) throws MethodArgumentTypeMismatchException {
        CustomerDTO customer = customerService.getCustomerById(productId, true);
        if (customer != null) {
            return ResponseEntity.status(HttpStatus.OK).body(customer);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found customer !");
        }
    }
}
