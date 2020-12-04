package com.fastcode.testdocbuild11.application.core.language.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class CreateLanguageInput {

    @NotNull(message = "lastUpdate Should not be null")
    private LocalDateTime lastUpdate;

    @NotNull(message = "name Should not be null")
    @Length(max = 20, message = "name must be less than 20 characters")
    private String name;
}
