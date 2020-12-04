package com.fastcode.testdocbuild11.domain.core.authorization.userrole;

import com.fastcode.testdocbuild11.domain.core.authorization.role.RoleEntity;
import com.fastcode.testdocbuild11.domain.core.authorization.user.UserEntity;
import com.querydsl.core.types.Predicate;
import java.time.*;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserroleManager {
    UserroleEntity create(UserroleEntity userrole);

    void delete(UserroleEntity userrole);

    UserroleEntity update(UserroleEntity userrole);

    UserroleEntity findById(UserroleId userroleId);

    Page<UserroleEntity> findAll(Predicate predicate, Pageable pageable);

    RoleEntity getRole(UserroleId userroleId);

    UserEntity getUser(UserroleId userroleId);

    List<UserroleEntity> getUserrolesByUserId(Long id);

    List<UserroleEntity> getUserrolesByRoleId(Long roleId);
}
