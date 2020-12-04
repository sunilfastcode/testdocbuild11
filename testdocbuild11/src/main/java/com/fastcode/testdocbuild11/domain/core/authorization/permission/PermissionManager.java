package com.fastcode.testdocbuild11.domain.core.authorization.permission;

import com.fastcode.testdocbuild11.domain.core.authorization.rolepermission.IRolepermissionRepository;
import com.fastcode.testdocbuild11.domain.core.authorization.userpermission.IUserpermissionRepository;
import com.querydsl.core.types.Predicate;
import java.time.*;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component("permissionManager")
@RequiredArgsConstructor
public class PermissionManager implements IPermissionManager {

    @Qualifier("permissionRepository")
    @NonNull
    protected final IPermissionRepository _permissionRepository;

    @Qualifier("rolepermissionRepository")
    @NonNull
    protected final IRolepermissionRepository _rolepermissionRepository;

    @Qualifier("userpermissionRepository")
    @NonNull
    protected final IUserpermissionRepository _userpermissionRepository;

    public PermissionEntity create(PermissionEntity permission) {
        return _permissionRepository.save(permission);
    }

    public void delete(PermissionEntity permission) {
        _permissionRepository.delete(permission);
    }

    public PermissionEntity update(PermissionEntity permission) {
        return _permissionRepository.save(permission);
    }

    public PermissionEntity findById(Long permissionId) {
        Optional<PermissionEntity> dbPermission = _permissionRepository.findById(permissionId);
        return dbPermission.orElse(null);
    }

    public Page<PermissionEntity> findAll(Predicate predicate, Pageable pageable) {
        return _permissionRepository.findAll(predicate, pageable);
    }

    public PermissionEntity findByPermissionName(String permissionName) {
        return _permissionRepository.findByPermissionName(permissionName);
    }
}
