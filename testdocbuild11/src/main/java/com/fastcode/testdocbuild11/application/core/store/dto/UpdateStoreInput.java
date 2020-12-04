package com.fastcode.testdocbuild11.application.core.store.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStoreInput {

    @NotNull(message = "lastUpdate Should not be null")
    private LocalDateTime lastUpdate;

    @NotNull(message = "storeId Should not be null")
    private Integer storeId;

    private Short addressId;
    private Short managerStaffId;
    private Long versiono;
}
