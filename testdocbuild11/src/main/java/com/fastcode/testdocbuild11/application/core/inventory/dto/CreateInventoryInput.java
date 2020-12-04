package com.fastcode.testdocbuild11.application.core.inventory.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateInventoryInput {

    @NotNull(message = "lastUpdate Should not be null")
    private LocalDateTime lastUpdate;

    @NotNull(message = "storeId Should not be null")
    private Short storeId;

    private Short filmId;
}
