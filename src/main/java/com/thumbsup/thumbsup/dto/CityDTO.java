package com.thumbsup.thumbsup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityDTO implements Serializable {
    private Long id;
    private String cityName;
    private String image;
    private String description;
    private Boolean status;
}
