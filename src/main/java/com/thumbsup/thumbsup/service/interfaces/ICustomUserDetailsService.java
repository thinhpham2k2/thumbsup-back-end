package com.thumbsup.thumbsup.service.interfaces;

import org.springframework.security.core.userdetails.UserDetails;

public interface ICustomUserDetailsService {

    UserDetails loadUserByPartner();

    UserDetails loadUserByAdmin();
}
