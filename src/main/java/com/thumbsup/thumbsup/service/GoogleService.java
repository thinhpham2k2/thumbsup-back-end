package com.thumbsup.thumbsup.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.thumbsup.thumbsup.dto.jwt.GoogleTokenDTO;
import com.thumbsup.thumbsup.dto.jwt.LoginFormDTO;
import com.thumbsup.thumbsup.service.interfaces.IGoogleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidParameterException;
import java.util.Collections;

@Service
@Transactional
@RequiredArgsConstructor
public class GoogleService implements IGoogleService {

    @Override
    public LoginFormDTO loginWithGoogle(GoogleTokenDTO googleToken, String role) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(googleToken.getClientId()))
                .build();
        GoogleIdToken idToken = verifier.verify(googleToken.getIdToken());
        if (idToken != null) {
            Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = payload.getEmailVerified();
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");

            return new LoginFormDTO(email, name);
        } else {
            throw new InvalidParameterException("Invalid token");
        }
    }
}
