package com.thumbsup.thumbsup.dto.ads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementDTO implements Serializable {
    private Long id;
    private String adsName;
    private BigDecimal price;
    private Integer duration;
    private LocalDateTime dateCreated;
    private LocalDateTime dateExpired;
    private Integer clickCount;
    private String description;
    private Long storeId;
    private String storeName;
    private String storeLogo;
    private String storeImageCover;
    private Boolean state;
    private Boolean status;
}
