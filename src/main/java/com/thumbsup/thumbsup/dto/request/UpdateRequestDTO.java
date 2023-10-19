package com.thumbsup.thumbsup.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRequestDTO implements Serializable {
    @NotEmpty(message = "Account  number is required")
    @NotNull(message = "Account  number is required")
    private String accountNumber;

    @NotEmpty(message = "Bank name is required")
    @NotNull(message = "Bank name is required")
    private String bankName;

    private String note;

    private Boolean state;
}
