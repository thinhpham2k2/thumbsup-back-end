package com.thumbsup.thumbsup.service.interfaces;

import com.thumbsup.thumbsup.dto.CustomerDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICustomerService {

    Page<CustomerDTO> getCustomerList(boolean status, List<Long> cityIds, String search, String sort, int page, int limit);
}
