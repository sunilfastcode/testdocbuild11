package com.fastcode.testdocbuild11.addons.reporting.application.dashboardversionreport;

import com.fastcode.testdocbuild11.addons.reporting.application.dashboard.dto.CreateDashboardOutput;
import com.fastcode.testdocbuild11.addons.reporting.application.dashboardversionreport.dto.*;
import com.fastcode.testdocbuild11.addons.reporting.application.report.dto.CreateReportOutput;
import com.fastcode.testdocbuild11.addons.reporting.domain.dashboard.DashboardEntity;
import com.fastcode.testdocbuild11.addons.reporting.domain.dashboardversion.DashboardversionEntity;
import com.fastcode.testdocbuild11.addons.reporting.domain.dashboardversion.DashboardversionId;
import com.fastcode.testdocbuild11.addons.reporting.domain.dashboardversion.IDashboardversionManager;
import com.fastcode.testdocbuild11.addons.reporting.domain.dashboardversionreport.DashboardversionreportEntity;
import com.fastcode.testdocbuild11.addons.reporting.domain.dashboardversionreport.DashboardversionreportId;
import com.fastcode.testdocbuild11.addons.reporting.domain.dashboardversionreport.IDashboardversionreportManager;
import com.fastcode.testdocbuild11.addons.reporting.domain.dashboardversionreport.QDashboardversionreportEntity;
import com.fastcode.testdocbuild11.addons.reporting.domain.report.IReportManager;
import com.fastcode.testdocbuild11.addons.reporting.domain.report.ReportEntity;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.querydsl.core.BooleanBuilder;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("dashboardversionreportAppService")
public class DashboardversionreportAppService implements IDashboardversionreportAppService {

    static final int case1 = 1;
    static final int case2 = 2;
    static final int case3 = 3;

    @Autowired
    @Qualifier("dashboardversionreportManager")
    protected IDashboardversionreportManager _reportdashboardManager;

    @Autowired
    @Qualifier("dashboardversionManager")
    protected IDashboardversionManager _dashboardversionManager;

    @Autowired
    @Qualifier("reportManager")
    protected IReportManager _reportManager;

    @Autowired
    @Qualifier("IDashboardversionreportMapperImpl")
    protected IDashboardversionreportMapper mapper;

    @Autowired
    protected LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreateDashboardversionreportOutput create(CreateDashboardversionreportInput input) {
        DashboardversionreportEntity reportdashboard = mapper.createReportdashboardInputToDashboardversionreportEntity(
            input
        );
        if (input.getDashboardId() != null) {
            DashboardversionEntity foundDashboard = _dashboardversionManager.findById(
                new DashboardversionId(input.getUserId(), input.getDashboardId(), input.getDashboardVersion())
            );
            if (foundDashboard != null) {
                reportdashboard.setDashboardversionEntity(foundDashboard);
            }
        }

        if (input.getReportId() != null) {
            ReportEntity foundReport = _reportManager.findById(input.getReportId());
            if (foundReport != null) {
                reportdashboard.setReport(foundReport);
            }
        }

        DashboardversionreportEntity createdReportdashboard = _reportdashboardManager.create(reportdashboard);
        return mapper.reportdashboardEntityToCreateReportdashboardOutput(createdReportdashboard);
    }

    public Boolean addReportsToDashboard(DashboardEntity dashboard, List<ReportEntity> reportsList) {
        Boolean status = true;
        DashboardversionreportEntity reportdashboard = new DashboardversionreportEntity();
        reportdashboard.setDashboardId(dashboard.getId());

        for (ReportEntity report : reportsList) {
            reportdashboard.setReport(report);
            reportdashboard.setReportId(report.getId());
        }

        return status;
    }

    public Boolean addReportsToDashboardRunningversion(
        CreateDashboardOutput dashboard,
        List<CreateReportOutput> reportsList
    ) {
        Boolean status = true;
        DashboardversionreportEntity reportdashboard = new DashboardversionreportEntity();
        DashboardversionEntity dashboardrunningVersion = _dashboardversionManager.findById(
            new DashboardversionId(dashboard.getOwnerId(), dashboard.getId(), "running")
        );
        if (dashboardrunningVersion != null) {
            reportdashboard.setDashboardId(dashboardrunningVersion.getDashboardId());
            reportdashboard.setDashboardVersion(dashboardrunningVersion.getDashboardVersion());
            reportdashboard.setUserId(dashboardrunningVersion.getUserId());
            reportdashboard.setDashboardversionEntity(dashboardrunningVersion);
        }

        List<DashboardversionreportEntity> list = _reportdashboardManager.findByDashboardIdAndVersionAndUserIdInDesc(
            reportdashboard.getDashboardId(),
            "running",
            dashboard.getOwnerId()
        );

        Long count = 0L;
        if (list != null && !list.isEmpty()) {
            count = list.get(0).getOrderId();
            count++;
        }

        for (CreateReportOutput report : reportsList) {
            ReportEntity foundReport = _reportManager.findById(report.getId());
            if (foundReport != null) {
                reportdashboard.setReport(foundReport);
                reportdashboard.setReportId(foundReport.getId());
            }

            reportdashboard.setReportWidth(report.getReportWidth());
            reportdashboard.setOrderId(count);
            DashboardversionreportEntity createdReportdashboard = _reportdashboardManager.create(reportdashboard);

            if (createdReportdashboard == null) {
                status = false;
            }
            count++;
        }

        return status;
    }

