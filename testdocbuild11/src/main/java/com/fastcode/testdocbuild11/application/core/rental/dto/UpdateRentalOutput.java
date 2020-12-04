package com.fastcode.testdocbuild11.application.core.rental.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRentalOutput {

    private LocalDateTime lastUpdate;
    private LocalDateTime rentalDate;
    private Integer rentalId;
    private LocalDateTime returnDate;
    private Short customerId;
    private Integer customerDescriptiveField;
    private Integer inventoryId;
    private Integer inventoryDescriptiveField;
    private Short staffId;
    private Integer staffDescriptiveField;
}
