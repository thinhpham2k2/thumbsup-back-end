package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.common.Common;
import com.thumbsup.thumbsup.dto.ads.AdvertisementDTO;
import com.thumbsup.thumbsup.dto.ads.CreateAdvertisementDTO;
import com.thumbsup.thumbsup.dto.store.StoreExtraDTO;
import com.thumbsup.thumbsup.entity.Advertisement;
import com.thumbsup.thumbsup.entity.Category;
import com.thumbsup.thumbsup.entity.Customer;
import com.thumbsup.thumbsup.entity.Store;
import com.thumbsup.thumbsup.mapper.AdvertisementMapper;
import com.thumbsup.thumbsup.mapper.StoreMapper;
import com.thumbsup.thumbsup.repository.*;
import com.thumbsup.thumbsup.service.interfaces.IAdvertisementService;
import com.thumbsup.thumbsup.service.interfaces.IPagingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AdvertisementService implements IAdvertisementService {

    private final IPagingService pagingService;

    private final StoreRepository storeRepository;

    private final ReviewRepository reviewRepository;

    private final CustomerRepository customerRepository;

    private final CategoryRepository categoryRepository;

    private final AdvertisementRepository advertisementRepository;

    private final WishlistStoreRepository wishlistStoreRepository;

    @Override
    public AdvertisementDTO createAdvertisement(CreateAdvertisementDTO create) {
        Optional<Store> store = storeRepository.findStoreByIdAndStatus(create.getStoreId(), true);
        if (store.isPresent()) {
            BigDecimal balance = store.get().getBalance().subtract(create.getPrice());
            if(balance.compareTo(new BigDecimal("0")) >= 0){
                store.get().setBalance(balance);
                storeRepository.save(store.get());
            } else {
                throw new InvalidParameterException("Store's balance is not enough");
            }
        } else {
            throw new InvalidParameterException("Invalid store");
        }
        Advertisement ads = advertisementRepository.save(AdvertisementMapper.INSTANCE.createToEntity(create));
        return AdvertisementMapper.INSTANCE.toDTO(ads);
    }

    @Override
    public AdvertisementDTO getAdvertisementById(long id) {
        return AdvertisementMapper.INSTANCE.toDTO(advertisementRepository.findByIdAndStatus(id, true).orElse(null));
    }

    @Override
    public Page<AdvertisementDTO> getAdvertisementListByStoreId(boolean status, long storeId, LocalDateTime dateNow,
                                                                String search, String sort, int page, int limit) {
        if (page < 0) throw new InvalidParameterException("Page number must not be less than zero!");
        if (limit < 1) throw new InvalidParameterException("Page size must not be less than one!");

        List<Sort.Order> order = new ArrayList<>();

        Set<String> sourceFieldList = pagingService.getAllFields(Advertisement.class);
        String[] subSort = sort.split(",");
        if (pagingService.checkPropertPresent(sourceFieldList, subSort[0])) {
            order.add(new Sort.Order(pagingService.getSortDirection(subSort[1]), transferProperty(subSort[0])));
        } else {
            throw new InvalidParameterException(subSort[0] + " is not a propertied of Advertisement");
        }

        Pageable pageable = PageRequest.of(page, limit).withSort(Sort.by(order));
        Page<Advertisement> pageResult = advertisementRepository.getAdvertisementListByStoreId(status, dateNow, storeId,
                search, pageable);

        return new PageImpl<>(pageResult.getContent().stream()
                .map(AdvertisementMapper.INSTANCE::toDTO)
                .collect(Collectors.toList()), pageResult.getPageable(), pageResult.getTotalElements());
    }

    @Override
    public Page<StoreExtraDTO> getAdvertisementStoreList(boolean status, List<Long> storeIds, LocalDateTime dateNow,
                                                         String search, String sort, int page, int limit) {
        if (page < 0) throw new InvalidParameterException("Page number must not be less than zero!");
        if (limit < 1) throw new InvalidParameterException("Page size must not be less than one!");

        List<Sort.Order> order = new ArrayList<>();

        Set<String> sourceFieldList = pagingService.getAllFields(Advertisement.class);
        String[] subSort = sort.split(",");
        if (pagingService.checkPropertPresent(sourceFieldList, subSort[0])) {
            order.add(new Sort.Order(pagingService.getSortDirection(subSort[1]), transferProperty(subSort[0])));
        } else {
            throw new InvalidParameterException(subSort[0] + " is not a propertied of Advertisement");
        }

        Pageable pageable = PageRequest.of(page, limit).withSort(Sort.by(order));
        Page<Advertisement> pageResult = advertisementRepository.getAdvertisementList(status, dateNow, storeIds, search, pageable);

        String userName = Common.userName;

        List<StoreExtraDTO> storeExtraList = pageResult.stream().filter(s -> s.getState().equals(true)).map(a
                -> StoreMapper.INSTANCE.toExtraDTO(a.getStore())).toList();

        storeExtraList = storeExtraList.stream().peek(s -> {
            s.setNumOfFollowing(wishlistStoreRepository.countAllByStatusAndStoreId(true, s.getId()).orElse(0));
            int count = reviewRepository.countAllByProductStoreIdAndStatus(s.getId(), true).orElse(0);
            s.setNumOfRating(count);
            s.setRating(count == 0 ? BigDecimal.ZERO : reviewRepository.sumRatingReviewByStore(true, s.getId())
                    .orElse(BigDecimal.ZERO).divide(new BigDecimal(count), 2, RoundingMode.HALF_DOWN));
            s.setCateList(categoryRepository.getDistinctByProductListStoreIdAndProductListStatusAndStatus(s.getId(),
                    true, true).stream().map(Category::getCategory).toList());
        }).toList();

        if (Common.role.equals("Customer")) {
            Optional<Customer> customer = customerRepository.findCustomerByUserNameAndStatus(userName, true);
            if (customer.isPresent()) {
                storeExtraList = storeExtraList.stream()
                        .peek(s -> s.setFavor(wishlistStoreRepository.existsByStatusAndCustomerIdAndStoreId(true,
                                customer.get().getId(), s.getId())))
                        .toList();
            }
        }

        return new PageImpl<>(storeExtraList, pageResult.getPageable(), pageResult.getTotalElements());
    }

    @Override
    public Page<AdvertisementDTO> getAdvertisementList(boolean status, List<Long> storeIds, LocalDateTime dateNow,
                                                       String search, String sort, int page, int limit) {
        if (page < 0) throw new InvalidParameterException("Page number must not be less than zero!");
        if (limit < 1) throw new InvalidParameterException("Page size must not be less than one!");

        List<Sort.Order> order = new ArrayList<>();

        Set<String> sourceFieldList = pagingService.getAllFields(Advertisement.class);
        String[] subSort = sort.split(",");
        if (pagingService.checkPropertPresent(sourceFieldList, subSort[0])) {
            order.add(new Sort.Order(pagingService.getSortDirection(subSort[1]), transferProperty(subSort[0])));
        } else {
            throw new InvalidParameterException(subSort[0] + " is not a propertied of Advertisement");
        }

        Pageable pageable = PageRequest.of(page, limit).withSort(Sort.by(order));
        Page<Advertisement> pageResult = advertisementRepository.getAdvertisementList(status, dateNow, storeIds, search, pageable);

        return new PageImpl<>(pageResult.getContent().stream()
                .map(AdvertisementMapper.INSTANCE::toDTO)
                .collect(Collectors.toList()), pageResult.getPageable(), pageResult.getTotalElements());
    }

    private String transferProperty(String property) {
        if (property.equals("store")) {
            return "store.storeName";
        }
        return property;
    }
}
