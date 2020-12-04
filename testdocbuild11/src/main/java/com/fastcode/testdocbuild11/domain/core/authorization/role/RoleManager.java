package com.fastcode.testdocbuild11.domain.core.authorization.role;

import com.fastcode.testdocbuild11.domain.core.authorization.rolepermission.IRolepermissionRepository;
import com.fastcode.testdocbuild11.domain.core.authorization.userrole.IUserroleRepository;
import com.querydsl.core.types.Predicate;
import java.time.*;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component("roleManager")
@RequiredArgsConstructor
public class RoleManager implements IRoleManager {

    @Qualifier("roleRepository")
    @NonNull
    protected final IRoleRepository _roleRepository;

    @Qualifier("rolepermissionRepository")
    @NonNull
    protected final IRolepermissionRepository _rolepermissionRepository;

    @Qualifier("userroleRepository")
    @NonNull
    protected final IUserroleRepository _userroleRepository;

    public RoleEntity create(RoleEntity role) {
        return _roleRepository.save(role);
    }

    public void delete(RoleEntity role) {
        _roleRepository.delete(role);
    }

    public RoleEntity update(RoleEntity role) {
        return _roleRepository.save(role);
    }

    public RoleEntity findById(Long roleId) {
        Optional<RoleEntity> dbRole = _roleRepository.findById(roleId);
        return dbRole.orElse(null);
    }

    public Page<RoleEntity> findAll(Predicate predicate, Pageable pageable) {
        return _roleRepository.findAll(predicate, pageable);
    }

    public RoleEntity findByRoleName(String roleName) {
        return _roleRepository.findByRoleName(roleName);
    }
}
