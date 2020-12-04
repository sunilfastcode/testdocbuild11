package com.fastcode.testdocbuild11.domain.core.authorization.user;

import com.querydsl.core.types.Predicate;
import java.time.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserManager {
    UserEntity create(UserEntity user);

    void delete(UserEntity user);

    UserEntity update(UserEntity user);

    UserEntity findById(Long id);
    UserEntity findByUserName(String userName);

    UserEntity findByEmailAddress(String emailAddress);

    Page<UserEntity> findAll(Predicate predicate, Pageable pageable);
}
