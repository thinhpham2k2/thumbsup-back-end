package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.repository.AdminRepository;
import com.thumbsup.thumbsup.service.interfaces.IAdminService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService implements IAdminService {
    private final AdminRepository adminRepository;
}
