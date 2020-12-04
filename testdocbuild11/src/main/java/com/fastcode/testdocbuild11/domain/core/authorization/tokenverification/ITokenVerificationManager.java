package com.fastcode.testdocbuild11.domain.core.authorization.tokenverification;

public interface ITokenVerificationManager {
    TokenverificationEntity save(TokenverificationEntity entity);

    void delete(TokenverificationEntity entity);

    TokenverificationEntity findByTokenAndType(String token, String tokenType);

    TokenverificationEntity findByUserIdAndType(Long userId, String tokenType);
}
