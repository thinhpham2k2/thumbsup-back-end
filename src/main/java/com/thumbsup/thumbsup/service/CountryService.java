package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.dto.country.CountryDTO;
import com.thumbsup.thumbsup.entity.Country;
import com.thumbsup.thumbsup.mapper.CountryMapper;
import com.thumbsup.thumbsup.repository.CountryRepository;
import com.thumbsup.thumbsup.service.interfaces.ICountryService;
import com.thumbsup.thumbsup.service.interfaces.IPagingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.ast.tree.expression.Collation;
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
public class CountryService implements ICountryService {

    private final IPagingService pagingService;

    private final CountryRepository countryRepository;

    @Override
    public Page<CountryDTO> getCountryList(boolean status, String search, String sort, int page, int limit) {
        if (page < 0) throw new InvalidParameterException("Page number must not be less than zero!");
        if (limit < 1) throw new InvalidParameterException("Page size must not be less than one!");

        List<Sort.Order> order = new ArrayList<>();
        Set<String> sourceFieldList = pagingService.getAllFields(Country.class);
        String[] subSort = sort.split(",");
        if (pagingService.checkPropertPresent(sourceFieldList, subSort[0])) {
            order.add(new Sort.Order(pagingService.getSortDirection(subSort[1]), subSort[0]));
        } else {
            throw new InvalidParameterException(subSort[0] + " is not a propertied of Country");
        }

        Pageable pageable = PageRequest.of(page, limit).withSort(Sort.by(order));
        Page<Country> pageResult = countryRepository.getCountriesByStatus(status, search, pageable);

        return new PageImpl<>(pageResult.getContent().stream()
                .map(CountryMapper.INSTANCE::toDTO)
                .collect(Collectors.toList()), pageResult.getPageable(), pageResult.getTotalElements());
    }

    @Override
    public CountryDTO getCountryById(long id) {
        return CountryMapper.INSTANCE.toDTO(countryRepository.findByIdAndStatus(id, true).orElse(null));
    }
}
