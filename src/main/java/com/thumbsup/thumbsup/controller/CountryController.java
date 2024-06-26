package com.thumbsup.thumbsup.controller;

import com.thumbsup.thumbsup.dto.country.CountryDTO;
import com.thumbsup.thumbsup.service.interfaces.ICountryService;
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
@Tag(name = "Country API")
@RequestMapping("/api/v1/countries")
public class CountryController {

    public static final String ADMIN = "ROLE_Admin";

    public static final String STORE = "ROLE_Store";

    public static final String CUSTOMER = "ROLE_Customer";

    private final ICountryService countryService;

    @GetMapping("")
    @Operation(summary = "Get country list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = Page.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getCountryList(@RequestParam(defaultValue = "") String search,
                                            @RequestParam(defaultValue = "0") Optional<Integer> page,
                                            @RequestParam(defaultValue = "id,asc") String sort,
                                            @RequestParam(defaultValue = "100") Optional<Integer> limit)
            throws MethodArgumentTypeMismatchException {
        Page<CountryDTO> countryList = countryService.getCountryList(true, search, sort, page.orElse(0), limit.orElse(100));
        if (!countryList.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(countryList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found country list");
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get country by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = CountryDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getCountryById(@PathVariable(value = "id") Long countryId)
            throws MethodArgumentTypeMismatchException {
        CountryDTO country = countryService.getCountryById(countryId);
        if (country != null) {
            return ResponseEntity.status(HttpStatus.OK).body(country);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found country");
        }
    }
}
