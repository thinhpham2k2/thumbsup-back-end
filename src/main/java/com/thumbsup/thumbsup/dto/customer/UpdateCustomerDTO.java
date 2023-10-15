package com.thumbsup.thumbsup.dto.customer;

import com.thumbsup.thumbsup.validation.interfaces.BirthdayConstraint;
import com.thumbsup.thumbsup.validation.interfaces.EmailConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCustomerDTO implements Serializable {
    @NotNull(message = "Full name is required!")
    @Size(min = 2, max = 255, message = "The length of full name is from 2 to 255 characters")
    private String fullName;

    @NotNull(message = "Phone is required!")
    @Size(min = 7, max = 20, message = "The length of phone is from 8 to 20 characters")
    private String phone;

    @EmailConstraint
    private String email;

    private String avatar;

    @BirthdayConstraint
    private LocalDate dob;

    @NotNull(message = "Address is required!")
    @Size(min = 5, max = 4000, message = "The length of address is from 5 to 4000 characters")
    private String address;

    @NotNull(message = "City's id is required!")
    private Long cityId;
}
