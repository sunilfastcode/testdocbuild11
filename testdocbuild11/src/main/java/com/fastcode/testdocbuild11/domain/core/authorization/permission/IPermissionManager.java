package com.fastcode.testdocbuild11.domain.core.authorization.permission;

import com.querydsl.core.types.Predicate;
import java.time.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPermissionManager {
    PermissionEntity create(PermissionEntity permission);

    void delete(PermissionEntity permission);

    PermissionEntity update(PermissionEntity permission);

    PermissionEntity findById(Long id);

    Page<PermissionEntity> findAll(Predicate predicate, Pageable pageable);

    PermissionEntity findByPermissionName(String permissionName);
}
