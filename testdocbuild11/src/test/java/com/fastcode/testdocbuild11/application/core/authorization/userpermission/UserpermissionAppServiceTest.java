package com.fastcode.testdocbuild11.application.core.authorization.userpermission;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.testdocbuild11.application.core.authorization.userpermission.dto.*;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.fastcode.testdocbuild11.domain.core.authorization.permission.IPermissionManager;
import com.fastcode.testdocbuild11.domain.core.authorization.permission.PermissionEntity;
import com.fastcode.testdocbuild11.domain.core.authorization.user.IUserManager;
import com.fastcode.testdocbuild11.domain.core.authorization.user.UserEntity;
import com.fastcode.testdocbuild11.domain.core.authorization.userpermission.*;
import com.fastcode.testdocbuild11.domain.core.authorization.userpermission.QUserpermissionEntity;
import com.fastcode.testdocbuild11.domain.core.authorization.userpermission.UserpermissionEntity;
import com.fastcode.testdocbuild11.domain.core.authorization.userpermission.UserpermissionId;
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
public class UserpermissionAppServiceTest {

    @InjectMocks
    @Spy
    protected UserpermissionAppService _appService;

    @Mock
    protected IUserpermissionManager _userpermissionManager;

    @Mock
    protected IPermissionManager _permissionManager;

    @Mock
    protected IUserManager _userManager;

    @Mock
    protected IUserpermissionMapper _mapper;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    @Mock
    protected UserpermissionId userpermissionId;

