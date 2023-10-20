package com.thumbsup.thumbsup.dto.request;

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
public class RequestDTO implements Serializable {
    private Long id;
    private BigDecimal amount;
    private String method;
    private String accountNumber;
    private String bankName;
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
