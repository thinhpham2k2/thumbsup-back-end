package com.thumbsup.thumbsup.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.thumbsup.thumbsup.entity.Store;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

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
    private Boolean state;
}
