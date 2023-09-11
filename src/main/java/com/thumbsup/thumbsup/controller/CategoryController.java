package com.thumbsup.thumbsup.controller;

import com.thumbsup.thumbsup.dto.CategoryDTO;
import com.thumbsup.thumbsup.service.interfaces.ICategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name = "Category API")
@RequestMapping("/api/v1/categories")
public class CategoryController {

    public static final String ADMIN = "ROLE_Admin";

    public static final String STORE = "ROLE_Store";

    public static final String CUSTOMER = "ROLE_Customer";

    private final ICategoryService categoryService;

    @GetMapping("")
    @Operation(summary = "Get category list")
    public ResponseEntity<?> getCategoryList(@RequestParam(defaultValue = "0") Optional<Integer> page,
                                          @RequestParam(defaultValue = "category,asc") String sort,
                                          @RequestParam(defaultValue = "100") Optional<Integer> limit) throws MethodArgumentTypeMismatchException {
        Page<CategoryDTO> categoryList = categoryService.getCategoryList(true, sort, page.orElse(0), limit.orElse(100));
        if (!categoryList.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(categoryList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found category list !");
        }
    }
}
