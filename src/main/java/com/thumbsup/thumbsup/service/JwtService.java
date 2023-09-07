package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.dto.AdminDTO;
import com.thumbsup.thumbsup.dto.CustomerDTO;
import com.thumbsup.thumbsup.dto.JwtResponseDTO;
import com.thumbsup.thumbsup.dto.StoreDTO;
import com.thumbsup.thumbsup.entity.CustomUserDetails;
import com.thumbsup.thumbsup.mapper.AdminMapper;
import com.thumbsup.thumbsup.mapper.CustomerMapper;
import com.thumbsup.thumbsup.mapper.StoreMapper;
import com.thumbsup.thumbsup.service.interfaces.IJwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class JwtService implements IJwtService {

    @Override
    public JwtResponseDTO validJwtResponse(String jwt, CustomUserDetails userDetails) {
        if (userDetails.getCustomer() != null) {
            CustomerDTO customerDTO = CustomerMapper.INSTANCE.toDTO(userDetails.getCustomer());
            return new JwtResponseDTO(jwt, customerDTO, "Customer");
        } else if (userDetails.getStore() != null) {
            StoreDTO storeDTO = StoreMapper.INSTANCE.toDTO(userDetails.getStore());
            return new JwtResponseDTO(jwt, storeDTO, "Store");
        } else if (userDetails.getAdmin() != null) {
            AdminDTO adminDTO = AdminMapper.INSTANCE.toDTO(userDetails.getAdmin());
            return new JwtResponseDTO(jwt, adminDTO, "Admin");
        }
        return null;
    }
}