    public Boolean addReportsToDashboardPublishedversion(
        CreateDashboardOutput dashboard,
        List<CreateReportOutput> reportsList
    ) {
        Boolean status = true;
        DashboardversionreportEntity reportdashboard = new DashboardversionreportEntity();
        DashboardversionEntity dashboardPublishedVersion = _dashboardversionManager.findById(
            new DashboardversionId(dashboard.getOwnerId(), dashboard.getId(), "published")
        );
        if (dashboardPublishedVersion != null) {
            reportdashboard.setDashboardId(dashboardPublishedVersion.getDashboardId());
            reportdashboard.setDashboardVersion(dashboardPublishedVersion.getDashboardVersion());
            reportdashboard.setUserId(dashboardPublishedVersion.getUserId());
            reportdashboard.setDashboardversionEntity(dashboardPublishedVersion);
        }

        List<DashboardversionreportEntity> list = _reportdashboardManager.findByDashboardIdAndVersionAndUserIdInDesc(
            reportdashboard.getDashboardId(),
            "published",
            dashboard.getOwnerId()
        );

        Long count = 0L;
        if (list != null && !list.isEmpty()) {
            count = list.get(0).getOrderId();
            count++;
        }

        for (CreateReportOutput report : reportsList) {
            ReportEntity foundReport = _reportManager.findById(report.getId());
            if (foundReport != null) {
                reportdashboard.setReport(foundReport);
                reportdashboard.setReportId(foundReport.getId());
            }

            reportdashboard.setReportWidth(report.getReportWidth());
            reportdashboard.setOrderId(count);
            DashboardversionreportEntity createdReportdashboard = _reportdashboardManager.create(reportdashboard);

            if (createdReportdashboard == null) {
                status = false;
            }
            count++;
        }

        return status;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UpdateDashboardversionreportOutput update(
        DashboardversionreportId reportdashboardId,
        UpdateDashboardversionreportInput input
    ) {
        DashboardversionreportEntity reportdashboard = mapper.updateReportdashboardInputToDashboardversionreportEntity(
            input
        );
        if (input.getDashboardId() != null) {
            DashboardversionEntity foundDashboard = _dashboardversionManager.findById(
                new DashboardversionId(input.getUserId(), input.getDashboardId(), input.getDashboardVersion())
            );
            if (foundDashboard != null) {
                reportdashboard.setDashboardversionEntity(foundDashboard);
            }
        }
        if (input.getReportId() != null) {
            ReportEntity foundReport = _reportManager.findById(input.getReportId());
            if (foundReport != null) {
                reportdashboard.setReport(foundReport);
            }
        }

        DashboardversionreportEntity updatedReportdashboard = _reportdashboardManager.update(reportdashboard);

        return mapper.reportdashboardEntityToUpdateReportdashboardOutput(updatedReportdashboard);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(DashboardversionreportId reportdashboardId) {
        DashboardversionreportEntity existing = _reportdashboardManager.findById(reportdashboardId);
        ReportEntity report = _reportManager.findById(reportdashboardId.getReportId());
        report.removeDashboardversionreport(existing);
        DashboardversionEntity dashboardversion = _dashboardversionManager.findById(
            new DashboardversionId(
                reportdashboardId.getUserId(),
                reportdashboardId.getDashboardId(),
                reportdashboardId.getDashboardVersion()
            )
        );
        dashboardversion.removeDashboardversionreport(existing);

        _reportdashboardManager.delete(existing);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindDashboardversionreportByIdOutput findById(DashboardversionreportId reportdashboardId) {
        DashboardversionreportEntity foundReportdashboard = _reportdashboardManager.findById(reportdashboardId);
        if (foundReportdashboard == null) return null;

        FindDashboardversionreportByIdOutput output = mapper.reportdashboardEntityToFindReportdashboardByIdOutput(
            foundReportdashboard
        );
        return output;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetDashboardversionOutput getDashboard(DashboardversionreportId reportdashboardId) {
        DashboardversionreportEntity foundReportdashboard = _reportdashboardManager.findById(reportdashboardId);
        if (foundReportdashboard == null) {
            logHelper.getLogger().error("There does not exist a reportdashboard wth a id=%s", reportdashboardId);
            return null;
        }
        DashboardversionEntity re = _reportdashboardManager.getDashboardversion(reportdashboardId);
        return mapper.dashboardversionEntityToGetDashboardversionOutput(re, foundReportdashboard);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetReportOutput getReport(DashboardversionreportId reportdashboardId) {
        DashboardversionreportEntity foundReportdashboard = _reportdashboardManager.findById(reportdashboardId);
        if (foundReportdashboard == null) {
            logHelper.getLogger().error("There does not exist a reportdashboard wth a id=%s", reportdashboardId);
            return null;
        }
        ReportEntity re = _reportdashboardManager.getReport(reportdashboardId);
        return mapper.reportEntityToGetReportOutput(re, foundReportdashboard);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindDashboardversionreportByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception {
        Page<DashboardversionreportEntity> foundReportdashboard = _reportdashboardManager.findAll(
            search(search),
            pageable
        );
        List<DashboardversionreportEntity> reportdashboardList = foundReportdashboard.getContent();
        Iterator<DashboardversionreportEntity> reportdashboardIterator = reportdashboardList.iterator();
        List<FindDashboardversionreportByIdOutput> output = new ArrayList<>();

        while (reportdashboardIterator.hasNext()) {
            output.add(mapper.reportdashboardEntityToFindReportdashboardByIdOutput(reportdashboardIterator.next()));
        }
        return output;
    }

    protected BooleanBuilder search(SearchCriteria search) throws Exception {
        QDashboardversionreportEntity reportdashboard = QDashboardversionreportEntity.dashboardversionreportEntity;
        if (search != null) {
            Map<String, SearchFields> map = new HashMap<>();
            for (SearchFields fieldDetails : search.getFields()) {
                map.put(fieldDetails.getFieldName(), fieldDetails);
            }
            List<String> keysList = new ArrayList<String>(map.keySet());
            checkProperties(keysList);
            return searchKeyValuePair(reportdashboard, map, search.getJoinColumns());
        }
        return null;
    }

    protected void checkProperties(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    list.get(i).replace("%20", "").trim().equals("dashboardversion") ||
                    list.get(i).replace("%20", "").trim().equals("dashboardId") ||
                    list.get(i).replace("%20", "").trim().equals("userId") ||
                    list.get(i).replace("%20", "").trim().equals("version") ||
                    list.get(i).replace("%20", "").trim().equals("report") ||
                    list.get(i).replace("%20", "").trim().equals("reportId")
                )
            ) {
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    protected BooleanBuilder searchKeyValuePair(
        QDashboardversionreportEntity reportdashboard,
        Map<String, SearchFields> map,
        Map<String, String> joinColumns
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("dashboardId")) {
                builder.and(reportdashboard.dashboardversionEntity.dashboardId.eq(Long.parseLong(joinCol.getValue())));
            }

            if (joinCol != null && joinCol.getKey().equals("userId")) {
                builder.and(reportdashboard.dashboardversionEntity.userId.eq(Long.parseLong(joinCol.getValue())));
            }

            if (joinCol != null && joinCol.getKey().equals("dashboardVersion")) {
                builder.and(reportdashboard.dashboardversionEntity.dashboardVersion.eq((joinCol.getValue())));
            }

            if (joinCol != null && joinCol.getKey().equals("reportId")) {
                builder.and(reportdashboard.report.id.eq(Long.parseLong(joinCol.getValue())));
            }
        }
        return builder;
    }

    public DashboardversionreportId parseReportdashboardKey(String keysString) {
        String[] keyEntries = keysString.split(",");
        DashboardversionreportId reportdashboardId = new DashboardversionreportId();

        Map<String, String> keyMap = new HashMap<String, String>();
        if (keyEntries.length > 1) {
            for (String keyEntry : keyEntries) {
                String[] keyEntryArr = keyEntry.split(":");
                if (keyEntryArr.length > 1) {
                    keyMap.put(keyEntryArr[0], keyEntryArr[1]);
                } else {
                    return null;
                }
            }
        } else {
            return null;
        }

        reportdashboardId.setDashboardId(Long.valueOf(keyMap.get("dashboardId")));
        reportdashboardId.setDashboardId(Long.valueOf(keyMap.get("userId")));
        reportdashboardId.setDashboardId(Long.valueOf(keyMap.get("dashboardVersion")));
        reportdashboardId.setReportId(Long.valueOf(keyMap.get("reportId")));
        return reportdashboardId;
    }
}
