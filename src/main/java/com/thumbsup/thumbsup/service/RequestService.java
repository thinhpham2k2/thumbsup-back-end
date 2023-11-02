package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.dto.request.CreateRequestDTO;
import com.thumbsup.thumbsup.dto.request.RequestDTO;
import com.thumbsup.thumbsup.dto.request.UpdateRequestDTO;
import com.thumbsup.thumbsup.entity.Request;
import com.thumbsup.thumbsup.mapper.RequestMapper;
import com.thumbsup.thumbsup.repository.RequestRepository;
import com.thumbsup.thumbsup.service.interfaces.IPagingService;
import com.thumbsup.thumbsup.service.interfaces.IRequestService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.ast.tree.expression.Collation;
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
public class RequestService implements IRequestService {

    private final IPagingService pagingService;

    private final RequestRepository requestRepository;

    @Override
    public void deleteRequest(long id) {
        Optional<Request> request = requestRepository.findByIdAndStatus(id, true);
        if (request.isPresent()) {
            request.get().setStatus(false);
            requestRepository.save(request.get());
        } else {
            throw new InvalidParameterException("Not found request");
        }
    }

    @Override
    public RequestDTO createRequest(CreateRequestDTO create) {
        if (requestRepository.existsByStatusAndStore_Id(true, create.getStoreId())) {
            Request request = requestRepository.save(RequestMapper.INSTANCE.createToEntity(create));
            return RequestMapper.INSTANCE.toDTO(request);
        } else {
            throw new InvalidParameterException("Request for this store already exists");
        }
    }

    @Override
    public RequestDTO updateRequest(long id, UpdateRequestDTO update) {
        Optional<Request> request = requestRepository.findByIdAndStatus(id, true);
        if (request.isPresent()) {
            Request request1 = requestRepository.save(RequestMapper.INSTANCE.updateToEntity(update, request.get()));
            return RequestMapper.INSTANCE.toDTO(request1);
        } else {
            throw new InvalidParameterException("Not found request");
        }
    }

    @Override
    public Page<RequestDTO> getRequestList(boolean status, List<Long> storeIds, String search, String sort, int page, int limit) {
        if (page < 0) throw new InvalidParameterException("Page number must not be less than zero!");
        if (limit < 1) throw new InvalidParameterException("Page size must not be less than one!");

        List<Sort.Order> order = new ArrayList<>();

        Set<String> sourceFieldList = pagingService.getAllFields(Request.class);
        String[] subSort = sort.split(",");
        if (pagingService.checkPropertPresent(sourceFieldList, subSort[0])) {
            order.add(new Sort.Order(pagingService.getSortDirection(subSort[1]), transferProperty(subSort[0])));
        } else {
            throw new InvalidParameterException(subSort[0] + " is not a propertied of Request");
        }

        Pageable pageable = PageRequest.of(page, limit).withSort(Sort.by(order));
        Page<Request> pageResult = requestRepository.getRequestList
                (status, storeIds, search, pageable, new Collation("utf8mb4_0900_ai_ci"));

        return new PageImpl<>(pageResult.getContent().stream()
                .map(RequestMapper.INSTANCE::toDTO)
                .collect(Collectors.toList()), pageResult.getPageable(), pageResult.getTotalElements());
    }

    @Override
    public Page<RequestDTO> getRequestListByStoreId(boolean status, long storeId, String search, String sort, int page, int limit) {
        if (page < 0) throw new InvalidParameterException("Page number must not be less than zero!");
        if (limit < 1) throw new InvalidParameterException("Page size must not be less than one!");

        List<Sort.Order> order = new ArrayList<>();

        Set<String> sourceFieldList = pagingService.getAllFields(Request.class);
        String[] subSort = sort.split(",");
        if (pagingService.checkPropertPresent(sourceFieldList, subSort[0])) {
            order.add(new Sort.Order(pagingService.getSortDirection(subSort[1]), transferProperty(subSort[0])));
        } else {
            throw new InvalidParameterException(subSort[0] + " is not a propertied of Request");
        }

        Pageable pageable = PageRequest.of(page, limit).withSort(Sort.by(order));
        Page<Request> pageResult = requestRepository.getRequestListByStoreId
                (status, storeId, search, pageable, new Collation("utf8mb4_0900_ai_ci"));

        return new PageImpl<>(pageResult.getContent().stream()
                .map(RequestMapper.INSTANCE::toDTO)
                .collect(Collectors.toList()), pageResult.getPageable(), pageResult.getTotalElements());
    }

    private String transferProperty(String property) {
        if (property.equals("store")) {
            return "store.storeName";
        }
        return property;
    }

    @Override
    public RequestDTO getRequestById(long id) {
        return RequestMapper.INSTANCE.toDTO(requestRepository.findByIdAndStatus(id, true).orElse(null));
    }
}
