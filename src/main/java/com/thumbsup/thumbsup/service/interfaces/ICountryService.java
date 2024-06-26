package com.thumbsup.thumbsup.service.interfaces;

import com.thumbsup.thumbsup.dto.country.CountryDTO;
import org.springframework.data.domain.Page;

public interface ICountryService {

    Page<CountryDTO> getCountryList(boolean status, String search, String sort, int page, int limit);

    CountryDTO getCountryById(long id);
}
