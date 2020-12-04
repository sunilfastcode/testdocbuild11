package com.fastcode.testdocbuild11.domain.core.authorization.user;

import com.fastcode.testdocbuild11.domain.core.authorization.userpermission.IUserpermissionRepository;
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

@Component("userManager")
@RequiredArgsConstructor
public class UserManager implements IUserManager {

    @Qualifier("userRepository")
    @NonNull
    protected final IUserRepository _userRepository;

    @Qualifier("userpermissionRepository")
    @NonNull
    protected final IUserpermissionRepository _userpermissionRepository;

    @Qualifier("userroleRepository")
    @NonNull
    protected final IUserroleRepository _userroleRepository;

    public UserEntity create(UserEntity user) {
        return _userRepository.save(user);
    }

    public void delete(UserEntity user) {
        _userRepository.delete(user);
    }

    public UserEntity update(UserEntity user) {
        return _userRepository.save(user);
    }

    public UserEntity findById(Long userId) {
        Optional<UserEntity> dbUser = _userRepository.findById(userId);
        return dbUser.orElse(null);
    }

    public UserEntity findByUserName(String userName) {
        return _userRepository.findByUserName(userName);
    }

    public UserEntity findByEmailAddress(String emailAddress) {
        return _userRepository.findByEmailAddress(emailAddress);
    }

    public Page<UserEntity> findAll(Predicate predicate, Pageable pageable) {
        return _userRepository.findAll(predicate, pageable);
    }
}
