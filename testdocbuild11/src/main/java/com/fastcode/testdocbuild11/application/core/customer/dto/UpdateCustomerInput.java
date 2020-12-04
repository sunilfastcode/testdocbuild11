package com.fastcode.testdocbuild11.application.core.customer.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class UpdateCustomerInput {

    private Integer active;

    @NotNull(message = "activebool Should not be null")
    private Boolean activebool;

    @NotNull(message = "createDate Should not be null")
    private LocalDate createDate;

    @NotNull(message = "customerId Should not be null")
    private Integer customerId;

    @Length(max = 50, message = "email must be less than 50 characters")
    private String email;

    @NotNull(message = "firstName Should not be null")
    @Length(max = 45, message = "firstName must be less than 45 characters")
    private String firstName;

    @NotNull(message = "lastName Should not be null")
    @Length(max = 45, message = "lastName must be less than 45 characters")
    private String lastName;

    private LocalDateTime lastUpdate;

    @NotNull(message = "storeId Should not be null")
    private Short storeId;

    private Short addressId;
    private Long versiono;
}
