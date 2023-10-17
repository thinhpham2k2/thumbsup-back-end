package com.thumbsup.thumbsup.service.interfaces;

import com.thumbsup.thumbsup.dto.city.CityDTO;
import org.springframework.data.domain.Page;

public interface ICityService {

    Page<CityDTO> getCityList(boolean status, String search, String sort, int page, int limit);

    CityDTO getCityById(long id);
}
