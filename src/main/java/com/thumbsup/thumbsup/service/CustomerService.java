package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.dto.customer.CreateCustomerDTO;
import com.thumbsup.thumbsup.dto.customer.CustomerDTO;
import com.thumbsup.thumbsup.dto.customer.UpdateCustomerDTO;
import com.thumbsup.thumbsup.entity.Customer;
import com.thumbsup.thumbsup.mapper.CustomerMapper;
import com.thumbsup.thumbsup.repository.CustomerRepository;
import com.thumbsup.thumbsup.repository.StoreRepository;
import com.thumbsup.thumbsup.service.interfaces.ICustomerService;
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
public class CustomerService implements ICustomerService {

    private final IPagingService pagingService;

    private final StoreRepository storeRepository;

    private final CustomerRepository customerRepository;

    @Override
    public CustomerDTO createCustomer(CreateCustomerDTO create) {
        if (customerRepository.findCustomerByEmailAndStatus(create.getEmail(), true).isEmpty()
                && storeRepository.findStoreByEmailAndStatus(create.getEmail(), true).isEmpty()) {
            Customer customer = customerRepository.save(CustomerMapper.INSTANCE.createToEntity(create));
            return CustomerMapper.INSTANCE.toDTO(customer);
        } else {
            throw new InvalidParameterException("Email is already in use");
        }
    }

    @Override
    public CustomerDTO updateCustomer(UpdateCustomerDTO update, Long id) {
        Optional<Customer> customer = customerRepository.findCustomerByIdAndStatus(id, true);
        if (customer.isPresent()) {
            if (customer.get().getEmail().equals(update.getEmail())) {
                Customer cus = customerRepository.save(CustomerMapper.INSTANCE.updateToEntity(update, customer.get()));
                return CustomerMapper.INSTANCE.toDTO(cus);
            } else if (customerRepository.findCustomerByEmailAndStatus(update.getEmail(), true).isEmpty()
                    && storeRepository.findStoreByEmailAndStatus(update.getEmail(), true).isEmpty()) {
                Customer cus = customerRepository.save(CustomerMapper.INSTANCE.updateToEntity(update, customer.get()));
                return CustomerMapper.INSTANCE.toDTO(cus);
            } else {
                throw new InvalidParameterException("Email is already in use");
            }
        } else {
            throw new InvalidParameterException("Not found customer");
        }
    }

    @Override
    public void deleteCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findCustomerByIdAndStatus(id, true);
        if (customer.isPresent()) {
            customer.get().setStatus(false);
            customerRepository.save(customer.get());
        } else {
            throw new InvalidParameterException("Not found customer");
        }
    }

    @Override
    public boolean checkByUsername(String userName) {
        return customerRepository.findCustomerByUserNameAndStatus(userName, true).isEmpty();
    }

    @Override
    public Page<CustomerDTO> getCustomerList(boolean status, List<Long> cityIds, String search, String sort, int page, int limit) {
        if (limit < 1) throw new InvalidParameterException("Page size must not be less than one!");
        if (page < 0) throw new InvalidParameterException("Page number must not be less than zero!");

        List<Sort.Order> order = new ArrayList<>();
        Set<String> sourceFieldList = pagingService.getAllFields(Customer.class);
        String[] subSort = sort.split(",");
        if (pagingService.checkPropertPresent(sourceFieldList, subSort[0])) {
            order.add(new Sort.Order(pagingService.getSortDirection(subSort[1]), transferProperty(subSort[0])));
        } else {
            throw new InvalidParameterException(subSort[0] + " is not a propertied of Customer!");
        }

        Pageable pageable = PageRequest.of(page, limit).withSort(Sort.by(order));
        Page<Customer> pageResult = customerRepository.getCustomerList(status, cityIds, search, pageable);

        return new PageImpl<>(pageResult.getContent().stream()
                .map(CustomerMapper.INSTANCE::toDTO)
                .collect(Collectors.toList()), pageResult.getPageable(), pageResult.getTotalElements());
    }

    @Override
    public CustomerDTO getCustomerById(long customerId, boolean status) {
        Optional<Customer> customer = customerRepository.findCustomerByIdAndStatus(customerId, status);
        return customer.map(CustomerMapper.INSTANCE::toDTO).orElse(null);
    }

    private static String transferProperty(String property) {
        if (property.equals("city")) {
            return "city.cityName";
        }
        return property;
    }
}
