package com.fastcode.testdocbuild11.domain.core.authorization.role;

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
import com.fastcode.testdocbuild11.domain.core.authorization.userrole.IUserroleRepository;
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
public class RoleManagerTest {

    @InjectMocks
    protected RoleManager _roleManager;

    @Mock
    protected IRoleRepository _roleRepository;

    @Mock
    protected IRolepermissionRepository _rolepermissionRepository;

    @Mock
    protected IUserroleRepository _userroleRepository;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    protected static Long ID = 15L;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_roleManager);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findRoleById_IdIsNotNullAndIdExists_ReturnRole() {
        RoleEntity role = mock(RoleEntity.class);

        Optional<RoleEntity> dbRole = Optional.of((RoleEntity) role);
        Mockito.when(_roleRepository.findById(anyLong())).thenReturn(dbRole);
        Assertions.assertThat(_roleManager.findById(ID)).isEqualTo(role);
    }

    @Test
    public void findRoleById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Mockito.<Optional<RoleEntity>>when(_roleRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThat(_roleManager.findById(ID)).isEqualTo(null);
    }

    @Test
    public void createRole_RoleIsNotNullAndRoleDoesNotExist_StoreRole() {
        RoleEntity role = mock(RoleEntity.class);
        Mockito.when(_roleRepository.save(any(RoleEntity.class))).thenReturn(role);
        Assertions.assertThat(_roleManager.create(role)).isEqualTo(role);
    }

    @Test
    public void deleteRole_RoleExists_RemoveRole() {
        RoleEntity role = mock(RoleEntity.class);
        _roleManager.delete(role);
        verify(_roleRepository).delete(role);
    }

    @Test
    public void updateRole_RoleIsNotNullAndRoleExists_UpdateRole() {
        RoleEntity role = mock(RoleEntity.class);
        Mockito.when(_roleRepository.save(any(RoleEntity.class))).thenReturn(role);
        Assertions.assertThat(_roleManager.update(role)).isEqualTo(role);
    }

    @Test
    public void findAll_PageableIsNotNull_ReturnPage() {
        Page<RoleEntity> role = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        Predicate predicate = mock(Predicate.class);

        Mockito.when(_roleRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(role);
        Assertions.assertThat(_roleManager.findAll(predicate, pageable)).isEqualTo(role);
    }
}
