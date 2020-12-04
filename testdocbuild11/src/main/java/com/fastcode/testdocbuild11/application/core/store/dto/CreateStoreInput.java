package com.fastcode.testdocbuild11.application.core.store.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateStoreInput {

    @NotNull(message = "lastUpdate Should not be null")
    private LocalDateTime lastUpdate;

    private Short addressId;
    private Short managerStaffId;
}
