package com.thumbsup.thumbsup.controller;

import com.thumbsup.thumbsup.dto.request.CreateRequestDTO;
import com.thumbsup.thumbsup.dto.request.RequestDTO;
import com.thumbsup.thumbsup.dto.request.UpdateRequestDTO;
import com.thumbsup.thumbsup.service.interfaces.IRequestService;
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
@Tag(name = "Request API")
@RequestMapping("/api/v1/requests")
@SecurityRequirement(name = "Authorization")
public class RequestController {

    public static final String ADMIN = "ROLE_Admin";

    public static final String STORE = "ROLE_Store";

    public static final String CUSTOMER = "ROLE_Customer";

    private final IRequestService requestService;

    @GetMapping("")
    @Secured({ADMIN})
    @Operation(summary = "Get request list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = Page.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getRequestList(@RequestParam(defaultValue = "") String search,
                                            @RequestParam(defaultValue = "0") Optional<Integer> page,
                                            @RequestParam(defaultValue = "id,desc") String sort,
                                            @RequestParam(defaultValue = "10") Optional<Integer> limit,
                                            @RequestParam(defaultValue = "")
                                            @Parameter(description = "<b>Filter by store ID<b>")
                                            List<Long> storeIds)
            throws MethodArgumentTypeMismatchException {
        Page<RequestDTO> requestList = requestService.getRequestList(true, storeIds, search, sort, page.orElse(0), limit.orElse(10));
        if (!requestList.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(requestList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found request list");
        }
    }

    @GetMapping("/{id}")
    @Secured({ADMIN, STORE})
    @Operation(summary = "Get request by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = RequestDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getRequestListById(@PathVariable(value = "id") Long requestId)
            throws MethodArgumentTypeMismatchException {
        RequestDTO request = requestService.getRequestById(requestId);
        if (request != null) {
            return ResponseEntity.status(HttpStatus.OK).body(request);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found request");
        }
    }

    @PostMapping("")
    @Secured({ADMIN, STORE})
    @Operation(summary = "Create request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = RequestDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> createRequest(@RequestBody @Validated CreateRequestDTO create){
        RequestDTO request = requestService.createRequest(create);
        if (request != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(request);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Create fail");
        }
    }

    @PutMapping("/{id}")
    @Secured({ADMIN, STORE})
    @Operation(summary = "Update request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = RequestDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> updateRequest(@PathVariable(value = "id") Long id,
                                           @RequestBody @Validated UpdateRequestDTO update){
        RequestDTO request = requestService.updateRequest(id, update);
        if (request != null) {
            return ResponseEntity.status(HttpStatus.OK).body(request);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Update fail");
        }
    }

    @DeleteMapping("/{id}")
    @Secured({ADMIN, STORE})
    @Operation(summary = "Delete request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = RequestDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> deleteRequest(@PathVariable(value = "id") Long id){
        requestService.deleteRequest(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Delete success");
    }
}
