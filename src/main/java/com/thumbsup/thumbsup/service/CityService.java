package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.dto.CityDTO;
import com.thumbsup.thumbsup.entity.City;
import com.thumbsup.thumbsup.mapper.CityMapper;
import com.thumbsup.thumbsup.repository.CityRepository;
import com.thumbsup.thumbsup.service.interfaces.ICityService;
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
public class CityService implements ICityService {

    private final IPagingService pagingService;

    private final CityRepository cityRepository;

    @Override
    public Page<CityDTO> getCityList(boolean status, String sort, int page, int limit) {
        if (page < 0) throw new InvalidParameterException("Page number must not be less than zero!");
        if (limit < 1) throw new InvalidParameterException("Page size must not be less than one!");

        List<Sort.Order> order = new ArrayList<>();
        Set<String> sourceFieldList = pagingService.getAllFields(City.class);
        String[] subSort = sort.split(",");
        if (!pagingService.checkPropertPresent(sourceFieldList, subSort[0])) {
            throw new InvalidParameterException(subSort[0] + " is not a propertied of Customer!");
        }

        Pageable pageable = PageRequest.of(page, limit).withSort(Sort.by(order));
        Page<City> pageResult = cityRepository.getCitiesByStatus(true, pageable);

        return new PageImpl<>(pageResult.getContent().stream()
                .map(CityMapper.INSTANCE::toDTO)
                .collect(Collectors.toList()), pageResult.getPageable(), pageResult.getTotalElements());
    }
}
