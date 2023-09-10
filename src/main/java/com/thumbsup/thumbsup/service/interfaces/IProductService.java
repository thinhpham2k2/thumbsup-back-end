package com.thumbsup.thumbsup.service.interfaces;

import com.thumbsup.thumbsup.dto.ProductDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductService {

    Page<ProductDTO> getProductList(boolean status, List<Long> storeIds, List<Long> cateIds, List<Long> brandIds, List<Long> countryIds, String search, String sort, int page, int limit);
}
