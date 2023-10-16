package com.thumbsup.thumbsup.service.interfaces;

import com.thumbsup.thumbsup.dto.jwt.GoogleTokenDTO;
import com.thumbsup.thumbsup.dto.jwt.LoginFormDTO;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface IGoogleService {

    LoginFormDTO loginWithGoogle(GoogleTokenDTO googleToken, String role) throws GeneralSecurityException, IOException;
}
