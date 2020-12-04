package com.fastcode.testdocbuild11.addons.reporting.domain.dashboardversion;

import com.fastcode.testdocbuild11.domain.core.authorization.user.UserEntity;
import com.querydsl.core.types.Predicate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDashboardversionManager {
    DashboardversionEntity create(DashboardversionEntity dashboard);

    void delete(DashboardversionEntity dashboard);

    DashboardversionEntity update(DashboardversionEntity dashboard);

    DashboardversionEntity findById(DashboardversionId id);

    List<DashboardversionEntity> findByUserId(Long userId);

    Page<DashboardversionEntity> findAll(Predicate predicate, Pageable pageable);

    UserEntity getUser(DashboardversionId dashboardId);
}
