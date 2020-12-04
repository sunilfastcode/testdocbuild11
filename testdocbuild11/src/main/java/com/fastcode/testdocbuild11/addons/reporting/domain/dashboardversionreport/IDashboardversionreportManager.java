package com.fastcode.testdocbuild11.addons.reporting.domain.dashboardversionreport;

import com.fastcode.testdocbuild11.addons.reporting.domain.dashboardversion.DashboardversionEntity;
import com.fastcode.testdocbuild11.addons.reporting.domain.report.ReportEntity;
import com.querydsl.core.types.Predicate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDashboardversionreportManager {
    DashboardversionreportEntity create(DashboardversionreportEntity reportdashboard);

    void delete(DashboardversionreportEntity reportdashboard);

    DashboardversionreportEntity update(DashboardversionreportEntity reportdashboard);

    DashboardversionreportEntity findById(DashboardversionreportId reportdashboardId);

    List<DashboardversionreportEntity> findByUserId(Long userId);

    List<DashboardversionreportEntity> findByIdIfUserIdIsNotSame(
        Long dashboardId,
        Long reportId,
        Long userId,
        String version
    );

    List<DashboardversionreportEntity> findByReportIdAndUserIdAndVersion(Long reportId, Long userId, String version);

    List<DashboardversionreportEntity> findByDashboardId(Long dashboardId);

    List<DashboardversionreportEntity> findByDashboardIdAndVersionAndUserId(
        Long dashboardId,
        String version,
        Long userId
    );

    List<DashboardversionreportEntity> findByDashboardIdAndVersionAndUserIdInDesc(Long id, String version, Long userId);

    Page<DashboardversionreportEntity> findAll(Predicate predicate, Pageable pageable);

    DashboardversionEntity getDashboardversion(DashboardversionreportId reportdashboardId);

    ReportEntity getReport(DashboardversionreportId reportdashboardId);

    List<DashboardversionreportEntity> findByReportId(Long reportId);
}
