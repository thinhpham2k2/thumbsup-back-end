package com.thumbsup.thumbsup.service.interfaces;

import com.thumbsup.thumbsup.dto.customer.CreateCustomerDTO;
import com.thumbsup.thumbsup.dto.customer.CustomerDTO;
import com.thumbsup.thumbsup.dto.customer.UpdateCustomerDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICustomerService {

    Page<CustomerDTO> getCustomerList(boolean status, List<Long> cityIds, String search, String sort, int page, int limit);

    CustomerDTO getCustomerById(long customerId, boolean status);

    boolean checkByUsername(String userName);

    boolean checkByEmail(String email);

    CustomerDTO createCustomer(CreateCustomerDTO create);

    CustomerDTO updateCustomer(UpdateCustomerDTO update, Long id);

    void deleteCustomer(Long id);
}
