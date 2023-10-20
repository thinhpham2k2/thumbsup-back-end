package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.dto.state.StateDTO;
import com.thumbsup.thumbsup.entity.State;
import com.thumbsup.thumbsup.mapper.StateMapper;
import com.thumbsup.thumbsup.repository.StateRepository;
import com.thumbsup.thumbsup.service.interfaces.IPagingService;
import com.thumbsup.thumbsup.service.interfaces.IStateService;
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
public class StateService implements IStateService {

    private final IPagingService pagingService;

    private final StateRepository stateRepository;

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
