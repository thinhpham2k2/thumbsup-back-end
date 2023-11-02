package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.common.Common;
import com.thumbsup.thumbsup.dto.store.CreateStoreDTO;
import com.thumbsup.thumbsup.dto.store.StoreDTO;
import com.thumbsup.thumbsup.dto.store.StoreExtraDTO;
import com.thumbsup.thumbsup.dto.store.UpdateStoreDTO;
import com.thumbsup.thumbsup.entity.Category;
import com.thumbsup.thumbsup.entity.Customer;
import com.thumbsup.thumbsup.entity.Store;
import com.thumbsup.thumbsup.mapper.StoreMapper;
import com.thumbsup.thumbsup.repository.*;
import com.thumbsup.thumbsup.service.interfaces.IFileService;
import com.thumbsup.thumbsup.service.interfaces.IPagingService;
import com.thumbsup.thumbsup.service.interfaces.IStoreService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreService implements IStoreService {

    private final IFileService fileService;

    private final IPagingService pagingService;

    private final StoreRepository storeRepository;

    private final PasswordEncoder passwordEncoder;

    private final ReviewRepository reviewRepository;

    private final CustomerRepository customerRepository;

    private final CategoryRepository categoryRepository;

    private final WishlistStoreRepository wishlistStoreRepository;

    @Override
    public StoreExtraDTO createStore(CreateStoreDTO create) {
        if (customerRepository.findCustomerByEmailAndStatus(create.getEmail(), true).isEmpty()
                && storeRepository.findStoreByEmailAndStatus(create.getEmail(), true).isEmpty()) {
            Store store = StoreMapper.INSTANCE.createToEntity(create);

            //Validate Logo Image
            String linkLogo = "";
            if (create.getLogo() != null) {
                try {
                    linkLogo = fileService.upload(create.getLogo());
                } catch (Exception e) {
                    throw new InvalidParameterException("Invalid logo image file");
                }
            }

            //Validate Cover Photo Image
            String linkPhoto = "";
            if (create.getCoverPhoto() != null) {
                try {
                    linkPhoto = fileService.upload(create.getCoverPhoto());
                } catch (Exception e) {
                    throw new InvalidParameterException("Invalid cover photo image file");
                }
            }

            store.setLogo(linkLogo);
            store.setCoverPhoto(linkPhoto);
            store.setPassword(passwordEncoder.encode(store.getPassword()));
            return entityToExtra(storeRepository.save(store));
        } else {
            throw new InvalidParameterException("Email is already in use");
        }
    }

    @Override
    public StoreExtraDTO updateStore(UpdateStoreDTO update, Long id) {
        Optional<Store> store = storeRepository.findStoreByStatusAndId(true, id);
        if (store.isPresent()) {

            //Validate Image
            String linkLogo;
            if (update.getLogo() == null) {
                linkLogo = store.get().getLogo();
            } else {
                try {
                    linkLogo = fileService.upload(update.getLogo());
                } catch (Exception e) {
                    throw new InvalidParameterException("Invalid logo image file");
                }
            }

            //Validate Image
            String linkPhoto;
            if (update.getLogo() == null) {
                linkPhoto = store.get().getCoverPhoto();
            } else {
                try {
                    linkPhoto = fileService.upload(update.getCoverPhoto());
                } catch (Exception e) {
                    throw new InvalidParameterException("Invalid cover photo image file");
                }
            }

            if (store.get().getEmail().equals(update.getEmail())) {
                Store st = StoreMapper.INSTANCE.updateToEntity(update, store.get());
                st.setLogo(linkLogo);
                st.setCoverPhoto(linkPhoto);
                return entityToExtra(storeRepository.save(st));
            } else if (customerRepository.findCustomerByEmailAndStatus(update.getEmail(), true).isEmpty()
                    && storeRepository.findStoreByEmailAndStatus(update.getEmail(), true).isEmpty()) {
                Store st = StoreMapper.INSTANCE.updateToEntity(update, store.get());
                st.setLogo(linkLogo);
                st.setCoverPhoto(linkPhoto);
                return entityToExtra(storeRepository.save(st));
            } else {
                throw new InvalidParameterException("Email is already in use");
            }
        } else {
            throw new InvalidParameterException("Not found store");
        }
    }

    @Override
    public void deleteStore(Long id) {
        Optional<Store> store = storeRepository.findStoreByStatusAndId(true, id);
        if (store.isPresent()) {
            store.get().setStatus(false);
            storeRepository.save(store.get());
        } else {
            throw new InvalidParameterException("Not found store");
        }
    }

    @Override
    public StoreExtraDTO entityToExtra(Store entity) {
        StoreExtraDTO storeExtraDTO = StoreMapper.INSTANCE.toExtraDTO(entity);

        if (Common.role.equals("Customer")) {
            Optional<Customer> customer = customerRepository.findCustomerByUserNameAndStatus(Common.userName, true);
            customer.ifPresent(value -> storeExtraDTO.setFavor(wishlistStoreRepository.existsByStatusAndCustomerIdAndStoreId(true, value.getId(), storeExtraDTO.getId())));
        }

        storeExtraDTO.setNumOfFollowing(wishlistStoreRepository.countAllByStatusAndStoreId(true, storeExtraDTO.getId()).orElse(0));
        int count = reviewRepository.countAllByProductStoreIdAndStatus(storeExtraDTO.getId(), true).orElse(0);
        storeExtraDTO.setNumOfRating(count);
        storeExtraDTO.setRating(count == 0 ? BigDecimal.ZERO : reviewRepository.sumRatingReviewByStore(true, storeExtraDTO.getId()).orElse(BigDecimal.ZERO)
                .divide(new BigDecimal(count), 2, RoundingMode.HALF_DOWN));
        storeExtraDTO.setCateList(categoryRepository
                .getDistinctByProductListStoreIdAndProductListStatusAndStatus(storeExtraDTO.getId(), true, true)
                .stream().map(Category::getCategory).toList());

        return storeExtraDTO;
    }

    @Override
    public boolean checkByUsername(String userName) {
        return storeRepository.findStoreByUserNameAndStatus(userName, true).isEmpty();
    }

    @Override
    public Page<StoreDTO> getStoreList(boolean status, List<Long> cityIds, String search, String sort, int page, int limit) {
        if (page < 0) throw new InvalidParameterException("Page number must not be less than zero!");
        if (limit < 1) throw new InvalidParameterException("Page size must not be less than one!");

        List<Sort.Order> order = new ArrayList<>();
        Set<String> sourceFieldList = pagingService.getAllFields(Store.class);
        String[] subSort = sort.split(",");
        if (pagingService.checkPropertPresent(sourceFieldList, subSort[0])) {
            order.add(new Sort.Order(pagingService.getSortDirection(subSort[1]), transferProperty(subSort[0])));
        } else {
            throw new InvalidParameterException(subSort[0] + " is not a propertied of Store");
        }

        Pageable pageable = PageRequest.of(page, limit).withSort(Sort.by(order));
        Page<Store> pageResult = storeRepository.getStoreList(status, cityIds, search, pageable);

        return new PageImpl<>(pageResult.getContent().stream()
                .map(StoreMapper.INSTANCE::toDTO)
                .collect(Collectors.toList()), pageResult.getPageable(), pageResult.getTotalElements());
    }

    @Override
    public StoreExtraDTO getStoreById(boolean status, long storeId) {
        Optional<Store> store = storeRepository.findStoreByStatusAndId(status, storeId);
        return store.map(this::entityToExtra).orElse(null);
    }

    private String transferProperty(String property) {
        if (property.equals("city")) {
            return "city.cityName";
        }
        return property;
    }
}
