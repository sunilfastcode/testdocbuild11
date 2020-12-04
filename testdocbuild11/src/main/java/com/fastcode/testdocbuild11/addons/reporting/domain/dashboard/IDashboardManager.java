package com.fastcode.testdocbuild11.addons.reporting.domain.dashboard;

import com.fastcode.testdocbuild11.addons.reporting.application.dashboard.dto.DashboardDetailsOutput;
import com.querydsl.core.types.Predicate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDashboardManager {
    DashboardEntity create(DashboardEntity dashboard);

    void delete(DashboardEntity dashboard);

    DashboardEntity update(DashboardEntity dashboard);

    DashboardEntity findById(Long id);

    DashboardEntity findByDashboardIdAndUserId(Long id, Long userId);

    List<DashboardEntity> findByUserId(Long userId);

    Page<DashboardEntity> findAll(Predicate predicate, Pageable pageable);

    Page<DashboardDetailsOutput> getDashboards(Long userId, String search, Pageable pageable) throws Exception;

    Page<DashboardDetailsOutput> getAvailableDashboards(Long userId, Long reportId, String search, Pageable pageable)
        throws Exception;
}
