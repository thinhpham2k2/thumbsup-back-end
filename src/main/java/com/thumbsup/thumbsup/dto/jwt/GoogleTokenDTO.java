package com.thumbsup.thumbsup.dto.jwt;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoogleTokenDTO implements Serializable {
    @NotNull(message = "Id token is required")
    private String idToken;

    @NotNull(message = "Client id is required")
    private String clientId;

    private String clientSecret;
}