package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.dto.order.CreateOrderDTO;
import com.thumbsup.thumbsup.dto.order.CreateOrderDetailDTO;
import com.thumbsup.thumbsup.dto.order.OrderDTO;
import com.thumbsup.thumbsup.entity.*;
import com.thumbsup.thumbsup.mapper.OrderDetailMapper;
import com.thumbsup.thumbsup.mapper.OrderMapper;
import com.thumbsup.thumbsup.repository.*;
import com.thumbsup.thumbsup.service.interfaces.IOrderService;
import com.thumbsup.thumbsup.service.interfaces.IPagingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
public class OrderService implements IOrderService {

    private final IPagingService pagingService;

    private final StoreRepository storeRepository;

    private final StateRepository stateRepository;

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final OrderDetailRepository detailRepository;

    private final StateDetailRepository stateDetailRepository;

    private final TransactionOrderRepository transactionOrderRepository;

    @Override
    public void createOrder(CreateOrderDTO create, boolean isPaid, String token) {
        if (create.getAmount().compareTo(create.getOrderDetailList().stream().map(CreateOrderDetailDTO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)) == 0) {
            Store store = new Store();
            for (CreateOrderDetailDTO d : create.getOrderDetailList()) {
                Optional<Product> p = productRepository.getProductByStatusAndId(true, d.getProductId());
                if (p.isPresent()) {
                    store = p.get().getStore();
                    if (p.get().getQuantity() < d.getQuantity()) {
                        throw new InvalidParameterException("Quantity of the " + p.get().getProductName() + " is not enough");
                    }
                }
            }

            try {
                Order order = orderRepository.save(OrderMapper.INSTANCE.createToEntity(create));

                for (CreateOrderDetailDTO d : create.getOrderDetailList()) {
                    OrderDetail detail = OrderDetailMapper.INSTANCE.createToEntity(d);
                    detail.setOrder(order);
                    detailRepository.save(detail);

                    Optional<Product> product = productRepository.getProductByStatusAndId(true, d.getProductId());
                    if (product.isPresent() && product.get().getQuantity() >= d.getQuantity()) {
                        product.get().setQuantity(product.get().getQuantity() - d.getQuantity());
                        productRepository.save(product.get());
                    }
                }

                StateDetail stateDetail = new StateDetail(null, LocalDateTime.now(), true,
                        stateRepository.getFirstState(true), order);
                stateDetailRepository.save(stateDetail);

                BigDecimal amount;
                if (isPaid) {
                    amount = order.getAmount().multiply(new BigDecimal("0.95"));

                    transactionOrderRepository.save(new TransactionOrder(null, token, amount, LocalDateTime.now(),
                            true, true, order, store));

                    store.setBalance(store.getBalance().add(amount));
                    storeRepository.save(store);
                }
            } catch (Exception e) {
                throw new InvalidParameterException("Create order fail");
            }
        } else {
            throw new InvalidParameterException("Invalid order's amount");
        }
    }

    @Override
    public OrderDTO getOrderByIdForCustomer(long id, long customerId) {
        return OrderMapper.INSTANCE.toDTO(orderRepository.findByIdAndCustomer_IdAndStatus(id, customerId, true).orElse(null));
    }

    @Override
    public OrderDTO getOrderByIdForStore(long id, long storeId) {
        return OrderMapper.INSTANCE.toDTO(orderRepository.getOrderByIdAndStore(id, true, storeId).orElse(null));
    }

    @Override
    public OrderDTO getOrderById(long id) {
        return OrderMapper.INSTANCE.toDTO(orderRepository.findByIdAndStatus(id, true).orElse(null));
    }

    @Override
    public Page<OrderDTO> getOrderList(boolean status, List<Long> customerIds, List<Long> stateIds, List<Long> storeIds,
                                       String search, String sort, int page, int limit) {
        if (limit < 1) throw new InvalidParameterException("Page size must not be less than one!");
        if (page < 0) throw new InvalidParameterException("Page number must not be less than zero!");

        List<Sort.Order> order = new ArrayList<>();
        Set<String> sourceFieldList = pagingService.getAllFields(Order.class);
        String[] subSort = sort.split(",");
        if (pagingService.checkPropertPresent(sourceFieldList, subSort[0])) {
            order.add(new Sort.Order(pagingService.getSortDirection(subSort[1]), transferProperty(subSort[0])));
        } else if (subSort[0].equals("stateCurrent")) {
            order.addAll(sortCustom(pagingService.getSortDirection(subSort[1])));
        } else {
            throw new InvalidParameterException(subSort[0] + " is not a propertied of Order");
        }

        Pageable pageable = PageRequest.of(page, limit).withSort(Sort.by(order));
        Page<Order> pageResult = orderRepository.getOrderList
                (status, customerIds, stateIds, storeIds, search, pageable);

        return new PageImpl<>(pageResult.getContent().stream()
                .map(OrderMapper.INSTANCE::toDTO)
                .collect(Collectors.toList()), pageResult.getPageable(), pageResult.getTotalElements());
    }

    private String transferProperty(String property) {
        if (property.equals("customer")) {
            return "customer.fullName";
        }
        return property;
    }

    private List<Sort.Order> sortCustom(Sort.Direction direction) {
        return JpaSort.unsafe(direction, "(SELECT MAX(s.state.id) FROM StateDetail s WHERE s.order = o)")
                .stream().toList();
    }
}
