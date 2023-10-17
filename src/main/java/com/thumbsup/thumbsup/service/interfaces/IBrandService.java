package com.thumbsup.thumbsup.service.interfaces;

import com.thumbsup.thumbsup.dto.brand.BrandDTO;
import org.springframework.data.domain.Page;

public interface IBrandService {

    Page<BrandDTO> getBrandList(boolean status, String search, String sort, int page, int limit);

    BrandDTO getBrandById(long id);
}