    private static final Long ID = 15L;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_appService);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findUserpermissionById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Mockito.when(_userpermissionManager.findById(any(UserpermissionId.class))).thenReturn(null);
        Assertions.assertThat(_appService.findById(userpermissionId)).isEqualTo(null);
    }

    @Test
    public void findUserpermissionById_IdIsNotNullAndIdExists_ReturnUserpermission() {
        UserpermissionEntity userpermission = mock(UserpermissionEntity.class);
        Mockito.when(_userpermissionManager.findById(any(UserpermissionId.class))).thenReturn(userpermission);

        Assertions
            .assertThat(_appService.findById(userpermissionId))
            .isEqualTo(_mapper.userpermissionEntityToFindUserpermissionByIdOutput(userpermission));
    }

    @Test
    public void createUserpermission_UserpermissionIsNotNullAndUserpermissionDoesNotExist_StoreUserpermission() {
        UserpermissionEntity userpermissionEntity = mock(UserpermissionEntity.class);
        CreateUserpermissionInput userpermissionInput = new CreateUserpermissionInput();

        PermissionEntity permission = mock(PermissionEntity.class);
        userpermissionInput.setPermissionId(15L);

        Mockito.when(_permissionManager.findById(any(Long.class))).thenReturn(permission);

        UserEntity user = mock(UserEntity.class);
        userpermissionInput.setUserId(15L);

        Mockito.when(_userManager.findById(any(Long.class))).thenReturn(user);

        Mockito
            .when(_mapper.createUserpermissionInputToUserpermissionEntity(any(CreateUserpermissionInput.class)))
            .thenReturn(userpermissionEntity);
        Mockito.when(_userpermissionManager.create(any(UserpermissionEntity.class))).thenReturn(userpermissionEntity);

        CreateUserpermissionOutput output = new CreateUserpermissionOutput();
        Mockito
            .when(_mapper.userpermissionEntityToCreateUserpermissionOutput(any(UserpermissionEntity.class)))
            .thenReturn(output);

        Assertions
            .assertThat(_appService.create(userpermissionInput))
            .isEqualTo(_mapper.userpermissionEntityToCreateUserpermissionOutput(userpermissionEntity));
    }

    @Test
    public void createUserpermission_UserpermissionIsNotNullAndUserpermissionDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {
        CreateUserpermissionInput userpermission = mock(CreateUserpermissionInput.class);

        Mockito
            .when(_mapper.createUserpermissionInputToUserpermissionEntity(any(CreateUserpermissionInput.class)))
            .thenReturn(null);
        Assertions.assertThat(_appService.create(userpermission)).isEqualTo(null);
    }

    @Test
    public void createUserpermission_UserpermissionIsNotNullAndUserpermissionDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {
        CreateUserpermissionInput userpermission = new CreateUserpermissionInput();

        userpermission.setPermissionId(15L);

        Mockito.when(_permissionManager.findById(any(Long.class))).thenReturn(null);
        Assertions.assertThat(_appService.create(userpermission)).isEqualTo(null);
    }

    @Test
    public void updateUserpermission_UserpermissionIsNotNullAndUserpermissionDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {
        UpdateUserpermissionInput userpermission = mock(UpdateUserpermissionInput.class);
        UserpermissionEntity userpermissionEntity = mock(UserpermissionEntity.class);

        Mockito
            .when(_mapper.updateUserpermissionInputToUserpermissionEntity(any(UpdateUserpermissionInput.class)))
            .thenReturn(userpermissionEntity);
        Assertions.assertThat(_appService.update(userpermissionId, userpermission)).isEqualTo(null);
    }

    @Test
    public void updateUserpermission_UserpermissionIsNotNullAndUserpermissionDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {
        UpdateUserpermissionInput userpermission = new UpdateUserpermissionInput();
        userpermission.setPermissionId(15L);

        Mockito.when(_permissionManager.findById(any(Long.class))).thenReturn(null);
        Assertions.assertThat(_appService.update(userpermissionId, userpermission)).isEqualTo(null);
    }

    @Test
    public void updateUserpermission_UserpermissionIdIsNotNullAndIdExists_ReturnUpdatedUserpermission() {
        UserpermissionEntity userpermissionEntity = mock(UserpermissionEntity.class);
        UpdateUserpermissionInput userpermission = mock(UpdateUserpermissionInput.class);

        Mockito
            .when(_mapper.updateUserpermissionInputToUserpermissionEntity(any(UpdateUserpermissionInput.class)))
            .thenReturn(userpermissionEntity);
        Mockito.when(_userpermissionManager.update(any(UserpermissionEntity.class))).thenReturn(userpermissionEntity);
        Assertions
            .assertThat(_appService.update(userpermissionId, userpermission))
            .isEqualTo(_mapper.userpermissionEntityToUpdateUserpermissionOutput(userpermissionEntity));
    }

    @Test
    public void deleteUserpermission_UserpermissionIsNotNullAndUserpermissionExists_UserpermissionRemoved() {
        UserpermissionEntity userpermissionEntity = mock(UserpermissionEntity.class);
        Mockito.when(_userpermissionManager.findById(any(UserpermissionId.class))).thenReturn(userpermissionEntity);

        _appService.delete(userpermissionId);
        verify(_userpermissionManager).delete(userpermissionEntity);
    }

    @Test
    public void find_ListIsEmpty_ReturnList() throws Exception {
        List<UserpermissionEntity> list = new ArrayList<>();
        Page<UserpermissionEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindUserpermissionByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_userpermissionManager.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void find_ListIsNotEmpty_ReturnList() throws Exception {
        List<UserpermissionEntity> list = new ArrayList<>();
        UserpermissionEntity userpermission = mock(UserpermissionEntity.class);
        list.add(userpermission);
        Page<UserpermissionEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindUserpermissionByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");
        output.add(_mapper.userpermissionEntityToFindUserpermissionByIdOutput(userpermission));

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_userpermissionManager.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
        QUserpermissionEntity userpermission = QUserpermissionEntity.userpermissionEntity;
        SearchFields searchFields = new SearchFields();
        searchFields.setOperator("equals");
        searchFields.setSearchValue("xyz");
        Map<String, SearchFields> map = new HashMap<>();
        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("xyz", String.valueOf(ID));
        BooleanBuilder builder = new BooleanBuilder();
        Assertions.assertThat(_appService.searchKeyValuePair(userpermission, map, searchMap)).isEqualTo(builder);
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
        QUserpermissionEntity userpermission = QUserpermissionEntity.userpermissionEntity;
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
            .searchKeyValuePair(any(QUserpermissionEntity.class), any(HashMap.class), any(HashMap.class));

        Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
    }

    @Test
    public void search_StringIsNull_ReturnNull() throws Exception {
        Assertions.assertThat(_appService.search(null)).isEqualTo(null);
    }

    //Permission
    @Test
    public void GetPermission_IfUserpermissionIdAndPermissionIdIsNotNullAndUserpermissionExists_ReturnPermission() {
        UserpermissionEntity userpermissionEntity = mock(UserpermissionEntity.class);
        PermissionEntity permission = mock(PermissionEntity.class);

        Mockito.when(_userpermissionManager.findById(any(UserpermissionId.class))).thenReturn(userpermissionEntity);
        Mockito.when(_userpermissionManager.getPermission(any(UserpermissionId.class))).thenReturn(permission);
        Assertions
            .assertThat(_appService.getPermission(userpermissionId))
            .isEqualTo(_mapper.permissionEntityToGetPermissionOutput(permission, userpermissionEntity));
    }

    @Test
    public void GetPermission_IfUserpermissionIdAndPermissionIdIsNotNullAndUserpermissionDoesNotExist_ReturnNull() {
        Mockito.when(_userpermissionManager.findById(any(UserpermissionId.class))).thenReturn(null);
        Assertions.assertThat(_appService.getPermission(userpermissionId)).isEqualTo(null);
    }

    //User
    @Test
    public void GetUser_IfUserpermissionIdAndUserIdIsNotNullAndUserpermissionExists_ReturnUser() {
        UserpermissionEntity userpermissionEntity = mock(UserpermissionEntity.class);
        UserEntity user = mock(UserEntity.class);

        Mockito.when(_userpermissionManager.findById(any(UserpermissionId.class))).thenReturn(userpermissionEntity);
        Mockito.when(_userpermissionManager.getUser(any(UserpermissionId.class))).thenReturn(user);
        Assertions
            .assertThat(_appService.getUser(userpermissionId))
            .isEqualTo(_mapper.userEntityToGetUserOutput(user, userpermissionEntity));
    }

    @Test
    public void GetUser_IfUserpermissionIdAndUserIdIsNotNullAndUserpermissionDoesNotExist_ReturnNull() {
        Mockito.when(_userpermissionManager.findById(any(UserpermissionId.class))).thenReturn(null);
        Assertions.assertThat(_appService.getUser(userpermissionId)).isEqualTo(null);
    }

    @Test
    public void ParseUserpermissionKey_KeysStringIsNotEmptyAndKeyValuePairExists_ReturnUserpermissionId() {
        String keyString = "permissionId=15,userId=15";

        UserpermissionId userpermissionId = new UserpermissionId();
        userpermissionId.setPermissionId(15L);
        userpermissionId.setUserId(15L);

        Assertions
            .assertThat(_appService.parseUserpermissionKey(keyString))
            .isEqualToComparingFieldByField(userpermissionId);
    }

    @Test
    public void ParseUserpermissionKey_KeysStringIsEmpty_ReturnNull() {
        String keyString = "";
        Assertions.assertThat(_appService.parseUserpermissionKey(keyString)).isEqualTo(null);
    }

    @Test
    public void ParseUserpermissionKey_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        String keyString = "permissionId";

        Assertions.assertThat(_appService.parseUserpermissionKey(keyString)).isEqualTo(null);
    }
}
