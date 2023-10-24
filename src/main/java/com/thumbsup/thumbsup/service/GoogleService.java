package com.thumbsup.thumbsup.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.thumbsup.thumbsup.dto.jwt.GoogleTokenDTO;
import com.thumbsup.thumbsup.dto.jwt.JwtResponseDTO;
import com.thumbsup.thumbsup.entity.Admin;
import com.thumbsup.thumbsup.entity.CustomUserDetails;
import com.thumbsup.thumbsup.entity.Customer;
import com.thumbsup.thumbsup.entity.Store;
import com.thumbsup.thumbsup.jwt.JwtTokenProvider;
import com.thumbsup.thumbsup.repository.AdminRepository;
import com.thumbsup.thumbsup.repository.CityRepository;
import com.thumbsup.thumbsup.repository.CustomerRepository;
import com.thumbsup.thumbsup.repository.StoreRepository;
import com.thumbsup.thumbsup.service.interfaces.IGoogleService;
import com.thumbsup.thumbsup.service.interfaces.IJwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class GoogleService implements IGoogleService {

    private final IJwtService jwtService;

    private final CityRepository cityRepository;

    private final AdminRepository adminRepository;

    private final StoreRepository storeRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final CustomerRepository customerRepository;

    @Override
    public JwtResponseDTO loginWithGoogle(GoogleTokenDTO googleToken, String role) throws IOException {
        Payload payload = verifyGoogleIdToken(googleToken);
        if (payload.getEmailVerified()) {
            JwtResponseDTO jwtResponseDTO;
            String email = payload.getEmail();
            CustomUserDetails user = new CustomUserDetails();
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String dateTimeString = now.format(formatter);
            switch (role) {
                case "Mobile" -> {
                    Optional<Customer> customer = customerRepository.findCustomerByEmailAndStatus(email, true);
                    if (customer.isPresent()) {
                        user.setCustomer(customer.get());
                        user.setRole("Customer");
                    } else {
                        Optional<Store> store = storeRepository.findStoreByEmailAndStatus(email, true);
                        if (store.isPresent()) {
                            user.setStore(store.get());
                            user.setRole("Store");
                        } else {
                            Customer cus = customerRepository.save(new Customer(null, email + dateTimeString, "", email, email, "", "", LocalDate.now(), "", true, true, cityRepository.findFirstByStatus(true), null, null, null, null));
                            user.setCustomer(cus);
                            user.setRole("Customer");
                        }
                    }
                }
                case "Store" -> {
                    Optional<Customer> customer = customerRepository.findCustomerByEmailAndStatus(email, true);
                    if (customer.isPresent()) {
                        throw new InvalidParameterException("Access denied");
                    } else {
                        Optional<Store> store = storeRepository.findStoreByEmailAndStatus(email, true);
                        if (store.isPresent()) {
                            user.setStore(store.get());
                            user.setRole("Store");
                        } else {
                            Store st = storeRepository.save(new Store(null, email + dateTimeString, "", email, email, "", "", "", "", BigDecimal.ZERO, LocalDate.now(), null, null, "", true, true, cityRepository.findFirstByStatus(true), null, null, null, null, null, null));
                            user.setStore(st);
                            user.setRole("Customer");
                        }
                    }
                }
                case "Admin" -> {
                    Optional<Admin> admin = adminRepository.findAdminByEmailAndStatus(email, true);
                    if (admin.isPresent()) {
                        user.setAdmin(admin.get());
                        user.setRole("Admin");
                    } else {
                        throw new InvalidParameterException("Access denied");
                    }
                }
            }

            String token = jwtTokenProvider.generateToken(user, 17280000000L);
            jwtResponseDTO = jwtService.validJwtResponse(token, user);
            return jwtResponseDTO;
        } else {
            throw new InvalidParameterException("Email has not been verified");
        }
    }

    @Override
    public Payload verifyGoogleIdToken(GoogleTokenDTO googleToken) throws IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(),
                GsonFactory.getDefaultInstance()).setAudience(Collections.singletonList(googleToken.getClientId())).build();
        GoogleIdToken idToken = GoogleIdToken.parse(GsonFactory.getDefaultInstance(), googleToken.getIdToken());
        if (idToken.verifyIssuer(Arrays.asList("accounts.google.com", "https://accounts.google.com")) &&
                (idToken.verifyAudience(List.of(googleToken.getClientId()))) &&
                idToken.verifyTime(verifier.getClock().currentTimeMillis(), verifier.getAcceptableTimeSkewSeconds())) {
            return idToken.getPayload();
        } else {
            throw new InvalidParameterException("Invalid token");
        }
    }
}
