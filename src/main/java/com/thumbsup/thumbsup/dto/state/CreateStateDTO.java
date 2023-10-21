package com.thumbsup.thumbsup.dto.state;

import com.thumbsup.thumbsup.validation.interfaces.OrderConstraint;
import com.thumbsup.thumbsup.validation.interfaces.StateConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateStateDTO implements Serializable {
    @OrderConstraint
    @NotNull(message = "Order's id is required")
    private Long orderId;

    @StateConstraint
    @NotNull(message = "State's id is required")
    private Long stateId;

    private String note;
}
