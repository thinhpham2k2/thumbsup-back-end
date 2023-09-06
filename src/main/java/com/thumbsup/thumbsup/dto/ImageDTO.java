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
public class ImageDTO implements Serializable {
    private Long id;
    private String url;
    private Boolean isCover;
    private Long productId;
    private String productName;
}
