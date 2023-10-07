package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.dto.admin.AdminDTO;
import com.thumbsup.thumbsup.entity.Admin;
import com.thumbsup.thumbsup.mapper.AdminMapper;
import com.thumbsup.thumbsup.repository.AdminRepository;
import com.thumbsup.thumbsup.service.interfaces.IAdminService;
import com.thumbsup.thumbsup.service.interfaces.IPagingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService implements IAdminService {

    private final IPagingService pagingService;

    private final AdminRepository adminRepository;

    @Override
    public AdminDTO getAdminById(long adminId, boolean status) {
        Optional<Admin> admin = adminRepository.findAdminByIdAndStatus(adminId, status);
        return admin.map(AdminMapper.INSTANCE::toDTO).orElse(null);
    }

    @Override
    public Page<AdminDTO> getAdminList(boolean status, String search, String sort, int page, int limit) {
        if (limit < 1) throw new InvalidParameterException("Page size must not be less than one!");
        if (page < 0) throw new InvalidParameterException("Page number must not be less than zero!");

        List<Sort.Order> order = new ArrayList<>();
        Set<String> sourceFieldList = pagingService.getAllFields(Admin.class);
        String[] subSort = sort.split(",");
        if (pagingService.checkPropertPresent(sourceFieldList, subSort[0])) {
            order.add(new Sort.Order(pagingService.getSortDirection(subSort[1]), subSort[0]));
        } else {
            throw new InvalidParameterException(subSort[0] + " is not a propertied of Admin!");
        }

        Pageable pageable = PageRequest.of(page, limit).withSort(Sort.by(order));
        Page<Admin> pageResult = adminRepository.getAdminList(status, search, pageable);

        return new PageImpl<>(pageResult.getContent().stream()
                .map(AdminMapper.INSTANCE::toDTO)
                .collect(Collectors.toList()), pageResult.getPageable(), pageResult.getTotalElements());
    }
}
