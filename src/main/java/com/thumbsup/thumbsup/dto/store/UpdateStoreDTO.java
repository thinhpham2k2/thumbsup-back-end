package com.thumbsup.thumbsup.dto.store;

import com.thumbsup.thumbsup.validation.interfaces.EmailConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.sql.Time;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStoreDTO implements Serializable {
    @NotNull(message = "Store name is required!")
    @Size(min = 2, max = 255, message = "The length of store name is from 2 to 255 characters")
    private String storeName;

    @EmailConstraint
    private String email;

    @NotNull(message = "Phone is required!")
    @Size(min = 7, max = 20, message = "The length of phone is from 8 to 20 characters")
    private String phone;

    private MultipartFile logo;

    private MultipartFile coverPhoto;

    @NotNull(message = "Address is required!")
    @Size(min = 5, max = 4000, message = "The length of address is from 5 to 4000 characters")
    private String address;

    @NotNull(message = "Opening hours is required!")
    private Time openingHours;

    @NotNull(message = "Closing hours is required!")
    private Time closingHours;

    @NotNull(message = "Description is required!")
    @Size(min = 5, max = 4000, message = "The length of description is from 5 to 4000 characters")
    private String description;

    @NotNull(message = "City's id is required!")
    private Long cityId;
}
