package com.fastcode.testdocbuild11.addons.reporting.application.dashboardversion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDashboardversionOutput {

    private String description;
    private Long id;
    private String title;
    private Boolean isRefreshed;
    private Long userId;
    private String userDescriptiveField;
}
