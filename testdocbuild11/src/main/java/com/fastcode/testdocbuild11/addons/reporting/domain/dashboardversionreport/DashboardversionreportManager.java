package com.fastcode.testdocbuild11.addons.reporting.domain.dashboardversionreport;

import com.fastcode.testdocbuild11.addons.reporting.domain.dashboardversion.DashboardversionEntity;
import com.fastcode.testdocbuild11.addons.reporting.domain.report.ReportEntity;
import com.querydsl.core.types.Predicate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component("dashboardversionreportManager")
public class DashboardversionreportManager implements IDashboardversionreportManager {

    @Autowired
    @Qualifier("dashboardversionreportRepository")
    protected IDashboardversionreportRepository _reportdashboardRepository;

    public DashboardversionreportEntity create(DashboardversionreportEntity reportdashboard) {
        return _reportdashboardRepository.save(reportdashboard);
    }

    public void delete(DashboardversionreportEntity reportdashboard) {
        _reportdashboardRepository.delete(reportdashboard);
    }

    public DashboardversionreportEntity update(DashboardversionreportEntity reportdashboard) {
        return _reportdashboardRepository.save(reportdashboard);
    }

    public DashboardversionreportEntity findById(DashboardversionreportId reportdashboardId) {
        Optional<DashboardversionreportEntity> dbDashboardversionreport = _reportdashboardRepository.findById(
            reportdashboardId
        );
        if (dbDashboardversionreport.isPresent()) {
            DashboardversionreportEntity existingDashboardversionreport = dbDashboardversionreport.get();
            return existingDashboardversionreport;
        } else {
            return null;
        }
    }

    public List<DashboardversionreportEntity> findByUserId(Long userId) {
        return _reportdashboardRepository.findByUserId(userId);
    }

    public List<DashboardversionreportEntity> findByIdIfUserIdIsNotSame(
        Long dashboardId,
        Long reportId,
        Long userId,
        String version
    ) {
        return _reportdashboardRepository.findByIdIfUserIdNotSame(dashboardId, reportId, userId, version);
    }

    public List<DashboardversionreportEntity> findByReportIdAndUserIdAndVersion(
        Long reportId,
        Long userId,
        String version
    ) {
        return _reportdashboardRepository.findByReportIdAndUserIdAndVersion(reportId, userId, version);
    }

    public List<DashboardversionreportEntity> findByDashboardId(Long dashboardId) {
        return _reportdashboardRepository.findByDashboardId(dashboardId);
    }

    public List<DashboardversionreportEntity> findByReportId(Long reportId) {
        return _reportdashboardRepository.findByReportId(reportId);
    }

    public List<DashboardversionreportEntity> findByDashboardIdAndVersionAndUserId(
        Long dashboardId,
        String version,
        Long userId
    ) {
        return _reportdashboardRepository.findByDashboardIdAndVersionAndUserId(dashboardId, version, userId);
    }

    public List<DashboardversionreportEntity> findByDashboardIdAndVersionAndUserIdInDesc(
        Long id,
        String version,
        Long userId
    ) {
        return _reportdashboardRepository.findByDashboardIdAndVersionAndUserIdInDesc(id, version, userId);
    }

    public Page<DashboardversionreportEntity> findAll(Predicate predicate, Pageable pageable) {
        return _reportdashboardRepository.findAll(predicate, pageable);
    }

    //Dashboard
    public DashboardversionEntity getDashboardversion(DashboardversionreportId reportdashboardId) {
        Optional<DashboardversionreportEntity> dbDashboardversionreport = _reportdashboardRepository.findById(
            reportdashboardId
        );
        if (dbDashboardversionreport.isPresent()) {
            DashboardversionreportEntity existingDashboardversionreport = dbDashboardversionreport.get();
            return existingDashboardversionreport.getDashboardversionEntity();
        } else {
            return null;
        }
    }

    //Report
    public ReportEntity getReport(DashboardversionreportId reportdashboardId) {
        Optional<DashboardversionreportEntity> dbDashboardversionreport = _reportdashboardRepository.findById(
            reportdashboardId
        );
        if (dbDashboardversionreport.isPresent()) {
            DashboardversionreportEntity existingDashboardversionreport = dbDashboardversionreport.get();
            return existingDashboardversionreport.getReport();
        } else {
            return null;
        }
    }
}
