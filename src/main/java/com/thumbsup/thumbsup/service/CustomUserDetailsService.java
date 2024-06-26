package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.common.Common;
import com.thumbsup.thumbsup.entity.Admin;
import com.thumbsup.thumbsup.entity.CustomUserDetails;
import com.thumbsup.thumbsup.entity.Customer;
import com.thumbsup.thumbsup.entity.Store;
import com.thumbsup.thumbsup.repository.AdminRepository;
import com.thumbsup.thumbsup.repository.CustomerRepository;
import com.thumbsup.thumbsup.repository.StoreRepository;
import com.thumbsup.thumbsup.service.interfaces.ICustomUserDetailsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements ICustomUserDetailsService, UserDetailsService {

    private final AdminRepository adminRepository;

    private final StoreRepository storeRepository;

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Set<GrantedAuthority> authoritySet = new HashSet<>();
        if (!Common.role.isBlank()) {
            switch (Common.role) {
                case "Mobile" -> {
                    Optional<Customer> customer = customerRepository.findCustomerByUserNameAndStatus(username, true);
                    if (customer.isPresent()) {
                        Common.role = "Customer";
                        authoritySet.add(new SimpleGrantedAuthority("ROLE_" + Common.role));
                        return new CustomUserDetails(null, null, customer.get(), authoritySet, Common.role);
                    } else {
                        Optional<Store> store = storeRepository.findStoreByUserNameAndStatus(username, true);
                        if (store.isPresent()) {
                            Common.role = "Store";
                            authoritySet.add(new SimpleGrantedAuthority("ROLE_" + Common.role));
                            return new CustomUserDetails(null, store.get(), null, authoritySet, Common.role);
                        }
                    }
                }
                case "Admin" -> {
                    authoritySet.add(new SimpleGrantedAuthority("ROLE_" + Common.role));
                    Optional<Admin> admin = adminRepository.findAdminByUserNameAndStatus(username, true);
                    return new CustomUserDetails(admin.orElse(null), null, null, authoritySet, Common.role);
                }
                case "Store" -> {
                    authoritySet.add(new SimpleGrantedAuthority("ROLE_" + Common.role));
                    Optional<Store> store = storeRepository.findStoreByUserNameAndStatus(username, true);
                    return new CustomUserDetails(null, store.orElse(null), null, authoritySet, Common.role);
                }
                case "Customer" -> {
                    authoritySet.add(new SimpleGrantedAuthority("ROLE_" + Common.role));
                    Optional<Customer> customer = customerRepository.findCustomerByUserNameAndStatus(username, true);
                    return new CustomUserDetails(null, null, customer.orElse(null), authoritySet, Common.role);
                }
            }
        }
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

