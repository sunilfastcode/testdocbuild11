package com.fastcode.testdocbuild11.application.core.store.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindStoreByIdOutput {

    private LocalDateTime lastUpdate;
    private Integer storeId;
    private Short addressId;
    private Integer addressDescriptiveField;
    private Short managerStaffId;
    private Integer staffDescriptiveField;
    private Long versiono;
}
