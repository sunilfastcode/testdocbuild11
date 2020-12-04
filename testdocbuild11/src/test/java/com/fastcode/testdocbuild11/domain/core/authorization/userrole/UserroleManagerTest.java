package com.fastcode.testdocbuild11.domain.core.authorization.userrole;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.fastcode.testdocbuild11.domain.core.authorization.role.IRoleRepository;
import com.fastcode.testdocbuild11.domain.core.authorization.role.RoleEntity;
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
public class UserroleManagerTest {

    @InjectMocks
    protected UserroleManager _userroleManager;

    @Mock
    protected IUserroleRepository _userroleRepository;

    @Mock
    protected IRoleRepository _roleRepository;

    @Mock
    protected IUserRepository _userRepository;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    @Mock
    protected UserroleId userroleId;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_userroleManager);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findUserroleById_IdIsNotNullAndIdExists_ReturnUserrole() {
        UserroleEntity userrole = mock(UserroleEntity.class);

        Optional<UserroleEntity> dbUserrole = Optional.of((UserroleEntity) userrole);
        Mockito.when(_userroleRepository.findById(any(UserroleId.class))).thenReturn(dbUserrole);
        Assertions.assertThat(_userroleManager.findById(userroleId)).isEqualTo(userrole);
    }

    @Test
    public void findUserroleById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Mockito
            .<Optional<UserroleEntity>>when(_userroleRepository.findById(any(UserroleId.class)))
            .thenReturn(Optional.empty());
        Assertions.assertThat(_userroleManager.findById(userroleId)).isEqualTo(null);
    }

    @Test
    public void createUserrole_UserroleIsNotNullAndUserroleDoesNotExist_StoreUserrole() {
        UserroleEntity userrole = mock(UserroleEntity.class);
        Mockito.when(_userroleRepository.save(any(UserroleEntity.class))).thenReturn(userrole);
        Assertions.assertThat(_userroleManager.create(userrole)).isEqualTo(userrole);
    }

    @Test
    public void deleteUserrole_UserroleExists_RemoveUserrole() {
        UserroleEntity userrole = mock(UserroleEntity.class);
        _userroleManager.delete(userrole);
        verify(_userroleRepository).delete(userrole);
    }

    @Test
    public void updateUserrole_UserroleIsNotNullAndUserroleExists_UpdateUserrole() {
        UserroleEntity userrole = mock(UserroleEntity.class);
        Mockito.when(_userroleRepository.save(any(UserroleEntity.class))).thenReturn(userrole);
        Assertions.assertThat(_userroleManager.update(userrole)).isEqualTo(userrole);
    }

    @Test
    public void findAll_PageableIsNotNull_ReturnPage() {
        Page<UserroleEntity> userrole = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        Predicate predicate = mock(Predicate.class);

        Mockito.when(_userroleRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(userrole);
        Assertions.assertThat(_userroleManager.findAll(predicate, pageable)).isEqualTo(userrole);
    }

    //Role
    @Test
    public void getRole_if_UserroleIdIsNotNull_returnRole() {
        UserroleEntity userroleEntity = mock(UserroleEntity.class);
        RoleEntity role = mock(RoleEntity.class);

        Optional<UserroleEntity> dbUserrole = Optional.of((UserroleEntity) userroleEntity);
        Mockito
            .<Optional<UserroleEntity>>when(_userroleRepository.findById(any(UserroleId.class)))
            .thenReturn(dbUserrole);
        Mockito.when(userroleEntity.getRole()).thenReturn(role);
        Assertions.assertThat(_userroleManager.getRole(userroleId)).isEqualTo(role);
    }

    //User
    @Test
    public void getUser_if_UserroleIdIsNotNull_returnUser() {
        UserroleEntity userroleEntity = mock(UserroleEntity.class);
        UserEntity user = mock(UserEntity.class);

        Optional<UserroleEntity> dbUserrole = Optional.of((UserroleEntity) userroleEntity);
        Mockito
            .<Optional<UserroleEntity>>when(_userroleRepository.findById(any(UserroleId.class)))
            .thenReturn(dbUserrole);
        Mockito.when(userroleEntity.getUser()).thenReturn(user);
        Assertions.assertThat(_userroleManager.getUser(userroleId)).isEqualTo(user);
    }
}
