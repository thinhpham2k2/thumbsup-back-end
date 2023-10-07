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
public class JwtResponseDTO implements Serializable {
    private String token;
    private Object user;
    private String role;
}
