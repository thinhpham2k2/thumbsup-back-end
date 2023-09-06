package com.thumbsup.thumbsup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDTO implements Serializable {
    private Long id;
    private String userName;
    private String storeName;
    private String email;
    private String phone;
    private String logo;
    private String coverPhoto;
    private String address;
    private BigDecimal balance;
    private LocalDate dateCreated;
    private Time openingHours;
    private Time closingHours;
    private String description;
    private Boolean state;
    private Long cityId;
    private String cityName;
    private Boolean status;
}
