package com.fastcode.testdocbuild11.application.core.rental.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRentalInput {

    @NotNull(message = "lastUpdate Should not be null")
    private LocalDateTime lastUpdate;

    @NotNull(message = "rentalDate Should not be null")
    private LocalDateTime rentalDate;

    private LocalDateTime returnDate;

    private Short customerId;
    private Integer inventoryId;
    private Short staffId;
}
