package com.fastcode.testdocbuild11.application.core.authorization.role;

import com.fastcode.testdocbuild11.application.core.authorization.role.dto.*;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.fastcode.testdocbuild11.domain.core.authorization.role.IRoleManager;
import com.fastcode.testdocbuild11.domain.core.authorization.role.QRoleEntity;
import com.fastcode.testdocbuild11.domain.core.authorization.role.RoleEntity;
import com.querydsl.core.BooleanBuilder;
import java.time.*;
import java.util.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("roleAppService")
@RequiredArgsConstructor
public class RoleAppService implements IRoleAppService {

    @Qualifier("roleManager")
    @NonNull
    protected final IRoleManager _roleManager;

    @Qualifier("IRoleMapperImpl")
    @NonNull
    protected final IRoleMapper mapper;

    @NonNull
    protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreateRoleOutput create(CreateRoleInput input) {
        RoleEntity role = mapper.createRoleInputToRoleEntity(input);

        RoleEntity createdRole = _roleManager.create(role);
        return mapper.roleEntityToCreateRoleOutput(createdRole);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UpdateRoleOutput update(Long roleId, UpdateRoleInput input) {
        RoleEntity role = mapper.updateRoleInputToRoleEntity(input);

        RoleEntity updatedRole = _roleManager.update(role);
        return mapper.roleEntityToUpdateRoleOutput(updatedRole);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long roleId) {
        RoleEntity existing = _roleManager.findById(roleId);
        _roleManager.delete(existing);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindRoleByIdOutput findById(Long roleId) {
        RoleEntity foundRole = _roleManager.findById(roleId);
        if (foundRole == null) return null;

        return mapper.roleEntityToFindRoleByIdOutput(foundRole);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindRoleByNameOutput findByRoleName(String roleName) {
        RoleEntity foundRole = _roleManager.findByRoleName(roleName);
        if (foundRole == null) {
            return null;
        }

        return mapper.roleEntityToFindRoleByNameOutput(foundRole);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindRoleByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception {
        Page<RoleEntity> foundRole = _roleManager.findAll(search(search), pageable);
        List<RoleEntity> roleList = foundRole.getContent();
        Iterator<RoleEntity> roleIterator = roleList.iterator();
        List<FindRoleByIdOutput> output = new ArrayList<>();

        while (roleIterator.hasNext()) {
            RoleEntity role = roleIterator.next();
            output.add(mapper.roleEntityToFindRoleByIdOutput(role));
        }
        return output;
    }

    protected BooleanBuilder search(SearchCriteria search) throws Exception {
        QRoleEntity role = QRoleEntity.roleEntity;
        if (search != null) {
            Map<String, SearchFields> map = new HashMap<>();
            for (SearchFields fieldDetails : search.getFields()) {
                map.put(fieldDetails.getFieldName(), fieldDetails);
            }
            List<String> keysList = new ArrayList<String>(map.keySet());
            checkProperties(keysList);
            return searchKeyValuePair(role, map, search.getJoinColumns());
        }
        return null;
    }

    protected void checkProperties(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    list.get(i).replace("%20", "").trim().equals("displayName") ||
                    list.get(i).replace("%20", "").trim().equals("id") ||
                    list.get(i).replace("%20", "").trim().equals("name")
                )
            ) {
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    protected BooleanBuilder searchKeyValuePair(
        QRoleEntity role,
        Map<String, SearchFields> map,
        Map<String, String> joinColumns
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if (details.getKey().replace("%20", "").trim().equals("displayName")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    role.displayName.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    role.displayName.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    role.displayName.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("id")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(role.id.eq(Long.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(role.id.ne(Long.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        role.id.between(
                            Long.valueOf(details.getValue().getStartingValue()),
                            Long.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        role.id.goe(Long.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        role.id.loe(Long.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("name")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    role.name.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    role.name.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    role.name.ne(details.getValue().getSearchValue())
                );
            }
        }

        return builder;
    }

    public Map<String, String> parseRolepermissionsJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("roleId", keysString);

        return joinColumnMap;
    }

    public Map<String, String> parseUserrolesJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("roleId", keysString);

        return joinColumnMap;
    }
}
