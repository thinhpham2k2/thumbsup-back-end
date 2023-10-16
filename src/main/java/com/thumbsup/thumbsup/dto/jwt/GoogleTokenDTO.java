package com.thumbsup.thumbsup.dto.jwt;

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
    private String idToken;
    private String clientId;
    private String clientSecret;
}
