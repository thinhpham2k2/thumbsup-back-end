package com.thumbsup.thumbsup.service.interfaces;

import com.thumbsup.thumbsup.dto.product.ProductDTO;
import com.thumbsup.thumbsup.dto.product.ProductExtraDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductService {

    Page<ProductDTO> getProductList(boolean status, List<Long> storeIds, List<Long> cateIds, List<Long> brandIds, List<Long> countryIds, String search, String sort, int page, int limit);

    Page<ProductDTO> getProductListByStoreId(boolean status, long storeId, List<Long> cateIds, List<Long> brandIds, List<Long> countryIds, String search, String sort, int page, int limit);

    ProductExtraDTO getProductById(boolean status, long productId);
}
