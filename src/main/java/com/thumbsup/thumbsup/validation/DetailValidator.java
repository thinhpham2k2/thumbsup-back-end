package com.thumbsup.thumbsup.validation;

import com.thumbsup.thumbsup.dto.order.CreateOrderDetailDTO;
import com.thumbsup.thumbsup.dto.product.ProductExtraDTO;
import com.thumbsup.thumbsup.service.interfaces.IProductService;
import com.thumbsup.thumbsup.validation.interfaces.DetailConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class DetailValidator implements ConstraintValidator<DetailConstraint, List<CreateOrderDetailDTO>> {

    private final IProductService productService;

    @Override
    public void initialize(DetailConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<CreateOrderDetailDTO> value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            List<ProductExtraDTO> productList = new ArrayList<>();
            constraintValidatorContext.disableDefaultConstraintViolation();
            for (CreateOrderDetailDTO d : value) {
                productList.add(productService.getProductById(true, d.getProductId()));
            }

            boolean hasDuplicateStore = productList.stream()
                    .map(ProductExtraDTO::getStoreId)
                    .distinct().count() == 1;

            if (!hasDuplicateStore) {
                constraintValidatorContext.buildConstraintViolationWithTemplate("The products in the order are not " +
                        "from the same store").addConstraintViolation();
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
