package com.fastcode.testdocbuild11.domain.core.authorization.rolepermission;

import com.fastcode.testdocbuild11.domain.core.authorization.permission.PermissionEntity;
import com.fastcode.testdocbuild11.domain.core.authorization.role.RoleEntity;
import com.querydsl.core.types.Predicate;
import java.time.*;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRolepermissionManager {
    RolepermissionEntity create(RolepermissionEntity rolepermission);

    void delete(RolepermissionEntity rolepermission);

    RolepermissionEntity update(RolepermissionEntity rolepermission);

    RolepermissionEntity findById(RolepermissionId rolepermissionId);

    Page<RolepermissionEntity> findAll(Predicate predicate, Pageable pageable);

    PermissionEntity getPermission(RolepermissionId rolepermissionId);

    RoleEntity getRole(RolepermissionId rolepermissionId);

    List<RolepermissionEntity> getRolepermissionsByRoleId(Long roleId);
}
