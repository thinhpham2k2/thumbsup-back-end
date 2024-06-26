package com.thumbsup.thumbsup.controller;

import com.thumbsup.thumbsup.dto.city.CityDTO;
import com.thumbsup.thumbsup.service.interfaces.ICityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name = "City API")
@RequestMapping("/api/v1/cities")
public class CityController {

    public static final String ADMIN = "ROLE_Admin";

    public static final String STORE = "ROLE_Store";

    public static final String CUSTOMER = "ROLE_Customer";

    private final ICityService cityService;

    @GetMapping("")
    @Operation(summary = "Get city list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = Page.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getCityList(@RequestParam(defaultValue = "") String search,
                                         @RequestParam(defaultValue = "0") Optional<Integer> page,
                                         @RequestParam(defaultValue = "cityName,asc") String sort,
                                         @RequestParam(defaultValue = "100") Optional<Integer> limit)
            throws MethodArgumentTypeMismatchException {
        Page<CityDTO> cityList = cityService.getCityList(true, search, sort, page.orElse(0), limit.orElse(100));
        if (!cityList.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(cityList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found city list");
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get city by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = CityDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getCityById(@PathVariable(value = "id") Long cityId)
            throws MethodArgumentTypeMismatchException {
        CityDTO city = cityService.getCityById(cityId);
        if (city != null) {
            return ResponseEntity.status(HttpStatus.OK).body(city);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found city");
        }
    }
}
