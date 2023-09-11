package com.thumbsup.thumbsup.service.interfaces;

import com.thumbsup.thumbsup.dto.BrandDTO;
import org.springframework.data.domain.Page;

public interface IBrandService {

    Page<BrandDTO> getBrandList(boolean status, String sort, int page, int limit);
}
