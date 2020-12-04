package com.fastcode.testdocbuild11.domain.core.authorization.userpermission;

import com.fastcode.testdocbuild11.domain.core.authorization.permission.PermissionEntity;
import com.fastcode.testdocbuild11.domain.core.authorization.user.UserEntity;
import com.querydsl.core.types.Predicate;
import java.time.*;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserpermissionManager {
    UserpermissionEntity create(UserpermissionEntity userpermission);

    void delete(UserpermissionEntity userpermission);

    UserpermissionEntity update(UserpermissionEntity userpermission);

    UserpermissionEntity findById(UserpermissionId userpermissionId);

    Page<UserpermissionEntity> findAll(Predicate predicate, Pageable pageable);

    PermissionEntity getPermission(UserpermissionId userpermissionId);

    UserEntity getUser(UserpermissionId userpermissionId);

    List<UserpermissionEntity> getUserpermissionsByUserId(Long id);
}
