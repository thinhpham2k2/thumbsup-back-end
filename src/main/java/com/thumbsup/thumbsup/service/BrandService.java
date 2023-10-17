package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.dto.brand.BrandDTO;
import com.thumbsup.thumbsup.entity.Brand;
import com.thumbsup.thumbsup.mapper.BrandMapper;
import com.thumbsup.thumbsup.repository.BrandRepository;
import com.thumbsup.thumbsup.service.interfaces.IBrandService;
import com.thumbsup.thumbsup.service.interfaces.IPagingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BrandService implements IBrandService {

    private final IPagingService pagingService;

    private final BrandRepository brandRepository;

    @Override
    public Page<BrandDTO> getBrandList(boolean status, String search, String sort, int page, int limit) {
        if (page < 0) throw new InvalidParameterException("Page number must not be less than zero!");
        if (limit < 1) throw new InvalidParameterException("Page size must not be less than one!");

        List<Sort.Order> order = new ArrayList<>();
        Set<String> sourceFieldList = pagingService.getAllFields(Brand.class);
        String[] subSort = sort.split(",");
        if (pagingService.checkPropertPresent(sourceFieldList, subSort[0])) {
            order.add(new Sort.Order(pagingService.getSortDirection(subSort[1]), subSort[0]));
        } else {
            throw new InvalidParameterException(subSort[0] + " is not a propertied of Brand!");
        }

        Pageable pageable = PageRequest.of(page, limit).withSort(Sort.by(order));
        Page<Brand> pageResult = brandRepository.getBrandsByStatus(status, search, pageable);

        return new PageImpl<>(pageResult.getContent().stream()
                .map(BrandMapper.INSTANCE::toDTO)
                .collect(Collectors.toList()), pageResult.getPageable(), pageResult.getTotalElements());
    }

    @Override
    public BrandDTO getBrandById(long id) {
        return BrandMapper.INSTANCE.toDTO(brandRepository.findByIdAndStatus(id, true).orElse(null));
    }
}
