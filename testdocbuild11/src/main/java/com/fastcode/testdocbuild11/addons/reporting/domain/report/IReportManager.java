package com.fastcode.testdocbuild11.addons.reporting.domain.report;

import com.fastcode.testdocbuild11.addons.reporting.application.report.dto.ReportDetailsOutput;
import com.querydsl.core.types.Predicate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IReportManager {
    ReportEntity create(ReportEntity report);

    void delete(ReportEntity report);

    ReportEntity update(ReportEntity report);

    ReportEntity findById(Long id);

    Page<ReportEntity> findAll(Predicate predicate, Pageable pageable);

    ReportEntity findByReportIdAndUserId(Long id, Long userId);

    List<ReportEntity> findByUserId(Long userId);

    Page<ReportDetailsOutput> getReports(Long userId, String search, Pageable pageable) throws Exception;
}
