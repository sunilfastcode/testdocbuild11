package com.fastcode.testdocbuild11.addons.reporting.application.reportversion.dto;

import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;

@Getter
@Setter
public class FindReportversionByIdOutput {

    private String ctype;
    private String description;
    private JSONObject query;
    private String reportType;
    private String title;
    private Boolean isRefreshed;
    private Long userId;
    private String userDescriptiveField;
    private Long reportId;
}
