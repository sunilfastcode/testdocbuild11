package com.fastcode.testdocbuild11.addons.reporting.application.dashboardversion;

import com.fastcode.testdocbuild11.addons.reporting.application.dashboardversion.dto.*;
import com.fastcode.testdocbuild11.addons.reporting.domain.dashboard.DashboardEntity;
import com.fastcode.testdocbuild11.addons.reporting.domain.dashboard.IDashboardManager;
import com.fastcode.testdocbuild11.addons.reporting.domain.dashboardversion.DashboardversionEntity;
import com.fastcode.testdocbuild11.addons.reporting.domain.dashboardversion.DashboardversionId;
import com.fastcode.testdocbuild11.addons.reporting.domain.dashboardversion.IDashboardversionManager;
import com.fastcode.testdocbuild11.addons.reporting.domain.dashboardversion.QDashboardversionEntity;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.fastcode.testdocbuild11.domain.core.authorization.user.UserEntity;
import com.fastcode.testdocbuild11.domain.extended.authorization.user.IUserManagerExtended;
import com.querydsl.core.BooleanBuilder;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("dashboardversionAppService")
public class DashboardversionAppService implements IDashboardversionAppService {

    static final int case1 = 1;
    static final int case2 = 2;
    static final int case3 = 3;

    @Autowired
    @Qualifier("dashboardversionManager")
    protected IDashboardversionManager _dashboardversionManager;

    @Autowired
    @Qualifier("userManagerExtended")
    protected IUserManagerExtended _userManager;

    @Autowired
    @Qualifier("dashboardManager")
    protected IDashboardManager _dashboardManager;

    @Autowired
    @Qualifier("IDashboardversionMapperImpl")
    protected IDashboardversionMapper mapper;

    @Autowired
    protected LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreateDashboardversionOutput create(CreateDashboardversionInput input) {
        DashboardversionEntity dashboardversion = mapper.createDashboardversionInputToDashboardversionEntity(input);
        if (input.getUserId() != null) {
            UserEntity foundUser = _userManager.findById(input.getUserId());

            if (foundUser != null) {
                dashboardversion.setUser(foundUser);
            }
        }

        if (input.getDashboardId() != null) {
            DashboardEntity foundDashboard = _dashboardManager.findById(input.getDashboardId());
            if (foundDashboard != null) {
                dashboardversion.setDashboard(foundDashboard);
            }
        }
        dashboardversion.setDashboardVersion("running");
        dashboardversion.setIsRefreshed(true);
        DashboardversionEntity createdRunningDashboardversion = _dashboardversionManager.create(dashboardversion);
        dashboardversion = mapper.createDashboardversionInputToDashboardversionEntity(input);
        dashboardversion.setDashboardVersion("published");
        DashboardversionEntity createdPublishedDashboardversion = _dashboardversionManager.create(dashboardversion);

        return mapper.dashboardversionEntityToCreateDashboardversionOutput(createdRunningDashboardversion);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UpdateDashboardversionOutput update(
        DashboardversionId dashboardversionId,
        UpdateDashboardversionInput input
    ) {
        DashboardversionEntity dashboardversion = mapper.updateDashboardversionInputToDashboardversionEntity(input);

        if (input.getUserId() != null) {
            UserEntity foundUser = _userManager.findById(input.getUserId());

            if (foundUser != null) {
                dashboardversion.setUser(foundUser);
            }
        }

        if (input.getDashboardId() != null) {
            DashboardEntity foundDashboard = _dashboardManager.findById(input.getDashboardId());
            dashboardversion.setDashboard(foundDashboard);
        }

        dashboardversion.setDashboardVersion(dashboardversionId.getDashboardVersion());
        DashboardversionEntity updatedDashboardversion = _dashboardversionManager.update(dashboardversion);

        return mapper.dashboardversionEntityToUpdateDashboardversionOutput(updatedDashboardversion);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(DashboardversionId dashboardversionId) {
        DashboardversionEntity existing = _dashboardversionManager.findById(dashboardversionId);
        _dashboardversionManager.delete(existing);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindDashboardversionByIdOutput findById(DashboardversionId dashboardversionId) {
        DashboardversionEntity foundDashboardversion = _dashboardversionManager.findById(dashboardversionId);
        if (foundDashboardversion == null) return null;

        FindDashboardversionByIdOutput output = mapper.dashboardversionEntityToFindDashboardversionByIdOutput(
            foundDashboardversion
        );
        return output;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetUserOutput getUser(DashboardversionId dashboardversionId) {
        DashboardversionEntity foundDashboardversion = _dashboardversionManager.findById(dashboardversionId);
        if (foundDashboardversion == null) {
            logHelper.getLogger().error("There does not exist a dashboardversion wth a id=%s", dashboardversionId);
            return null;
        }

        UserEntity re = _dashboardversionManager.getUser(dashboardversionId);
        return mapper.userEntityToGetUserOutput(re, foundDashboardversion);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindDashboardversionByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception {
        Page<DashboardversionEntity> foundDashboardversion = _dashboardversionManager.findAll(search(search), pageable);
        List<DashboardversionEntity> dashboardversionList = foundDashboardversion.getContent();
        Iterator<DashboardversionEntity> dashboardversionIterator = dashboardversionList.iterator();
        List<FindDashboardversionByIdOutput> output = new ArrayList<>();

        while (dashboardversionIterator.hasNext()) {
            output.add(mapper.dashboardversionEntityToFindDashboardversionByIdOutput(dashboardversionIterator.next()));
        }
        return output;
    }

    protected BooleanBuilder search(SearchCriteria search) throws Exception {
        QDashboardversionEntity dashboardversion = QDashboardversionEntity.dashboardversionEntity;
        if (search != null) {
            Map<String, SearchFields> map = new HashMap<>();
            for (SearchFields fieldDetails : search.getFields()) {
                map.put(fieldDetails.getFieldName(), fieldDetails);
            }
            List<String> keysList = new ArrayList<String>(map.keySet());
            checkProperties(keysList);
            return searchKeyValuePair(dashboardversion, map, search.getJoinColumns());
        }
        return null;
    }

    protected void checkProperties(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    list.get(i).replace("%20", "").trim().equals("userId") ||
                    list.get(i).replace("%20", "").trim().equals("user") ||
                    list.get(i).replace("%20", "").trim().equals("description") ||
                    list.get(i).replace("%20", "").trim().equals("id") ||
                    list.get(i).replace("%20", "").trim().equals("reportdashboard") ||
                    list.get(i).replace("%20", "").trim().equals("title")
                )
            ) {
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    protected BooleanBuilder searchKeyValuePair(
        QDashboardversionEntity dashboardversion,
        Map<String, SearchFields> map,
        Map<String, String> joinColumns
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if (details.getKey().replace("%20", "").trim().equals("description")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    dashboardversion.description.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    dashboardversion.description.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    dashboardversion.description.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("title")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    dashboardversion.title.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    dashboardversion.title.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    dashboardversion.title.ne(details.getValue().getSearchValue())
                );
            }
        }

        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("userId")) {
                builder.and(dashboardversion.user.id.eq(Long.parseLong(joinCol.getValue())));
            }
        }

        return builder;
    }

    public Map<String, String> parseReportdashboardJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("dashboardversionId", keysString);
        return joinColumnMap;
    }
}
