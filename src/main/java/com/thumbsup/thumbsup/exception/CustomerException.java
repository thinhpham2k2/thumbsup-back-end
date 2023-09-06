package com.thumbsup.thumbsup.exception;

import com.thumbsup.thumbsup.exception.dto.CustomerErrorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerException extends RuntimeException {

    private CustomerErrorDTO customerErrorDTO;

    public CustomerException(String message, CustomerErrorDTO customerErrorDTO){
        super(message);
        this.customerErrorDTO = customerErrorDTO;
    }
}
