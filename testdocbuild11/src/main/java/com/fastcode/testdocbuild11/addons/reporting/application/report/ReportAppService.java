package com.fastcode.testdocbuild11.addons.reporting.application.report;

import com.fastcode.testdocbuild11.addons.reporting.application.report.dto.*;
import com.fastcode.testdocbuild11.addons.reporting.application.reportversion.IReportversionAppService;
import com.fastcode.testdocbuild11.addons.reporting.application.reportversion.IReportversionMapper;
import com.fastcode.testdocbuild11.addons.reporting.application.reportversion.dto.CreateReportversionInput;
import com.fastcode.testdocbuild11.addons.reporting.application.reportversion.dto.CreateReportversionOutput;
import com.fastcode.testdocbuild11.addons.reporting.application.reportversion.dto.UpdateReportversionInput;
import com.fastcode.testdocbuild11.addons.reporting.application.reportversion.dto.UpdateReportversionOutput;
import com.fastcode.testdocbuild11.addons.reporting.domain.dashboardversionreport.DashboardversionreportEntity;
import com.fastcode.testdocbuild11.addons.reporting.domain.dashboardversionreport.IDashboardversionreportManager;
import com.fastcode.testdocbuild11.addons.reporting.domain.report.IReportManager;
import com.fastcode.testdocbuild11.addons.reporting.domain.report.QReportEntity;
import com.fastcode.testdocbuild11.addons.reporting.domain.report.ReportEntity;
import com.fastcode.testdocbuild11.addons.reporting.domain.reportversion.IReportversionManager;
import com.fastcode.testdocbuild11.addons.reporting.domain.reportversion.ReportversionEntity;
import com.fastcode.testdocbuild11.addons.reporting.domain.reportversion.ReportversionId;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.fastcode.testdocbuild11.domain.core.authorization.user.UserEntity;
import com.fastcode.testdocbuild11.domain.extended.authorization.user.IUserManagerExtended;
import com.querydsl.core.BooleanBuilder;
import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("reportAppService")
public class ReportAppService implements IReportAppService {

    static final int case1 = 1;
    static final int case2 = 2;
    static final int case3 = 3;

    @Autowired
    @Qualifier("reportManager")
    protected IReportManager _reportManager;

    @Autowired
    @Qualifier("reportversionManager")
    protected IReportversionManager _reportversionManager;

    @Autowired
    @Qualifier("userManagerExtended")
    protected IUserManagerExtended _userManager;

    @Autowired
    @Qualifier("dashboardversionreportManager")
    protected IDashboardversionreportManager _reportDashboardManager;

    @Autowired
    @Qualifier("reportversionAppService")
    protected IReportversionAppService _reportversionAppservice;

    @Autowired
    @Qualifier("IReportMapperImpl")
    protected IReportMapper mapper;

    @Autowired
    @Qualifier("IReportversionMapperImpl")
    protected IReportversionMapper reportversionMapper;

