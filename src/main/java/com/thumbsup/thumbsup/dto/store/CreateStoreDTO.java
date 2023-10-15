package com.thumbsup.thumbsup.dto.store;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thumbsup.thumbsup.validation.interfaces.CityConstraint;
import com.thumbsup.thumbsup.validation.interfaces.EmailConstraint;
import com.thumbsup.thumbsup.validation.interfaces.UsernameConstraint;
import jakarta.validation.constraints.AssertTrue;
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
@JsonIgnoreProperties(value = { "passwordMatching" })
public class CreateStoreDTO implements Serializable {
    @UsernameConstraint
    private String userName;

    @NotNull(message = "Password is required")
    @Size(min = 2, max = 100, message = "The length of password is from 2 to 100 characters")
    private String password;

    private String passwordConfirmed;

    @NotNull(message = "Store name is required")
    @Size(min = 2, max = 255, message = "The length of store name is from 2 to 255 characters")
    private String storeName;

    @EmailConstraint
    private String email;

    @NotNull(message = "Phone is required")
    @Size(min = 7, max = 20, message = "The length of phone is from 8 to 20 characters")
    private String phone;

    private MultipartFile logo;

    private MultipartFile coverPhoto;

    @NotNull(message = "Address is required")
    @Size(min = 5, max = 4000, message = "The length of address is from 5 to 4000 characters")
    private String address;

    @NotNull(message = "Opening hours is required")
    private Time openingHours;

    @NotNull(message = "Closing hours is required!")
    private Time closingHours;

    @NotNull(message = "Description is required")
    @Size(min = 5, max = 4000, message = "The length of description is from 5 to 4000 characters")
    private String description;

    @CityConstraint
    @NotNull(message = "City's id is required")
    private Long cityId;

    @AssertTrue(message = "Confirmed password does not match the password")
    public boolean isPasswordMatching() {
        return password.equals(passwordConfirmed);
    }
}
