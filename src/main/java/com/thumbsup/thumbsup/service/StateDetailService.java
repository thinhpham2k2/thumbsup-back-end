package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.repository.StateDetailRepository;
import com.thumbsup.thumbsup.service.interfaces.IStateDetailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class StateDetailService implements IStateDetailService {
    private final StateDetailRepository stateDetailRepository;
}
