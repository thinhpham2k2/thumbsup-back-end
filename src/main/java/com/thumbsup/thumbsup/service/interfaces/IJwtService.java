package com.thumbsup.thumbsup.service.interfaces;

import com.thumbsup.thumbsup.dto.JwtResponseDTO;
import com.thumbsup.thumbsup.entity.CustomUserDetails;

public interface IJwtService {

    JwtResponseDTO validJwtResponse(String jwt, CustomUserDetails userDetails);
}
