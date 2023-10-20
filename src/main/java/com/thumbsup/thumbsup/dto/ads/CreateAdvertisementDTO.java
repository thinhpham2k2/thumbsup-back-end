package com.thumbsup.thumbsup.dto.ads;

import com.thumbsup.thumbsup.validation.interfaces.StoreConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdvertisementDTO implements Serializable {
    @NotNull(message = "Advertisement's name is required!")
    @Size(min = 2, max = 255, message = "The length of Advertisement's name is from 2 to 255 characters")
    private String adsName;

    @Range(min = 1, max = 100000000, message = "Price must be a positive number")
    private BigDecimal price;

    @Range(min = 1, max = 100000000, message = "Duration must be a positive number")
    private Integer duration;

    @NotNull(message = "Description is required!")
    @Size(min = 5, max = 4000, message = "The length of description is from 5 to 4000 characters")
    private String description;

    @StoreConstraint
    @NotNull(message = "Store's id is required")
    private Long storeId;

    private Boolean state;
}
