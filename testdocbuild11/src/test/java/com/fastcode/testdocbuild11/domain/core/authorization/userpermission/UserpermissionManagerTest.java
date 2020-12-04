package com.fastcode.testdocbuild11.domain.core.authorization.userpermission;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.fastcode.testdocbuild11.domain.core.authorization.permission.IPermissionRepository;
import com.fastcode.testdocbuild11.domain.core.authorization.permission.PermissionEntity;
import com.fastcode.testdocbuild11.domain.core.authorization.user.IUserRepository;
import com.fastcode.testdocbuild11.domain.core.authorization.user.UserEntity;
import com.querydsl.core.types.Predicate;
import java.time.*;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserpermissionManagerTest {

    @InjectMocks
    protected UserpermissionManager _userpermissionManager;

    @Mock
    protected IUserpermissionRepository _userpermissionRepository;

    @Mock
    protected IPermissionRepository _permissionRepository;

    @Mock
    protected IUserRepository _userRepository;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    @Mock
    protected UserpermissionId userpermissionId;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_userpermissionManager);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findUserpermissionById_IdIsNotNullAndIdExists_ReturnUserpermission() {
        UserpermissionEntity userpermission = mock(UserpermissionEntity.class);

        Optional<UserpermissionEntity> dbUserpermission = Optional.of((UserpermissionEntity) userpermission);
        Mockito.when(_userpermissionRepository.findById(any(UserpermissionId.class))).thenReturn(dbUserpermission);
        Assertions.assertThat(_userpermissionManager.findById(userpermissionId)).isEqualTo(userpermission);
    }

    @Test
    public void findUserpermissionById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Mockito
            .<Optional<UserpermissionEntity>>when(_userpermissionRepository.findById(any(UserpermissionId.class)))
            .thenReturn(Optional.empty());
        Assertions.assertThat(_userpermissionManager.findById(userpermissionId)).isEqualTo(null);
    }

    @Test
    public void createUserpermission_UserpermissionIsNotNullAndUserpermissionDoesNotExist_StoreUserpermission() {
        UserpermissionEntity userpermission = mock(UserpermissionEntity.class);
        Mockito.when(_userpermissionRepository.save(any(UserpermissionEntity.class))).thenReturn(userpermission);
        Assertions.assertThat(_userpermissionManager.create(userpermission)).isEqualTo(userpermission);
    }

    @Test
    public void deleteUserpermission_UserpermissionExists_RemoveUserpermission() {
        UserpermissionEntity userpermission = mock(UserpermissionEntity.class);
        _userpermissionManager.delete(userpermission);
        verify(_userpermissionRepository).delete(userpermission);
    }

    @Test
    public void updateUserpermission_UserpermissionIsNotNullAndUserpermissionExists_UpdateUserpermission() {
        UserpermissionEntity userpermission = mock(UserpermissionEntity.class);
        Mockito.when(_userpermissionRepository.save(any(UserpermissionEntity.class))).thenReturn(userpermission);
        Assertions.assertThat(_userpermissionManager.update(userpermission)).isEqualTo(userpermission);
    }

    @Test
    public void findAll_PageableIsNotNull_ReturnPage() {
        Page<UserpermissionEntity> userpermission = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        Predicate predicate = mock(Predicate.class);

        Mockito
            .when(_userpermissionRepository.findAll(any(Predicate.class), any(Pageable.class)))
            .thenReturn(userpermission);
        Assertions.assertThat(_userpermissionManager.findAll(predicate, pageable)).isEqualTo(userpermission);
    }

    //Permission
    @Test
    public void getPermission_if_UserpermissionIdIsNotNull_returnPermission() {
        UserpermissionEntity userpermissionEntity = mock(UserpermissionEntity.class);
        PermissionEntity permission = mock(PermissionEntity.class);

        Optional<UserpermissionEntity> dbUserpermission = Optional.of((UserpermissionEntity) userpermissionEntity);
        Mockito
            .<Optional<UserpermissionEntity>>when(_userpermissionRepository.findById(any(UserpermissionId.class)))
            .thenReturn(dbUserpermission);
        Mockito.when(userpermissionEntity.getPermission()).thenReturn(permission);
        Assertions.assertThat(_userpermissionManager.getPermission(userpermissionId)).isEqualTo(permission);
    }

    //User
    @Test
    public void getUser_if_UserpermissionIdIsNotNull_returnUser() {
        UserpermissionEntity userpermissionEntity = mock(UserpermissionEntity.class);
        UserEntity user = mock(UserEntity.class);

        Optional<UserpermissionEntity> dbUserpermission = Optional.of((UserpermissionEntity) userpermissionEntity);
        Mockito
            .<Optional<UserpermissionEntity>>when(_userpermissionRepository.findById(any(UserpermissionId.class)))
            .thenReturn(dbUserpermission);
        Mockito.when(userpermissionEntity.getUser()).thenReturn(user);
        Assertions.assertThat(_userpermissionManager.getUser(userpermissionId)).isEqualTo(user);
    }
}
