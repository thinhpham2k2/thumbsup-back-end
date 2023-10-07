package com.thumbsup.thumbsup.dto.state;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StateDetailDTO implements Serializable {
    private Long id;
    private LocalDateTime date;
    private Long stateId;
    private String stateName;
    private Long orderId;
    private Boolean status;
}
