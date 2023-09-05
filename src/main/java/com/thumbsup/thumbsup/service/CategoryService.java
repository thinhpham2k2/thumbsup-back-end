package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.repository.CategoryRepository;
import com.thumbsup.thumbsup.service.interfaces.ICategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
}
