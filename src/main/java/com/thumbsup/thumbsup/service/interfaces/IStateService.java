package com.thumbsup.thumbsup.service.interfaces;

import com.thumbsup.thumbsup.dto.state.CreateStateDTO;
import com.thumbsup.thumbsup.dto.state.StateDTO;
import org.springframework.data.domain.Page;

public interface IStateService {

    Page<StateDTO> getStateList(boolean status, String search, String sort, int page, int limit);

    StateDTO getStateById(long id);

    void createStateDetail(CreateStateDTO create);
}
