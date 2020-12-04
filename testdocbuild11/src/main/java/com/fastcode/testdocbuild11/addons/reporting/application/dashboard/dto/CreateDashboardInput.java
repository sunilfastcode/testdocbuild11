package com.fastcode.testdocbuild11.addons.reporting.application.dashboard.dto;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDashboardInput {

    private String description;
    private Boolean isPublished;

    @NotNull
    private String title;

    private Long ownerId;
}
