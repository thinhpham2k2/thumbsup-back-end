package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.repository.RequestRepository;
import com.thumbsup.thumbsup.service.interfaces.IPagingService;
import com.thumbsup.thumbsup.service.interfaces.IRequestService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RequestService implements IRequestService {

    private final IPagingService pagingService;

    private final RequestRepository requestRepository;

    @Override
    public String hello() {
        return "RequestService";
    }
}
