package com.thumbsup.thumbsup.service.interfaces;

import com.thumbsup.thumbsup.dto.CountryDTO;
import org.springframework.data.domain.Page;

public interface ICountryService {

    Page<CountryDTO> getCountryList(boolean status, String sort, int page, int limit);
}
