package com.fastcode.testdocbuild11.addons.reporting.domain.dashboard;

import com.fastcode.testdocbuild11.addons.reporting.application.dashboard.dto.DashboardDetailsOutput;
import com.querydsl.core.types.Predicate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component("dashboardManager")
public class DashboardManager implements IDashboardManager {

    @Autowired
    @Qualifier("dashboardRepository")
    protected IDashboardRepository _dashboardRepository;

    public DashboardEntity create(DashboardEntity dashboard) {
        return _dashboardRepository.save(dashboard);
    }

    public void delete(DashboardEntity dashboard) {
        _dashboardRepository.delete(dashboard);
    }

    public DashboardEntity update(DashboardEntity dashboard) {
        return _dashboardRepository.save(dashboard);
    }

    public DashboardEntity findById(Long dashboardId) {
        Optional<DashboardEntity> dbDashboard = _dashboardRepository.findById(dashboardId);
        return dbDashboard.orElse(null);
    }

    public DashboardEntity findByDashboardIdAndUserId(Long dashboardId, Long userId) {
        return _dashboardRepository.findByDashboardIdAndUserId(dashboardId, userId);
    }

    public Page<DashboardEntity> findAll(Predicate predicate, Pageable pageable) {
        return _dashboardRepository.findAll(predicate, pageable);
    }

    public List<DashboardEntity> findByUserId(Long userId) {
        return _dashboardRepository.findByUserId(userId);
    }

    public Page<DashboardDetailsOutput> getDashboards(Long userId, String search, Pageable pageable) throws Exception {
        Page<DashboardDetailsOutput> list = _dashboardRepository.getAllDashboardsByUserId(userId, search, pageable);
        return list;
    }

    public Page<DashboardDetailsOutput> getAvailableDashboards(
        Long userId,
        Long reportId,
        String search,
        Pageable pageable
    )
        throws Exception {
        Page<DashboardDetailsOutput> list = _dashboardRepository.getAvailableDashboardsByUserId(
            userId,
            reportId,
            search,
            pageable
        );
        return list;
    }
}
