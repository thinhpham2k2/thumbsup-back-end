package com.thumbsup.thumbsup.service.interfaces;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.thumbsup.thumbsup.dto.jwt.GoogleTokenDTO;
import com.thumbsup.thumbsup.dto.jwt.JwtResponseDTO;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface IGoogleService {

    Payload verifyGoogleIdToken(GoogleTokenDTO googleToken) throws IOException;

    JwtResponseDTO loginWithGoogle(GoogleTokenDTO googleToken, String role) throws IOException;
}
