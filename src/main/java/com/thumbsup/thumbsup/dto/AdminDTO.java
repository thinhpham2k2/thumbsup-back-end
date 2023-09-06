package com.thumbsup.thumbsup.dto;

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
public class AdminDTO implements Serializable {
    private Long id;
    private String userName;
    private String fullName;
    private String email;
    private String phone;
    private String avatar;
    private LocalDate dob;
    private Boolean state;
    private Boolean status;
}
