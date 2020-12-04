package com.fastcode.testdocbuild11.application.core.authorization.role;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.testdocbuild11.application.core.authorization.role.dto.*;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.fastcode.testdocbuild11.domain.core.authorization.role.*;
import com.fastcode.testdocbuild11.domain.core.authorization.role.QRoleEntity;
import com.fastcode.testdocbuild11.domain.core.authorization.role.RoleEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class RoleAppServiceTest {

    @InjectMocks
    @Spy
    protected RoleAppService _appService;

    @Mock
    protected IRoleManager _roleManager;

    @Mock
    protected IRoleMapper _mapper;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    protected static Long ID = 15L;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_appService);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findRoleById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Mockito.when(_roleManager.findById(anyLong())).thenReturn(null);
        Assertions.assertThat(_appService.findById(ID)).isEqualTo(null);
    }

    @Test
    public void findRoleById_IdIsNotNullAndIdExists_ReturnRole() {
        RoleEntity role = mock(RoleEntity.class);
        Mockito.when(_roleManager.findById(anyLong())).thenReturn(role);

        Assertions.assertThat(_appService.findById(ID)).isEqualTo(_mapper.roleEntityToFindRoleByIdOutput(role));
    }

    @Test
    public void findRoleByName_NameIsNotNullAndRoleDoesNotExist_ReturnNull() {
        Mockito.when(_roleManager.findByRoleName(anyString())).thenReturn(null);
        Assertions.assertThat(_appService.findByRoleName("Role1")).isEqualTo(null);
    }

    @Test
    public void findRoleByName_NameIsNotNullAndRoleExists_ReturnARole() {
        RoleEntity role = mock(RoleEntity.class);

        Mockito.when(_roleManager.findByRoleName(anyString())).thenReturn(role);
        Assertions
            .assertThat(_appService.findByRoleName("Role1"))
            .isEqualTo(_mapper.roleEntityToFindRoleByNameOutput(role));
    }

    @Test
    public void createRole_RoleIsNotNullAndRoleDoesNotExist_StoreRole() {
        RoleEntity roleEntity = mock(RoleEntity.class);
        CreateRoleInput roleInput = new CreateRoleInput();

        Mockito.when(_mapper.createRoleInputToRoleEntity(any(CreateRoleInput.class))).thenReturn(roleEntity);
        Mockito.when(_roleManager.create(any(RoleEntity.class))).thenReturn(roleEntity);

        Assertions
            .assertThat(_appService.create(roleInput))
            .isEqualTo(_mapper.roleEntityToCreateRoleOutput(roleEntity));
    }

    @Test
    public void updateRole_RoleIdIsNotNullAndIdExists_ReturnUpdatedRole() {
        RoleEntity roleEntity = mock(RoleEntity.class);
        UpdateRoleInput role = mock(UpdateRoleInput.class);

        Mockito.when(_mapper.updateRoleInputToRoleEntity(any(UpdateRoleInput.class))).thenReturn(roleEntity);
        Mockito.when(_roleManager.update(any(RoleEntity.class))).thenReturn(roleEntity);
        Assertions.assertThat(_appService.update(ID, role)).isEqualTo(_mapper.roleEntityToUpdateRoleOutput(roleEntity));
    }

    @Test
    public void deleteRole_RoleIsNotNullAndRoleExists_RoleRemoved() {
        RoleEntity roleEntity = mock(RoleEntity.class);
        Mockito.when(_roleManager.findById(anyLong())).thenReturn(roleEntity);

        _appService.delete(ID);
        verify(_roleManager).delete(roleEntity);
    }

    @Test
    public void find_ListIsEmpty_ReturnList() throws Exception {
        List<RoleEntity> list = new ArrayList<>();
        Page<RoleEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindRoleByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_roleManager.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void find_ListIsNotEmpty_ReturnList() throws Exception {
        List<RoleEntity> list = new ArrayList<>();
        RoleEntity role = mock(RoleEntity.class);
        list.add(role);
        Page<RoleEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindRoleByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");
        output.add(_mapper.roleEntityToFindRoleByIdOutput(role));

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_roleManager.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
        QRoleEntity role = QRoleEntity.roleEntity;
        SearchFields searchFields = new SearchFields();
        searchFields.setOperator("equals");
        searchFields.setSearchValue("xyz");
        Map<String, SearchFields> map = new HashMap<>();
        map.put("displayName", searchFields);
        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("xyz", String.valueOf(ID));
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(role.displayName.eq("xyz"));
        Assertions.assertThat(_appService.searchKeyValuePair(role, map, searchMap)).isEqualTo(builder);
    }

    @Test(expected = Exception.class)
    public void checkProperties_PropertyDoesNotExist_ThrowException() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("xyz");
        _appService.checkProperties(list);
    }

    @Test
    public void checkProperties_PropertyExists_ReturnNothing() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("displayName");
        list.add("name");
        _appService.checkProperties(list);
    }

    @Test
    public void search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
        Map<String, SearchFields> map = new HashMap<>();
        QRoleEntity role = QRoleEntity.roleEntity;
        List<SearchFields> fieldsList = new ArrayList<>();
        SearchFields fields = new SearchFields();
        SearchCriteria search = new SearchCriteria();
        search.setType(3);
        search.setValue("xyz");
        search.setOperator("equals");
        fields.setFieldName("displayName");
        fields.setOperator("equals");
        fields.setSearchValue("xyz");
        fieldsList.add(fields);
        search.setFields(fieldsList);
        BooleanBuilder builder = new BooleanBuilder();
        builder.or(role.displayName.eq("xyz"));
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
        Mockito
            .doReturn(builder)
            .when(_appService)
            .searchKeyValuePair(any(QRoleEntity.class), any(HashMap.class), any(HashMap.class));

        Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
    }

    @Test
    public void search_StringIsNull_ReturnNull() throws Exception {
        Assertions.assertThat(_appService.search(null)).isEqualTo(null);
    }

    @Test
    public void ParserolepermissionsJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        String keyString = "15";
        joinColumnMap.put("roleId", keyString);
        Assertions.assertThat(_appService.parseRolepermissionsJoinColumn(keyString)).isEqualTo(joinColumnMap);
    }

    @Test
    public void ParseuserrolesJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        String keyString = "15";
        joinColumnMap.put("roleId", keyString);
        Assertions.assertThat(_appService.parseUserrolesJoinColumn(keyString)).isEqualTo(joinColumnMap);
    }
}
