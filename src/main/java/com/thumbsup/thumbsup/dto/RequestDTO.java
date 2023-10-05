package com.thumbsup.thumbsup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {
    private Long id;
    private BigDecimal amount;
    private String note;
    private LocalDateTime dateCreated;
    private LocalDateTime dateAccept;
    private Long storeId;
    private String storeName;
    private String storeLogo;
    private String storeImageCover;
    private Boolean state;
    private Boolean status;
}
