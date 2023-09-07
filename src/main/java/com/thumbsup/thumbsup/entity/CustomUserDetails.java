package com.thumbsup.thumbsup.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {

    private Admin admin;
    private Store store;
    private Customer customer;

    Set<GrantedAuthority> authoritySet;

    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authoritySet;
    }

    @Override
    public String getPassword() {
        if (this.customer != null) {
            return customer.getPassword();
        } else if (this.store != null) {
            return store.getPassword();
        } else {
            return admin.getPassword();
        }
    }

    @Override
    public String getUsername() {
        if (this.customer != null) {
            return customer.getUserName();
        } else if (this.store != null) {
            return store.getUserName();
        } else {
            return admin.getUserName();
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
