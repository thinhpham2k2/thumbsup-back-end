package com.thumbsup.thumbsup.service.interfaces;

import com.thumbsup.thumbsup.dto.CategoryDTO;
import org.springframework.data.domain.Page;

public interface ICategoryService {

    Page<CategoryDTO> getCategoryList(boolean status, String sort, int page, int limit);
}
