package com.thumbsup.thumbsup.service.interfaces;

import com.thumbsup.thumbsup.dto.category.CategoryDTO;
import org.springframework.data.domain.Page;

public interface ICategoryService {

    Page<CategoryDTO> getCategoryList(boolean status, String search, String sort, int page, int limit);

    CategoryDTO getCategoryById(long id);
}
