package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.dto.transaction.TransactionOrderDTO;
import com.thumbsup.thumbsup.entity.TransactionOrder;
import com.thumbsup.thumbsup.mapper.TransactionOrderMapper;
import com.thumbsup.thumbsup.repository.TransactionOrderRepository;
import com.thumbsup.thumbsup.service.interfaces.IPagingService;
import com.thumbsup.thumbsup.service.interfaces.ITransactionOrderService;
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
public class TransactionOrderService implements ITransactionOrderService {

    private final IPagingService pagingService;

    private final TransactionOrderRepository transactionOrderRepository;

    @Override
    public Page<TransactionOrderDTO> getTransactionList(boolean status, List<Long> storeIds, String search, String sort, int page, int limit) {
        if (page < 0) throw new InvalidParameterException("Page number must not be less than zero!");
        if (limit < 1) throw new InvalidParameterException("Page size must not be less than one!");

        List<Sort.Order> order = new ArrayList<>();

        Set<String> sourceFieldList = pagingService.getAllFields(TransactionOrder.class);
        String[] subSort = sort.split(",");
        if (pagingService.checkPropertPresent(sourceFieldList, subSort[0])) {
            order.add(new Sort.Order(pagingService.getSortDirection(subSort[1]), transferProperty(subSort[0])));
        } else {
            throw new InvalidParameterException(subSort[0] + " is not a propertied of Transaction");
        }

        Pageable pageable = PageRequest.of(page, limit).withSort(Sort.by(order));
        Page<TransactionOrder> pageResult = transactionOrderRepository.getTransactionList(status, storeIds, search, pageable);

        return new PageImpl<>(pageResult.getContent().stream()
                .map(TransactionOrderMapper.INSTANCE::toDTO)
                .collect(Collectors.toList()), pageResult.getPageable(), pageResult.getTotalElements());
    }

    @Override
    public Page<TransactionOrderDTO> getTransactionListByStoreId(boolean status, long storeId, String search, String sort, int page, int limit) {
        if (page < 0) throw new InvalidParameterException("Page number must not be less than zero!");
        if (limit < 1) throw new InvalidParameterException("Page size must not be less than one!");

        List<Sort.Order> order = new ArrayList<>();

        Set<String> sourceFieldList = pagingService.getAllFields(TransactionOrder.class);
        String[] subSort = sort.split(",");
        if (pagingService.checkPropertPresent(sourceFieldList, subSort[0])) {
            order.add(new Sort.Order(pagingService.getSortDirection(subSort[1]), transferProperty(subSort[0])));
        } else {
            throw new InvalidParameterException(subSort[0] + " is not a propertied of Transaction");
        }

        Pageable pageable = PageRequest.of(page, limit).withSort(Sort.by(order));
        Page<TransactionOrder> pageResult = transactionOrderRepository.getTransactionListByStoreId(status, storeId, search, pageable);

        return new PageImpl<>(pageResult.getContent().stream()
                .map(TransactionOrderMapper.INSTANCE::toDTO)
                .collect(Collectors.toList()), pageResult.getPageable(), pageResult.getTotalElements());
    }

    private String transferProperty(String property) {
        if (property.equals("store")) {
            return "store.storeName";
        }
        return property;
    }

    @Override
    public TransactionOrderDTO getTransactionById(long id) {
        return TransactionOrderMapper.INSTANCE.toDTO(transactionOrderRepository.findByIdAndStatus(id, true).orElse(null));
    }
}
