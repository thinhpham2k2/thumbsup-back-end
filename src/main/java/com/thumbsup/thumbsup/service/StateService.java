package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.repository.StateRepository;
import com.thumbsup.thumbsup.service.interfaces.IStateService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class StateService implements IStateService {
    private final StateRepository stateRepository;
}
