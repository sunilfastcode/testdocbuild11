package com.fastcode.testdocbuild11.application.core.payment.dto;

import java.math.BigDecimal;
import java.time.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePaymentInput {

    @NotNull(message = "amount Should not be null")
    private BigDecimal amount;

    @NotNull(message = "paymentDate Should not be null")
    private LocalDateTime paymentDate;

    private Short customerId;
    private Integer rentalId;
    private Short staffId;
}
