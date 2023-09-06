package com.thumbsup.thumbsup.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerErrorDTO implements Serializable {
    private String userName;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private String avatar;
    private String dob;
    private String address;
    private String city;
}
