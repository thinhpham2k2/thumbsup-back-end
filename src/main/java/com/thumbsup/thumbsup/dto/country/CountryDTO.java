package com.thumbsup.thumbsup.dto.country;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryDTO implements Serializable {
    private Long id;
    private String country;
    private String description;
    private Boolean status;
}
