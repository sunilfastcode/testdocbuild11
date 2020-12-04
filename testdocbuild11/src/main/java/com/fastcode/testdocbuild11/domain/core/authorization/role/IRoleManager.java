package com.fastcode.testdocbuild11.domain.core.authorization.role;

import com.querydsl.core.types.Predicate;
import java.time.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRoleManager {
    RoleEntity create(RoleEntity role);

    void delete(RoleEntity role);

    RoleEntity update(RoleEntity role);

    RoleEntity findById(Long id);

    Page<RoleEntity> findAll(Predicate predicate, Pageable pageable);

    RoleEntity findByRoleName(String roleName);
}
