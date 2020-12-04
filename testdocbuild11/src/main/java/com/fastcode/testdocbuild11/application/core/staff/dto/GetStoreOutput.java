package com.fastcode.testdocbuild11.application.core.staff.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetStoreOutput {

    private LocalDateTime lastUpdate;
    private Integer storeId;
    private Integer staffStaffId;
}
