package com.fastcode.testdocbuild11.addons.reporting.application.dashboard.dto;

import com.fastcode.testdocbuild11.addons.reporting.application.report.dto.UpdateReportInput;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDashboardInput {

    private String description;

    @NotNull(message = "id Should not be null")
    private Long id;

    @NotNull
    private String title;

    private Long ownerId;
    private Long userId;
    private Boolean isPublished;
    List<UpdateReportInput> reportDetails = new ArrayList<>();
    private Long versiono;
}
