package com.thumbsup.thumbsup.service;

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
        Optional<Admin> admin = adminRepository.findAdminByUserNameAndStatus(username, true);
        Optional<Store> store = storeRepository.findStoreByUserNameAndStatus(username, true);
        Optional<Customer> customer = customerRepository.findCustomerByUserNameAndStatus(username, true);
        String role;

        if (customer.isPresent()) {
            role = "Customer";
        } else if (store.isPresent()) {
            role = "Store";
        } else if (admin.isPresent()) {
            role = "Admin";
        } else {
            return null;
        }

        Set<GrantedAuthority> authoritySet = new HashSet<>();
        authoritySet.add(new SimpleGrantedAuthority("ROLE_" + role));

        return new CustomUserDetails(role.equals("Admin") ? admin.get() : null, role.equals("Store") ? store.get() : null, role.equals("Customer") ? customer.get() : null, authoritySet, role);
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

