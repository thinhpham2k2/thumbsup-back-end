package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.dto.payment.CreatePaymentAccountDTO;
import com.thumbsup.thumbsup.dto.payment.PaymentAccountDTO;
import com.thumbsup.thumbsup.entity.PaymentAccount;
import com.thumbsup.thumbsup.entity.Store;
import com.thumbsup.thumbsup.mapper.PaymentAccountMapper;
import com.thumbsup.thumbsup.repository.PaymentAccountRepository;
import com.thumbsup.thumbsup.repository.StoreRepository;
import com.thumbsup.thumbsup.service.interfaces.IPagingService;
import com.thumbsup.thumbsup.service.interfaces.IPaymentAccountService;
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
public class PaymentAccountService implements IPaymentAccountService {

    private final IPagingService pagingService;

    private final StoreRepository storeRepository;

    private final PaymentAccountRepository paymentAccountRepository;

    @Override
    public PaymentAccountDTO getPaymentById(long id) {
        return PaymentAccountMapper.INSTANCE.toDTO(paymentAccountRepository.findByIdAndStatus(id, true).orElse(null));
    }

    @Override
    public PaymentAccountDTO createPayment(CreatePaymentAccountDTO create) {
        Optional<Store> store = storeRepository.findStoreByStatusAndId(true, create.getStoreId());
        if (store.isPresent()) {
            store.get().setBalance(store.get().getBalance().add(create.getAmount()));
            storeRepository.save(store.get());
        } else {
            throw new InvalidParameterException("Invalid store");
        }
        PaymentAccount payment = paymentAccountRepository.save(PaymentAccountMapper.INSTANCE.createToEntity(create));
        return PaymentAccountMapper.INSTANCE.toDTO(payment);
    }

    @Override
    public Page<PaymentAccountDTO> getPaymentList(boolean status, List<Long> storeIds, String search, String sort, int page, int limit) {
        if (page < 0) throw new InvalidParameterException("Page number must not be less than zero!");
        if (limit < 1) throw new InvalidParameterException("Page size must not be less than one!");

        List<Sort.Order> order = new ArrayList<>();

        Set<String> sourceFieldList = pagingService.getAllFields(PaymentAccount.class);
        String[] subSort = sort.split(",");
        if (pagingService.checkPropertPresent(sourceFieldList, subSort[0])) {
            order.add(new Sort.Order(pagingService.getSortDirection(subSort[1]), transferProperty(subSort[0])));
        } else {
            throw new InvalidParameterException(subSort[0] + " is not a propertied of Payment");
        }

        Pageable pageable = PageRequest.of(page, limit).withSort(Sort.by(order));
        Page<PaymentAccount> pageResult = paymentAccountRepository.getPaymentList
                (status, storeIds, search, pageable);

        return new PageImpl<>(pageResult.getContent().stream()
                .map(PaymentAccountMapper.INSTANCE::toDTO)
                .collect(Collectors.toList()), pageResult.getPageable(), pageResult.getTotalElements());
    }

    @Override
    public Page<PaymentAccountDTO> getPaymentListByStoreId(boolean status, long storeId, String search, String sort, int page, int limit) {
        if (page < 0) throw new InvalidParameterException("Page number must not be less than zero!");
        if (limit < 1) throw new InvalidParameterException("Page size must not be less than one!");

        List<Sort.Order> order = new ArrayList<>();

        Set<String> sourceFieldList = pagingService.getAllFields(PaymentAccount.class);
        String[] subSort = sort.split(",");
        if (pagingService.checkPropertPresent(sourceFieldList, subSort[0])) {
            order.add(new Sort.Order(pagingService.getSortDirection(subSort[1]), transferProperty(subSort[0])));
        } else {
            throw new InvalidParameterException(subSort[0] + " is not a propertied of Payment");
        }

        Pageable pageable = PageRequest.of(page, limit).withSort(Sort.by(order));
        Page<PaymentAccount> pageResult = paymentAccountRepository.getPaymentListByStoreId
                (status, storeId, search, pageable);

        return new PageImpl<>(pageResult.getContent().stream()
                .map(PaymentAccountMapper.INSTANCE::toDTO)
                .collect(Collectors.toList()), pageResult.getPageable(), pageResult.getTotalElements());
    }

    private String transferProperty(String property) {
        if (property.equals("store")) {
            return "store.storeName";
        }
        return property;
    }
}
