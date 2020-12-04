package com.fastcode.testdocbuild11.domain.core.authorization.userpreference;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserpreferenceRepository
    extends JpaRepository<UserpreferenceEntity, Long>, QuerydslPredicateExecutor<UserpreferenceEntity> {}
