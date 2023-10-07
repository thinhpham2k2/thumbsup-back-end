package com.thumbsup.thumbsup.service.interfaces;

import com.thumbsup.thumbsup.dto.admin.AdminDTO;
import org.springframework.data.domain.Page;

public interface IAdminService {

    AdminDTO getAdminById(long adminId, boolean status);

    Page<AdminDTO> getAdminList(boolean status, String search, String sort, int page, int limit);
}
