package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.dto.state.CreateStateDTO;
import com.thumbsup.thumbsup.dto.state.StateDTO;
import com.thumbsup.thumbsup.entity.Order;
import com.thumbsup.thumbsup.entity.State;
import com.thumbsup.thumbsup.entity.StateDetail;
import com.thumbsup.thumbsup.mapper.StateMapper;
import com.thumbsup.thumbsup.repository.OrderRepository;
import com.thumbsup.thumbsup.repository.StateDetailRepository;
import com.thumbsup.thumbsup.repository.StateRepository;
import com.thumbsup.thumbsup.service.interfaces.IPagingService;
import com.thumbsup.thumbsup.service.interfaces.IStateService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StateService implements IStateService {

    private final IPagingService pagingService;

    private final StateRepository stateRepository;

    private final OrderRepository orderRepository;

    private final StateDetailRepository stateDetailRepository;

    @Override
    public void createStateDetail(CreateStateDTO create) {
        Optional<Order> order = orderRepository.findByIdAndStatus(create.getOrderId(), true);
        if (order.isPresent()) {
            Optional<Long> stateId = order.get().getStateDetailList().stream().map(StateDetail::getState).filter(s
                    -> s.getStatus().equals(true)).map(State::getId).max(Comparator.naturalOrder());
            if (stateId.isPresent()) {
                if (stateId.get() < create.getStateId()) {
                    for (long i = stateId.get() + 1; i <= create.getStateId(); i++) {
                        StateDetail stateDetail = new StateDetail(null, LocalDateTime.now(), true,
                                stateRepository.findByIdAndStatus(i, true).orElse(null), order.get());
                        stateDetailRepository.save(stateDetail);
                    }
                } else {
                    throw new InvalidParameterException("Invalid state because the order has already gone through this state");
                }
            } else {
                StateDetail stateDetail = new StateDetail(null, LocalDateTime.now(), true,
                        stateRepository.getFirstState(true), order.get());
                stateDetailRepository.save(stateDetail);
            }
        } else {
            throw new InvalidParameterException("Invalid order");
        }
    }

    @Override
    public Page<StateDTO> getStateList(boolean status, String search, String sort, int page, int limit) {
        if (page < 0) throw new InvalidParameterException("Page number must not be less than zero!");
        if (limit < 1) throw new InvalidParameterException("Page size must not be less than one!");

        List<Sort.Order> order = new ArrayList<>();
        Set<String> sourceFieldList = pagingService.getAllFields(State.class);
        String[] subSort = sort.split(",");
        if (pagingService.checkPropertPresent(sourceFieldList, subSort[0])) {
            order.add(new Sort.Order(pagingService.getSortDirection(subSort[1]), subSort[0]));
        } else {
            throw new InvalidParameterException(subSort[0] + " is not a propertied of State");
        }

        Pageable pageable = PageRequest.of(page, limit).withSort(Sort.by(order));
        Page<State> pageResult = stateRepository.getStatesByStatus(status, search, pageable);

        return new PageImpl<>(pageResult.getContent().stream()
                .map(StateMapper.INSTANCE::toDTO)
                .collect(Collectors.toList()), pageResult.getPageable(), pageResult.getTotalElements());
    }

    @Override
    public StateDTO getStateById(long id) {
        return StateMapper.INSTANCE.toDTO(stateRepository.findByIdAndStatus(id, true).orElse(null));
    }
}
