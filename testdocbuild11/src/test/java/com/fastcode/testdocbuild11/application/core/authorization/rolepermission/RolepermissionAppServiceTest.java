package com.fastcode.testdocbuild11.application.core.authorization.rolepermission;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.testdocbuild11.application.core.authorization.rolepermission.dto.*;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.fastcode.testdocbuild11.domain.core.authorization.permission.IPermissionManager;
import com.fastcode.testdocbuild11.domain.core.authorization.permission.PermissionEntity;
import com.fastcode.testdocbuild11.domain.core.authorization.role.IRoleManager;
import com.fastcode.testdocbuild11.domain.core.authorization.role.RoleEntity;
import com.fastcode.testdocbuild11.domain.core.authorization.rolepermission.*;
import com.fastcode.testdocbuild11.domain.core.authorization.rolepermission.QRolepermissionEntity;
import com.fastcode.testdocbuild11.domain.core.authorization.rolepermission.RolepermissionEntity;
import com.fastcode.testdocbuild11.domain.core.authorization.rolepermission.RolepermissionId;
import com.fastcode.testdocbuild11.domain.core.authorization.userrole.IUserroleManager;
import com.fastcode.testdocbuild11.security.JWTAppService;
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
public class RolepermissionAppServiceTest {

    @InjectMocks
    @Spy
    protected RolepermissionAppService _appService;

    @Mock
    protected JWTAppService jwtAppService;

    @Mock
    protected IUserroleManager _userroleManager;

    @Mock
    protected IRolepermissionManager _rolepermissionManager;

    @Mock
    protected IPermissionManager _permissionManager;

    @Mock
    protected IRoleManager _roleManager;

    @Mock
    protected IRolepermissionMapper _mapper;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    @Mock
    protected RolepermissionId rolepermissionId;

