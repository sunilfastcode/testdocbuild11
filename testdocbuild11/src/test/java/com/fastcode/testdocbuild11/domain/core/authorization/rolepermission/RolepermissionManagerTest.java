package com.fastcode.testdocbuild11.domain.core.authorization.rolepermission;

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
import com.fastcode.testdocbuild11.domain.core.authorization.role.IRoleRepository;
import com.fastcode.testdocbuild11.domain.core.authorization.role.RoleEntity;
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
public class RolepermissionManagerTest {

    @InjectMocks
    protected RolepermissionManager _rolepermissionManager;

    @Mock
    protected IRolepermissionRepository _rolepermissionRepository;

    @Mock
    protected IPermissionRepository _permissionRepository;

    @Mock
    protected IRoleRepository _roleRepository;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    @Mock
    protected RolepermissionId rolepermissionId;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_rolepermissionManager);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findRolepermissionById_IdIsNotNullAndIdExists_ReturnRolepermission() {
        RolepermissionEntity rolepermission = mock(RolepermissionEntity.class);

        Optional<RolepermissionEntity> dbRolepermission = Optional.of((RolepermissionEntity) rolepermission);
        Mockito.when(_rolepermissionRepository.findById(any(RolepermissionId.class))).thenReturn(dbRolepermission);
        Assertions.assertThat(_rolepermissionManager.findById(rolepermissionId)).isEqualTo(rolepermission);
    }

    @Test
    public void findRolepermissionById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Mockito
            .<Optional<RolepermissionEntity>>when(_rolepermissionRepository.findById(any(RolepermissionId.class)))
            .thenReturn(Optional.empty());
        Assertions.assertThat(_rolepermissionManager.findById(rolepermissionId)).isEqualTo(null);
    }

    @Test
    public void createRolepermission_RolepermissionIsNotNullAndRolepermissionDoesNotExist_StoreRolepermission() {
        RolepermissionEntity rolepermission = mock(RolepermissionEntity.class);
        Mockito.when(_rolepermissionRepository.save(any(RolepermissionEntity.class))).thenReturn(rolepermission);
        Assertions.assertThat(_rolepermissionManager.create(rolepermission)).isEqualTo(rolepermission);
    }

    @Test
    public void deleteRolepermission_RolepermissionExists_RemoveRolepermission() {
        RolepermissionEntity rolepermission = mock(RolepermissionEntity.class);
        _rolepermissionManager.delete(rolepermission);
        verify(_rolepermissionRepository).delete(rolepermission);
    }

    @Test
    public void updateRolepermission_RolepermissionIsNotNullAndRolepermissionExists_UpdateRolepermission() {
        RolepermissionEntity rolepermission = mock(RolepermissionEntity.class);
        Mockito.when(_rolepermissionRepository.save(any(RolepermissionEntity.class))).thenReturn(rolepermission);
        Assertions.assertThat(_rolepermissionManager.update(rolepermission)).isEqualTo(rolepermission);
    }

    @Test
    public void findAll_PageableIsNotNull_ReturnPage() {
        Page<RolepermissionEntity> rolepermission = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        Predicate predicate = mock(Predicate.class);

        Mockito
            .when(_rolepermissionRepository.findAll(any(Predicate.class), any(Pageable.class)))
            .thenReturn(rolepermission);
        Assertions.assertThat(_rolepermissionManager.findAll(predicate, pageable)).isEqualTo(rolepermission);
    }

    //Permission
    @Test
    public void getPermission_if_RolepermissionIdIsNotNull_returnPermission() {
        RolepermissionEntity rolepermissionEntity = mock(RolepermissionEntity.class);
        PermissionEntity permission = mock(PermissionEntity.class);

        Optional<RolepermissionEntity> dbRolepermission = Optional.of((RolepermissionEntity) rolepermissionEntity);
        Mockito
            .<Optional<RolepermissionEntity>>when(_rolepermissionRepository.findById(any(RolepermissionId.class)))
            .thenReturn(dbRolepermission);
        Mockito.when(rolepermissionEntity.getPermission()).thenReturn(permission);
        Assertions.assertThat(_rolepermissionManager.getPermission(rolepermissionId)).isEqualTo(permission);
    }

    //Role
    @Test
    public void getRole_if_RolepermissionIdIsNotNull_returnRole() {
        RolepermissionEntity rolepermissionEntity = mock(RolepermissionEntity.class);
        RoleEntity role = mock(RoleEntity.class);

        Optional<RolepermissionEntity> dbRolepermission = Optional.of((RolepermissionEntity) rolepermissionEntity);
        Mockito
            .<Optional<RolepermissionEntity>>when(_rolepermissionRepository.findById(any(RolepermissionId.class)))
            .thenReturn(dbRolepermission);
        Mockito.when(rolepermissionEntity.getRole()).thenReturn(role);
        Assertions.assertThat(_rolepermissionManager.getRole(rolepermissionId)).isEqualTo(role);
    }
}
