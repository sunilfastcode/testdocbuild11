package com.fastcode.testdocbuild11.addons.reporting.domain.dashboardversion;

import com.fastcode.testdocbuild11.domain.core.authorization.user.UserEntity;
import com.querydsl.core.types.Predicate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component("dashboardversionManager")
public class DashboardversionManager implements IDashboardversionManager {

    @Autowired
    @Qualifier("dashboardversionRepository")
    protected IDashboardversionRepository _dashboardversionRepository;

    public DashboardversionEntity create(DashboardversionEntity dashboardversion) {
        return _dashboardversionRepository.save(dashboardversion);
    }

    public void delete(DashboardversionEntity dashboardversion) {
        _dashboardversionRepository.delete(dashboardversion);
    }

    public DashboardversionEntity update(DashboardversionEntity dashboardversion) {
        return _dashboardversionRepository.save(dashboardversion);
    }

    public DashboardversionEntity findById(DashboardversionId dashboardversionId) {
        Optional<DashboardversionEntity> dbDashboardversion = _dashboardversionRepository.findById(dashboardversionId);
        if (dbDashboardversion.isPresent()) {
            DashboardversionEntity existingDashboardversion = dbDashboardversion.get();
            return existingDashboardversion;
        } else {
            return null;
        }
    }

    public List<DashboardversionEntity> findByUserId(Long userId) {
        return _dashboardversionRepository.findByUserId(userId);
    }

    public Page<DashboardversionEntity> findAll(Predicate predicate, Pageable pageable) {
        return _dashboardversionRepository.findAll(predicate, pageable);
    }

    //User
    public UserEntity getUser(DashboardversionId dashboardversionId) {
        Optional<DashboardversionEntity> dbDashboardversion = _dashboardversionRepository.findById(dashboardversionId);
        if (dbDashboardversion.isPresent()) {
            DashboardversionEntity existingDashboardversion = dbDashboardversion.get();
            return existingDashboardversion.getUser();
        } else {
            return null;
        }
    }
}