    private static final Long ID = 15L;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_appService);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findRolepermissionById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Mockito.when(_rolepermissionManager.findById(any(RolepermissionId.class))).thenReturn(null);
        Assertions.assertThat(_appService.findById(rolepermissionId)).isEqualTo(null);
    }

    @Test
    public void findRolepermissionById_IdIsNotNullAndIdExists_ReturnRolepermission() {
        RolepermissionEntity rolepermission = mock(RolepermissionEntity.class);
        Mockito.when(_rolepermissionManager.findById(any(RolepermissionId.class))).thenReturn(rolepermission);

        Assertions
            .assertThat(_appService.findById(rolepermissionId))
            .isEqualTo(_mapper.rolepermissionEntityToFindRolepermissionByIdOutput(rolepermission));
    }

    @Test
    public void createRolepermission_RolepermissionIsNotNullAndRolepermissionDoesNotExist_StoreRolepermission() {
        RolepermissionEntity rolepermissionEntity = mock(RolepermissionEntity.class);
        CreateRolepermissionInput rolepermissionInput = new CreateRolepermissionInput();

        PermissionEntity permission = mock(PermissionEntity.class);
        rolepermissionInput.setPermissionId(15L);

        Mockito.when(_permissionManager.findById(any(Long.class))).thenReturn(permission);

        RoleEntity role = mock(RoleEntity.class);
        rolepermissionInput.setRoleId(15L);

        Mockito.when(_roleManager.findById(any(Long.class))).thenReturn(role);

        Mockito
            .when(_mapper.createRolepermissionInputToRolepermissionEntity(any(CreateRolepermissionInput.class)))
            .thenReturn(rolepermissionEntity);
        Mockito.when(_rolepermissionManager.create(any(RolepermissionEntity.class))).thenReturn(rolepermissionEntity);

        Assertions
            .assertThat(_appService.create(rolepermissionInput))
            .isEqualTo(_mapper.rolepermissionEntityToCreateRolepermissionOutput(rolepermissionEntity));
    }

    @Test
    public void createRolepermission_RolepermissionIsNotNullAndRolepermissionDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {
        CreateRolepermissionInput rolepermission = mock(CreateRolepermissionInput.class);

        Mockito
            .when(_mapper.createRolepermissionInputToRolepermissionEntity(any(CreateRolepermissionInput.class)))
            .thenReturn(null);
        Assertions.assertThat(_appService.create(rolepermission)).isEqualTo(null);
    }

    @Test
    public void createRolepermission_RolepermissionIsNotNullAndRolepermissionDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {
        CreateRolepermissionInput rolepermission = new CreateRolepermissionInput();

        rolepermission.setPermissionId(15L);

        Mockito.when(_permissionManager.findById(any(Long.class))).thenReturn(null);
        Assertions.assertThat(_appService.create(rolepermission)).isEqualTo(null);
    }

    @Test
    public void updateRolepermission_RolepermissionIsNotNullAndRolepermissionDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {
        UpdateRolepermissionInput rolepermission = mock(UpdateRolepermissionInput.class);
        RolepermissionEntity rolepermissionEntity = mock(RolepermissionEntity.class);

        Mockito
            .when(_mapper.updateRolepermissionInputToRolepermissionEntity(any(UpdateRolepermissionInput.class)))
            .thenReturn(rolepermissionEntity);
        Assertions.assertThat(_appService.update(rolepermissionId, rolepermission)).isEqualTo(null);
    }

    @Test
    public void updateRolepermission_RolepermissionIsNotNullAndRolepermissionDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {
        UpdateRolepermissionInput rolepermission = new UpdateRolepermissionInput();
        rolepermission.setPermissionId(15L);

        Mockito.when(_permissionManager.findById(any(Long.class))).thenReturn(null);
        Assertions.assertThat(_appService.update(rolepermissionId, rolepermission)).isEqualTo(null);
    }

    @Test
    public void updateRolepermission_RolepermissionIdIsNotNullAndIdExists_ReturnUpdatedRolepermission() {
        RolepermissionEntity rolepermissionEntity = mock(RolepermissionEntity.class);
        UpdateRolepermissionInput rolepermission = mock(UpdateRolepermissionInput.class);

        Mockito
            .when(_mapper.updateRolepermissionInputToRolepermissionEntity(any(UpdateRolepermissionInput.class)))
            .thenReturn(rolepermissionEntity);
        Mockito.when(_rolepermissionManager.update(any(RolepermissionEntity.class))).thenReturn(rolepermissionEntity);
        Assertions
            .assertThat(_appService.update(rolepermissionId, rolepermission))
            .isEqualTo(_mapper.rolepermissionEntityToUpdateRolepermissionOutput(rolepermissionEntity));
    }

    @Test
    public void deleteRolepermission_RolepermissionIsNotNullAndRolepermissionExists_RolepermissionRemoved() {
        RolepermissionEntity rolepermissionEntity = mock(RolepermissionEntity.class);
        Mockito.when(_rolepermissionManager.findById(any(RolepermissionId.class))).thenReturn(rolepermissionEntity);

        _appService.delete(rolepermissionId);
        verify(_rolepermissionManager).delete(rolepermissionEntity);
    }

    @Test
    public void find_ListIsEmpty_ReturnList() throws Exception {
        List<RolepermissionEntity> list = new ArrayList<>();
        Page<RolepermissionEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindRolepermissionByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_rolepermissionManager.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void find_ListIsNotEmpty_ReturnList() throws Exception {
        List<RolepermissionEntity> list = new ArrayList<>();
        RolepermissionEntity rolepermission = mock(RolepermissionEntity.class);
        list.add(rolepermission);
        Page<RolepermissionEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindRolepermissionByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");
        output.add(_mapper.rolepermissionEntityToFindRolepermissionByIdOutput(rolepermission));

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_rolepermissionManager.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
        QRolepermissionEntity rolepermission = QRolepermissionEntity.rolepermissionEntity;
        SearchFields searchFields = new SearchFields();
        searchFields.setOperator("equals");
        searchFields.setSearchValue("xyz");
        Map<String, SearchFields> map = new HashMap<>();
        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("xyz", String.valueOf(ID));
        BooleanBuilder builder = new BooleanBuilder();
        Assertions.assertThat(_appService.searchKeyValuePair(rolepermission, map, searchMap)).isEqualTo(builder);
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
        _appService.checkProperties(list);
    }

    @Test
    public void search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
        Map<String, SearchFields> map = new HashMap<>();
        QRolepermissionEntity rolepermission = QRolepermissionEntity.rolepermissionEntity;
        List<SearchFields> fieldsList = new ArrayList<>();
        SearchFields fields = new SearchFields();
        SearchCriteria search = new SearchCriteria();
        search.setType(3);
        search.setValue("xyz");
        search.setOperator("equals");
        fields.setOperator("equals");
        fields.setSearchValue("xyz");
        fieldsList.add(fields);
        search.setFields(fieldsList);
        BooleanBuilder builder = new BooleanBuilder();
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
        Mockito
            .doReturn(builder)
            .when(_appService)
            .searchKeyValuePair(any(QRolepermissionEntity.class), any(HashMap.class), any(HashMap.class));

        Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
    }

    @Test
    public void search_StringIsNull_ReturnNull() throws Exception {
        Assertions.assertThat(_appService.search(null)).isEqualTo(null);
    }

    //Permission
    @Test
    public void GetPermission_IfRolepermissionIdAndPermissionIdIsNotNullAndRolepermissionExists_ReturnPermission() {
        RolepermissionEntity rolepermissionEntity = mock(RolepermissionEntity.class);
        PermissionEntity permission = mock(PermissionEntity.class);

        Mockito.when(_rolepermissionManager.findById(any(RolepermissionId.class))).thenReturn(rolepermissionEntity);
        Mockito.when(_rolepermissionManager.getPermission(any(RolepermissionId.class))).thenReturn(permission);
        Assertions
            .assertThat(_appService.getPermission(rolepermissionId))
            .isEqualTo(_mapper.permissionEntityToGetPermissionOutput(permission, rolepermissionEntity));
    }

    @Test
    public void GetPermission_IfRolepermissionIdAndPermissionIdIsNotNullAndRolepermissionDoesNotExist_ReturnNull() {
        Mockito.when(_rolepermissionManager.findById(any(RolepermissionId.class))).thenReturn(null);
        Assertions.assertThat(_appService.getPermission(rolepermissionId)).isEqualTo(null);
    }

    //Role
    @Test
    public void GetRole_IfRolepermissionIdAndRoleIdIsNotNullAndRolepermissionExists_ReturnRole() {
        RolepermissionEntity rolepermissionEntity = mock(RolepermissionEntity.class);
        RoleEntity role = mock(RoleEntity.class);

        Mockito.when(_rolepermissionManager.findById(any(RolepermissionId.class))).thenReturn(rolepermissionEntity);
        Mockito.when(_rolepermissionManager.getRole(any(RolepermissionId.class))).thenReturn(role);
        Assertions
            .assertThat(_appService.getRole(rolepermissionId))
            .isEqualTo(_mapper.roleEntityToGetRoleOutput(role, rolepermissionEntity));
    }

    @Test
    public void GetRole_IfRolepermissionIdAndRoleIdIsNotNullAndRolepermissionDoesNotExist_ReturnNull() {
        Mockito.when(_rolepermissionManager.findById(any(RolepermissionId.class))).thenReturn(null);
        Assertions.assertThat(_appService.getRole(rolepermissionId)).isEqualTo(null);
    }

    @Test
    public void ParseRolepermissionKey_KeysStringIsNotEmptyAndKeyValuePairExists_ReturnRolepermissionId() {
        String keyString = "permissionId=15,roleId=15";

        RolepermissionId rolepermissionId = new RolepermissionId();
        rolepermissionId.setPermissionId(15L);
        rolepermissionId.setRoleId(15L);

        Assertions
            .assertThat(_appService.parseRolepermissionKey(keyString))
            .isEqualToComparingFieldByField(rolepermissionId);
    }

    @Test
    public void ParseRolepermissionKey_KeysStringIsEmpty_ReturnNull() {
        String keyString = "";
        Assertions.assertThat(_appService.parseRolepermissionKey(keyString)).isEqualTo(null);
    }

    @Test
    public void ParseRolepermissionKey_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        String keyString = "permissionId";

        Assertions.assertThat(_appService.parseRolepermissionKey(keyString)).isEqualTo(null);
    }
}
