package com.fastcode.testdocbuild11.domain.core.authorization.userpermission;

import com.fastcode.testdocbuild11.domain.core.authorization.permission.IPermissionRepository;
import com.fastcode.testdocbuild11.domain.core.authorization.permission.PermissionEntity;
import com.fastcode.testdocbuild11.domain.core.authorization.user.IUserRepository;
import com.fastcode.testdocbuild11.domain.core.authorization.user.UserEntity;
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

@Component("userpermissionManager")
@RequiredArgsConstructor
public class UserpermissionManager implements IUserpermissionManager {

    @Qualifier("userpermissionRepository")
    @NonNull
    protected final IUserpermissionRepository _userpermissionRepository;

    @Qualifier("permissionRepository")
    @NonNull
    protected final IPermissionRepository _permissionRepository;

    @Qualifier("userRepository")
    @NonNull
    protected final IUserRepository _userRepository;

    public UserpermissionEntity create(UserpermissionEntity userpermission) {
        return _userpermissionRepository.save(userpermission);
    }

    public void delete(UserpermissionEntity userpermission) {
        _userpermissionRepository.delete(userpermission);
    }

    public UserpermissionEntity update(UserpermissionEntity userpermission) {
        return _userpermissionRepository.save(userpermission);
    }

    public UserpermissionEntity findById(UserpermissionId userpermissionId) {
        Optional<UserpermissionEntity> dbUserpermission = _userpermissionRepository.findById(userpermissionId);
        return dbUserpermission.orElse(null);
    }

    public Page<UserpermissionEntity> findAll(Predicate predicate, Pageable pageable) {
        return _userpermissionRepository.findAll(predicate, pageable);
    }

    public PermissionEntity getPermission(UserpermissionId userpermissionId) {
        Optional<UserpermissionEntity> dbUserpermission = _userpermissionRepository.findById(userpermissionId);
        if (dbUserpermission.isPresent()) {
            UserpermissionEntity existingUserpermission = dbUserpermission.get();
            return existingUserpermission.getPermission();
        } else {
            return null;
        }
    }

    public UserEntity getUser(UserpermissionId userpermissionId) {
        Optional<UserpermissionEntity> dbUserpermission = _userpermissionRepository.findById(userpermissionId);
        if (dbUserpermission.isPresent()) {
            UserpermissionEntity existingUserpermission = dbUserpermission.get();
            return existingUserpermission.getUser();
        } else {
            return null;
        }
    }

    public List<UserpermissionEntity> getUserpermissionsByUserId(Long id) {
        return _userpermissionRepository.findByUserId(id);
    }
}
