package com.thumbsup.thumbsup.controller;

import com.thumbsup.thumbsup.dto.state.StateDTO;
import com.thumbsup.thumbsup.service.interfaces.IStateService;
import io.swagger.v3.oas.annotations.Operation;
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

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name = "State API")
@RequestMapping("/api/v1/states")
@SecurityRequirement(name = "Authorization")
public class StateController {

    public static final String ADMIN = "ROLE_Admin";

    public static final String STORE = "ROLE_Store";

    public static final String CUSTOMER = "ROLE_Customer";

    private final IStateService stateService;

    @GetMapping("")
    @Secured({ADMIN, STORE, CUSTOMER})
    @Operation(summary = "Get state list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = Page.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getStateList(@RequestParam(defaultValue = "") String search,
                                          @RequestParam(defaultValue = "0") Optional<Integer> page,
                                          @RequestParam(defaultValue = "id,asc") String sort,
                                          @RequestParam(defaultValue = "100") Optional<Integer> limit)
            throws MethodArgumentTypeMismatchException {
        Page<StateDTO> stateList = stateService.getStateList(true, search, sort, page.orElse(0), limit.orElse(100));
        if (!stateList.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(stateList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found state list");
        }
    }

    @GetMapping("/{id}")
    @Secured({ADMIN, STORE, CUSTOMER})
    @Operation(summary = "Get state by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = StateDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getStateById(@PathVariable(value = "id") Long stateId)
            throws MethodArgumentTypeMismatchException {
        StateDTO state = stateService.getStateById(stateId);
        if (state != null) {
            return ResponseEntity.status(HttpStatus.OK).body(state);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found state");
        }
    }
}
