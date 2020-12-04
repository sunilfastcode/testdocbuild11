package com.fastcode.testdocbuild11.addons.reporting.domain.reportversion;

import com.fastcode.testdocbuild11.domain.core.authorization.user.UserEntity;
import com.querydsl.core.types.Predicate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component("reportversionManager")
public class ReportversionManager implements IReportversionManager {

    @Autowired
    @Qualifier("reportversionRepository")
    protected IReportversionRepository _reportversionRepository;

    public ReportversionEntity create(ReportversionEntity report) {
        return _reportversionRepository.save(report);
    }

    public void delete(ReportversionEntity report) {
        _reportversionRepository.delete(report);
    }

    public ReportversionEntity update(ReportversionEntity report) {
        return _reportversionRepository.save(report);
    }

    public ReportversionEntity findById(ReportversionId reportversionId) {
        Optional<ReportversionEntity> dbReportversion = _reportversionRepository.findById(reportversionId);
        if (dbReportversion.isPresent()) {
            ReportversionEntity existingReportversion = dbReportversion.get();
            return existingReportversion;
        } else {
            return null;
        }
    }

    public List<ReportversionEntity> findByUserId(Long userId) {
        return _reportversionRepository.findByUserId(userId);
    }

    public Page<ReportversionEntity> findAll(Predicate predicate, Pageable pageable) {
        return _reportversionRepository.findAll(predicate, pageable);
    }

    //User
    public UserEntity getUser(ReportversionId reportId) {
        Optional<ReportversionEntity> dbReportversion = _reportversionRepository.findById(reportId);
        if (dbReportversion.isPresent()) {
            ReportversionEntity existingReportversion = dbReportversion.get();
            return existingReportversion.getUser();
        } else {
            return null;
        }
    }
}
