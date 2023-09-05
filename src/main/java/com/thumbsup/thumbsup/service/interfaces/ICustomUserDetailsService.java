package com.thumbsup.thumbsup.service.interfaces;

import org.springframework.security.core.userdetails.UserDetails;

public interface ICustomUserDetailsService {

    UserDetails loadUserByEmail(String email);

    UserDetails loadUserByPartner();

    UserDetails loadUserByAdmin();
}
