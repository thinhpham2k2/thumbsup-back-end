package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.entity.CustomUserDetails;
import com.thumbsup.thumbsup.service.interfaces.ICustomUserDetailsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements ICustomUserDetailsService, UserDetailsService {

//    private final AdminRepository adminRepository;
//
//    private final PartnerRepository partnerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        Optional<Partner> partner = partnerRepository.findPartnerByUserNameAndStatus(username, true);
//        Optional<Admin> admin = adminRepository.findAdminByUserNameAndStatus(username, true);
//        String role;
//
//        if (partner.isEmpty()) {
//            if (admin.isEmpty()) {
//                throw new UsernameNotFoundException(username);
//            } else {
//                role = "Admin";
//            }
//        } else {
//            role = "Partner";
//        }
//
//        Set<GrantedAuthority> authoritySet = new HashSet<>();
//        authoritySet.add(new SimpleGrantedAuthority("ROLE_" + role));
//
//        return new CustomUserDetails(admin.isPresent() && partner.isEmpty() ? admin.get() : null, partner.orElse(null), authoritySet, role);
        return null;
    }

    @Override
    public UserDetails loadUserByEmail(String email) {
//        Optional<Partner> partner = partnerRepository.findPartnerByEmail(email);
//        Optional<Admin> admin = adminRepository.findAdminByEmail(email);
//        String role;
//
//        if (partner.isEmpty()) {
//            if (admin.isEmpty()) {
//                return new CustomUserDetails(null, null, null, null);
//            } else {
//                if (admin.get().getStatus()) {
//                    role = "Admin";
//                } else {
//                    throw new InvalidParameterException("Your email account has been blocked");
//                }
//            }
//        } else {
//            if (partner.get().getStatus()) {
//                role = "Partner";
//            } else {
//                throw new InvalidParameterException("Your email account has been blocked");
//            }
//        }
//
//        Set<GrantedAuthority> authoritySet = new HashSet<>();
//        authoritySet.add(new SimpleGrantedAuthority("ROLE_" + role));
//
//        return new CustomUserDetails(admin.isPresent() && partner.isEmpty() ? admin.get() : null, partner.orElse(null), authoritySet, role);
        return null;
    }

    @Override
    public UserDetails loadUserByPartner() {
        return null;
    }

    @Override
    public UserDetails loadUserByAdmin() {
        return null;
    }
//
//    @Override
//    public UserDetails loadUserByPartner(PartnerDTO partnerDTO) {
//        String role = "Partner";
//        Set<GrantedAuthority> authoritySet = new HashSet<>();
//        authoritySet.add(new SimpleGrantedAuthority("ROLE_" + role));
//
//        return new CustomUserDetails(null, PartnerMapper.INSTANCE.toEntity(partnerDTO), authoritySet, role);
//    }
//
//    @Override
//    public UserDetails loadUserByAdmin(AdminDTO adminDTO) {
//        String role = "Admin";
//        Set<GrantedAuthority> authoritySet = new HashSet<>();
//        authoritySet.add(new SimpleGrantedAuthority("ROLE_" + role));
//
//        return new CustomUserDetails(AdminMapper.INSTANCE.toEntity(adminDTO), null, authoritySet, role);
//    }
}

