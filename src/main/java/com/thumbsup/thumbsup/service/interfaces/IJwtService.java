package com.thumbsup.thumbsup.service.interfaces;

import com.thumbsup.thumbsup.dto.jwt.JwtResponseDTO;
import com.thumbsup.thumbsup.entity.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;

public interface IJwtService {

    JwtResponseDTO validJwtResponse(String jwt, CustomUserDetails userDetails);

    String getJwtFromRequest(HttpServletRequest request);
}
