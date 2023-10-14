package com.thumbsup.thumbsup.dto.customer;

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
    private String fullName;
    private String phone;
    private String avatar;
    private LocalDate dob;
    private String address;
    private Long cityId;
}
