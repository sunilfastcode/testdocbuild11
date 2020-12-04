package com.fastcode.testdocbuild11.domain.core.authorization.rolepermission;

import com.fastcode.testdocbuild11.domain.core.authorization.permission.IPermissionRepository;
import com.fastcode.testdocbuild11.domain.core.authorization.permission.PermissionEntity;
import com.fastcode.testdocbuild11.domain.core.authorization.role.IRoleRepository;
import com.fastcode.testdocbuild11.domain.core.authorization.role.RoleEntity;
import com.querydsl.core.types.Predicate;
import java.time.*;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component("rolepermissionManager")
@RequiredArgsConstructor
public class RolepermissionManager implements IRolepermissionManager {

    @Qualifier("rolepermissionRepository")
    @NonNull
    protected final IRolepermissionRepository _rolepermissionRepository;

    @Qualifier("permissionRepository")
    @NonNull
    protected final IPermissionRepository _permissionRepository;

    @Qualifier("roleRepository")
    @NonNull
    protected final IRoleRepository _roleRepository;

    public RolepermissionEntity create(RolepermissionEntity rolepermission) {
        return _rolepermissionRepository.save(rolepermission);
    }

    public void delete(RolepermissionEntity rolepermission) {
        _rolepermissionRepository.delete(rolepermission);
    }

    public RolepermissionEntity update(RolepermissionEntity rolepermission) {
        return _rolepermissionRepository.save(rolepermission);
    }

    public RolepermissionEntity findById(RolepermissionId rolepermissionId) {
        Optional<RolepermissionEntity> dbRolepermission = _rolepermissionRepository.findById(rolepermissionId);
        return dbRolepermission.orElse(null);
    }

    public Page<RolepermissionEntity> findAll(Predicate predicate, Pageable pageable) {
        return _rolepermissionRepository.findAll(predicate, pageable);
    }

    public PermissionEntity getPermission(RolepermissionId rolepermissionId) {
        Optional<RolepermissionEntity> dbRolepermission = _rolepermissionRepository.findById(rolepermissionId);
        if (dbRolepermission.isPresent()) {
            RolepermissionEntity existingRolepermission = dbRolepermission.get();
            return existingRolepermission.getPermission();
        } else {
            return null;
        }
    }

    public RoleEntity getRole(RolepermissionId rolepermissionId) {
        Optional<RolepermissionEntity> dbRolepermission = _rolepermissionRepository.findById(rolepermissionId);
        if (dbRolepermission.isPresent()) {
            RolepermissionEntity existingRolepermission = dbRolepermission.get();
            return existingRolepermission.getRole();
        } else {
            return null;
        }
    }

    public List<RolepermissionEntity> getRolepermissionsByRoleId(Long roleId) {
        return _rolepermissionRepository.findByRoleId(roleId);
    }
}
