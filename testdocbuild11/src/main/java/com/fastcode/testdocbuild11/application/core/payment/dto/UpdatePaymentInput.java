package com.fastcode.testdocbuild11.application.core.payment.dto;

import java.math.BigDecimal;
import java.time.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePaymentInput {

    @NotNull(message = "amount Should not be null")
    private BigDecimal amount;

    @NotNull(message = "paymentDate Should not be null")
    private LocalDateTime paymentDate;

    @NotNull(message = "paymentId Should not be null")
    private Integer paymentId;

    private Short customerId;
    private Integer rentalId;
    private Short staffId;
    private Long versiono;
}
