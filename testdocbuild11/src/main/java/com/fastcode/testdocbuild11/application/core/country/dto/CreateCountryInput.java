package com.fastcode.testdocbuild11.application.core.country.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class CreateCountryInput {

    @NotNull(message = "country Should not be null")
    @Length(max = 50, message = "country must be less than 50 characters")
    private String country;

    @NotNull(message = "lastUpdate Should not be null")
    private LocalDateTime lastUpdate;
}
