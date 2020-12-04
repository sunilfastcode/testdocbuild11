package com.fastcode.testdocbuild11.domain.core.authorization.userrole;

import com.fastcode.testdocbuild11.domain.core.authorization.role.IRoleRepository;
import com.fastcode.testdocbuild11.domain.core.authorization.role.RoleEntity;
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

@Component("userroleManager")
@RequiredArgsConstructor
public class UserroleManager implements IUserroleManager {

    @Qualifier("userroleRepository")
    @NonNull
    protected final IUserroleRepository _userroleRepository;

    @Qualifier("roleRepository")
    @NonNull
    protected final IRoleRepository _roleRepository;

    @Qualifier("userRepository")
    @NonNull
    protected final IUserRepository _userRepository;

    public UserroleEntity create(UserroleEntity userrole) {
        return _userroleRepository.save(userrole);
    }

    public void delete(UserroleEntity userrole) {
        _userroleRepository.delete(userrole);
    }

    public UserroleEntity update(UserroleEntity userrole) {
        return _userroleRepository.save(userrole);
    }

    public UserroleEntity findById(UserroleId userroleId) {
        Optional<UserroleEntity> dbUserrole = _userroleRepository.findById(userroleId);
        return dbUserrole.orElse(null);
    }

    public Page<UserroleEntity> findAll(Predicate predicate, Pageable pageable) {
        return _userroleRepository.findAll(predicate, pageable);
    }

    public RoleEntity getRole(UserroleId userroleId) {
        Optional<UserroleEntity> dbUserrole = _userroleRepository.findById(userroleId);
        if (dbUserrole.isPresent()) {
            UserroleEntity existingUserrole = dbUserrole.get();
            return existingUserrole.getRole();
        } else {
            return null;
        }
    }

    public UserEntity getUser(UserroleId userroleId) {
        Optional<UserroleEntity> dbUserrole = _userroleRepository.findById(userroleId);
        if (dbUserrole.isPresent()) {
            UserroleEntity existingUserrole = dbUserrole.get();
            return existingUserrole.getUser();
        } else {
            return null;
        }
    }

    public List<UserroleEntity> getUserrolesByUserId(Long id) {
        return _userroleRepository.findByUserId(id);
    }

    public List<UserroleEntity> getUserrolesByRoleId(Long roleId) {
        return _userroleRepository.findByRoleId(roleId);
    }
}
