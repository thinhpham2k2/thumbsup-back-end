package com.thumbsup.thumbsup.dto.state;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StateDTO implements Serializable {
    private Long id;
    private String state;
    private String description;
    private Boolean status;
}
