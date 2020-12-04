package com.fastcode.testdocbuild11.addons.reporting.domain.report;

import com.fastcode.testdocbuild11.addons.reporting.application.report.dto.ReportDetailsOutput;
import com.querydsl.core.types.Predicate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component("reportManager")
public class ReportManager implements IReportManager {

    @Autowired
    @Qualifier("reportRepository")
    protected IReportRepository _reportRepository;

    public ReportEntity create(ReportEntity report) {
        return _reportRepository.save(report);
    }

    public void delete(ReportEntity report) {
        _reportRepository.delete(report);
    }

    public ReportEntity update(ReportEntity report) {
        return _reportRepository.save(report);
    }

    public ReportEntity findById(Long reportId) {
        Optional<ReportEntity> dbReport = _reportRepository.findById(reportId);
        if (dbReport.isPresent()) {
            ReportEntity existingReport = dbReport.get();
            return existingReport;
        } else {
            return null;
        }
    }

    public ReportEntity findByReportIdAndUserId(Long reportId, Long userId) {
        return _reportRepository.findByReportIdAndUserId(reportId, userId);
    }

    public List<ReportEntity> findByUserId(Long userId) {
        return _reportRepository.findByUserId(userId);
    }

    public Page<ReportEntity> findAll(Predicate predicate, Pageable pageable) {
        return _reportRepository.findAll(predicate, pageable);
    }

    public Page<ReportDetailsOutput> getReports(Long userId, String search, Pageable pageable) throws Exception {
        Page<ReportDetailsOutput> list = _reportRepository.getAllReportsByUserId(userId, search, pageable);
        return list;
    }
}