    @Autowired
    protected LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreateReportOutput create(CreateReportInput input) {
        ReportEntity report = mapper.createReportInputToReportEntity(input);
        if (input.getOwnerId() != null) {
            UserEntity foundUser = _userManager.findById(input.getOwnerId());

            if (foundUser != null) {
                report.setUser(foundUser);
            }
        }

        ReportEntity createdReport = _reportManager.create(report);
        CreateReportversionInput reportversion = mapper.createReportInputToCreateReportversionInput(input);
        reportversion.setReportId(createdReport.getId());

        CreateReportversionOutput reportversionOutput = _reportversionAppservice.create(reportversion);
        return mapper.reportEntityAndCreateReportversionOutputToCreateReportOutput(createdReport, reportversionOutput);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UpdateReportOutput update(Long reportId, UpdateReportInput input) {
        ReportversionId reportversionId = new ReportversionId(input.getUserId(), reportId, "running");

        ReportversionEntity rv = _reportversionManager.findById(reportversionId);
        UpdateReportversionInput reportversion = mapper.updateReportInputToUpdateReportversionInput(input);
        reportversion.setReportId(rv.getReport().getId());
        reportversion.setReportVersion(rv.getReportVersion());
        reportversion.setVersiono(rv.getVersiono());
        reportversion.setIsRefreshed(false);

        UpdateReportversionOutput reportversionOutput = _reportversionAppservice.update(reportversionId, reportversion);

        ReportEntity foundReport = _reportManager.findById(reportId);
        if (foundReport.getUser() != null && foundReport.getUser().getId() == input.getUserId()) {
            foundReport.setIsPublished(false);
            foundReport = _reportManager.update(foundReport);
        }

        return mapper.reportEntityAndUpdateReportversionOutputToUpdateReportOutput(foundReport, reportversionOutput);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long reportId, Long userId) {
        ReportEntity existing = _reportManager.findById(reportId);

        List<DashboardversionreportEntity> dvrList = _reportDashboardManager.findByReportId(reportId);

        for (DashboardversionreportEntity dvr : dvrList) {
            _reportDashboardManager.delete(dvr);
        }

        _reportversionAppservice.delete(new ReportversionId(userId, reportId, "running"));
        _reportversionAppservice.delete(new ReportversionId(userId, reportId, "published"));

        _reportManager.delete(existing);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindReportByIdOutput findById(Long reportId) {
        ReportEntity foundReport = _reportManager.findById(reportId);
        if (foundReport == null) return null;

        ReportversionEntity reportversion = _reportversionManager.findById(
            new ReportversionId(foundReport.getUser().getId(), foundReport.getId(), "running")
        );

        FindReportByIdOutput output = mapper.reportEntityToFindReportByIdOutput(foundReport, reportversion);
        return output;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindReportByIdOutput findByReportIdAndUserId(Long reportId, Long userId, String version) {
        ReportEntity foundReport = _reportManager.findByReportIdAndUserId(reportId, userId);
        if (foundReport == null) {
            return null;
        }

        ReportversionEntity reportVersion, publishedversion;
        publishedversion = _reportversionManager.findById(new ReportversionId(userId, reportId, "published"));

        if (StringUtils.isNotBlank(version) && version.equalsIgnoreCase("published")) {
            reportVersion = publishedversion;
        } else {
            reportVersion = _reportversionManager.findById(new ReportversionId(userId, reportId, "running"));
        }
        FindReportByIdOutput output = mapper.reportEntitiesToFindReportByIdOutput(foundReport, reportVersion);

        return output;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ReportDetailsOutput publishReport(Long userId, Long reportId) {
        ReportEntity foundReport = _reportManager.findById(reportId);

        foundReport.setIsPublished(true);
        foundReport = _reportManager.update(foundReport);
        ReportversionEntity foundReportversion = _reportversionManager.findById(
            new ReportversionId(userId, reportId, "running")
        );
        ReportversionEntity foundPublishedversion = _reportversionManager.findById(
            new ReportversionId(userId, reportId, "published")
        );
        ReportversionEntity publishedVersion = reportversionMapper.reportversionEntityToReportversionEntity(
            foundReportversion,
            userId,
            "published"
        );

        if (foundPublishedversion != null) {
            publishedVersion.setVersiono(foundPublishedversion.getVersiono());
        } else publishedVersion.setVersiono(0L);

        _reportversionManager.update(publishedVersion);
        foundReportversion.setIsRefreshed(true);
        _reportversionManager.update(foundReportversion);

        return mapper.reportEntitiesToReportDetailsOutput(foundReport, foundReportversion);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ReportDetailsOutput refreshReport(Long userId, Long reportId) {
        ReportEntity foundReport = _reportManager.findById(reportId);

        if (foundReport != null && foundReport.getUser() != null && foundReport.getUser().getId() == userId) {
            ReportversionEntity ownerPublishedversion = _reportversionManager.findById(
                new ReportversionId(userId, reportId, "published")
            );
            ReportversionEntity ownerRunningversion = _reportversionManager.findById(
                new ReportversionId(userId, reportId, "running")
            );

            UserEntity foundUser = _userManager.findById(userId);
            ReportversionEntity updatedVersion = reportversionMapper.reportversionEntityToReportversionEntity(
                ownerPublishedversion,
                userId,
                "running"
            );
            updatedVersion.setUser(foundUser);
            updatedVersion.setVersiono(ownerRunningversion.getVersiono());
            updatedVersion.setIsRefreshed(true);
            _reportversionManager.update(updatedVersion);

            ReportversionEntity runningversion = _reportversionManager.findById(
                new ReportversionId(userId, reportId, "running")
            );
            ReportDetailsOutput output = mapper.reportEntitiesToReportDetailsOutput(foundReport, runningversion);
            return output;
        }

        return null;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<ReportDetailsOutput> getReports(Long userId, String search, Pageable pageable) throws Exception {
        Page<ReportDetailsOutput> foundReport = _reportManager.getReports(userId, search, pageable);
        List<ReportDetailsOutput> reportList = foundReport.getContent();

        return reportList;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindReportByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception {
        Page<ReportEntity> foundReport = _reportManager.findAll(search(search), pageable);
        List<ReportEntity> reportList = foundReport.getContent();
        Iterator<ReportEntity> reportIterator = reportList.iterator();
        List<FindReportByIdOutput> output = new ArrayList<>();

        while (reportIterator.hasNext()) {
            ReportEntity report = reportIterator.next();
            ReportversionEntity reportVersion = _reportversionManager.findById(
                new ReportversionId(report.getUser().getId(), report.getId(), "running")
            );
            FindReportByIdOutput reportOutput = mapper.reportEntitiesToFindReportByIdOutput(report, reportVersion);
            output.add(reportOutput);
        }
        return output;
    }

    protected BooleanBuilder search(SearchCriteria search) throws Exception {
        QReportEntity report = QReportEntity.reportEntity;
        if (search != null) {
            Map<String, SearchFields> map = new HashMap<>();
            for (SearchFields fieldDetails : search.getFields()) {
                map.put(fieldDetails.getFieldName(), fieldDetails);
            }
            List<String> keysList = new ArrayList<String>(map.keySet());
            checkProperties(keysList);
            return searchKeyValuePair(report, map, search.getJoinColumns());
        }
        return null;
    }

    protected void checkProperties(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    list.get(i).replace("%20", "").trim().equals("userId") ||
                    list.get(i).replace("%20", "").trim().equals("ctype") ||
                    list.get(i).replace("%20", "").trim().equals("description") ||
                    list.get(i).replace("%20", "").trim().equals("id") ||
                    list.get(i).replace("%20", "").trim().equals("query") ||
                    list.get(i).replace("%20", "").trim().equals("reportType") ||
                    list.get(i).replace("%20", "").trim().equals("reportdashboard") ||
                    list.get(i).replace("%20", "").trim().equals("title") ||
                    list.get(i).replace("%20", "").trim().equals("user")
                )
            ) {
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    protected BooleanBuilder searchKeyValuePair(
        QReportEntity report,
        Map<String, SearchFields> map,
        Map<String, String> joinColumns
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if (details.getKey().replace("%20", "").trim().equals("isPublished")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    (
                        details.getValue().getSearchValue().equalsIgnoreCase("true") ||
                        details.getValue().getSearchValue().equalsIgnoreCase("false")
                    )
                ) builder.and(
                    report.isPublished.eq(Boolean.parseBoolean(details.getValue().getSearchValue()))
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    (
                        details.getValue().getSearchValue().equalsIgnoreCase("true") ||
                        details.getValue().getSearchValue().equalsIgnoreCase("false")
                    )
                ) builder.and(report.isPublished.ne(Boolean.parseBoolean(details.getValue().getSearchValue())));
            }
        }
        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("ownerId")) {
                builder.and(report.user.id.eq(Long.parseLong(joinCol.getValue())));
            }
        }
        return builder;
    }

    public Map<String, String> parseReportdashboardJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("reportId", keysString);
        return joinColumnMap;
    }
}
