package com.fastcode.testdocbuild11.domain.core.authorization.tokenverification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ITokenverificationRepository
    extends
        JpaRepository<TokenverificationEntity, TokenverificationId>,
        QuerydslPredicateExecutor<TokenverificationEntity> {
    TokenverificationEntity findByTokenAndTokenType(String token, String tokenType);

    TokenverificationEntity findByUserIdAndTokenType(Long userId, String tokenType);
}
