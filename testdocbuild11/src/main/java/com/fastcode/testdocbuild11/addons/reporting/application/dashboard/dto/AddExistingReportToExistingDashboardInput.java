package com.fastcode.testdocbuild11.addons.reporting.application.dashboard.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddExistingReportToExistingDashboardInput {

    private Long id;
    private String description;
    private String title;
    private Long ownerId;
    private Boolean isPublished;
    List<ExistingReportInput> reportDetails = new ArrayList<>();
}
