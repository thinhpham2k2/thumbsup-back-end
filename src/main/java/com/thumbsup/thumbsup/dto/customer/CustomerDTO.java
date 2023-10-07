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
public class CustomerDTO implements Serializable {
    private Long id;
    private String userName;
    private String fullName;
    private String email;
    private String phone;
    private String avatar;
    private LocalDate dob;
    private String address;
    private Boolean state;
    private Long cityId;
    private String cityName;
    private Boolean status;
}
