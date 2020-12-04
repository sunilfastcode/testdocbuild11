package com.fastcode.testdocbuild11.domain.core.authorization.permission;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.fastcode.testdocbuild11.domain.core.authorization.rolepermission.IRolepermissionRepository;
import com.fastcode.testdocbuild11.domain.core.authorization.userpermission.IUserpermissionRepository;
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
public class PermissionManagerTest {

    @InjectMocks
    protected PermissionManager _permissionManager;

    @Mock
    protected IPermissionRepository _permissionRepository;

    @Mock
    protected IRolepermissionRepository _rolepermissionRepository;

    @Mock
    protected IUserpermissionRepository _userpermissionRepository;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    protected static Long ID = 15L;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_permissionManager);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findPermissionById_IdIsNotNullAndIdExists_ReturnPermission() {
        PermissionEntity permission = mock(PermissionEntity.class);

        Optional<PermissionEntity> dbPermission = Optional.of((PermissionEntity) permission);
        Mockito.when(_permissionRepository.findById(anyLong())).thenReturn(dbPermission);
        Assertions.assertThat(_permissionManager.findById(ID)).isEqualTo(permission);
    }

    @Test
    public void findPermissionById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Mockito
            .<Optional<PermissionEntity>>when(_permissionRepository.findById(anyLong()))
            .thenReturn(Optional.empty());
        Assertions.assertThat(_permissionManager.findById(ID)).isEqualTo(null);
    }

    @Test
    public void createPermission_PermissionIsNotNullAndPermissionDoesNotExist_StorePermission() {
        PermissionEntity permission = mock(PermissionEntity.class);
        Mockito.when(_permissionRepository.save(any(PermissionEntity.class))).thenReturn(permission);
        Assertions.assertThat(_permissionManager.create(permission)).isEqualTo(permission);
    }

    @Test
    public void deletePermission_PermissionExists_RemovePermission() {
        PermissionEntity permission = mock(PermissionEntity.class);
        _permissionManager.delete(permission);
        verify(_permissionRepository).delete(permission);
    }

    @Test
    public void updatePermission_PermissionIsNotNullAndPermissionExists_UpdatePermission() {
        PermissionEntity permission = mock(PermissionEntity.class);
        Mockito.when(_permissionRepository.save(any(PermissionEntity.class))).thenReturn(permission);
        Assertions.assertThat(_permissionManager.update(permission)).isEqualTo(permission);
    }

    @Test
    public void findAll_PageableIsNotNull_ReturnPage() {
        Page<PermissionEntity> permission = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        Predicate predicate = mock(Predicate.class);

        Mockito.when(_permissionRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(permission);
        Assertions.assertThat(_permissionManager.findAll(predicate, pageable)).isEqualTo(permission);
    }
}
