package com.thumbsup.thumbsup.dto.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thumbsup.thumbsup.validation.interfaces.BirthdayConstraint;
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
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = { "passwordMatching" })
public class CreateCustomerDTO implements Serializable {
    @NotNull(message = "User name is required")
    @UsernameConstraint
    private String userName;

    @NotNull(message = "Password is required")
    @Size(min = 2, max = 100, message = "The length of password is from 2 to 100 characters")
    private String password;

    private String passwordConfirmed;

    @NotNull(message = "Full name is required")
    @Size(min = 2, max = 255, message = "The length of full name is from 2 to 255 characters")
    private String fullName;

    @NotNull
    @EmailConstraint
    private String email;

    @NotNull(message = "Phone is required")
    @Size(min = 7, max = 20, message = "The length of phone is from 8 to 20 characters")
    private String phone;

    private MultipartFile avatar;

    @BirthdayConstraint
    @NotNull(message = "Date of birth is required")
    private LocalDate dob;

    @NotNull(message = "Address is required")
    @Size(min = 5, max = 4000, message = "The length of address is from 5 to 4000 characters")
    private String address;

    @CityConstraint
    @NotNull(message = "City's id is required")
    private Long cityId;

    @AssertTrue(message = "Confirmed password does not match the password")
    public boolean isPasswordMatching() {
        return password.equals(passwordConfirmed);
    }
}
