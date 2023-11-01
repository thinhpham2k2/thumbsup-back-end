package com.thumbsup.thumbsup.controller;

import com.thumbsup.thumbsup.dto.chart.SaleDTO;
import com.thumbsup.thumbsup.dto.chart.TitleAdminDTO;
import com.thumbsup.thumbsup.service.interfaces.IChartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Chart API")
@RequestMapping("/api/v1/charts")
@SecurityRequirement(name = "Authorization")
public class ChartController {

    public static final String ADMIN = "ROLE_Admin";

    public static final String STORE = "ROLE_Store";

    public static final String CUSTOMER = "ROLE_Customer";

    private final IChartService chartService;

    @GetMapping("/sales")
    @Secured({ADMIN})
    @Operation(summary = "Get sale chart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = SaleDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getSaleChartByStoreId(@RequestParam(required = false,
            defaultValue = "#{T(java.time.LocalDate).now().minusDays(7)}")
                                                       LocalDate from,
                                                   @RequestParam(required = false,
                                                           defaultValue = "#{T(java.time.LocalDate).now()}")
                                                       LocalDate to,
                                                   @RequestParam(defaultValue = "") String sort)
            throws MethodArgumentTypeMismatchException {
        List<SaleDTO> result = chartService.getSale(sort, from.atStartOfDay(), to.atStartOfDay());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/titles")
    @Secured({ADMIN})
    @Operation(summary = "Get title chart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = TitleAdminDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getTitleChartByStoreId()
            throws MethodArgumentTypeMismatchException {
        TitleAdminDTO result = chartService.getTitle();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
