package com.thumbsup.thumbsup.dto.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreExtraDTO implements Serializable {
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
    private Integer numOfFollowing;
    private Integer numOfRating;
    private BigDecimal rating;
    private Boolean favor;
    private List<String> cateList;
    private Boolean status;
}
