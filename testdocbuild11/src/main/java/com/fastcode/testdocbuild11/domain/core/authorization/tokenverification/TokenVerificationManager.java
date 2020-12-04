package com.fastcode.testdocbuild11.domain.core.authorization.tokenverification;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenVerificationManager implements ITokenVerificationManager {

    @NonNull
    private final ITokenverificationRepository _tokenRepository;

    public TokenverificationEntity findByTokenAndType(String token, String tokenType) {
        return _tokenRepository.findByTokenAndTokenType(token, tokenType);
    }

    public TokenverificationEntity save(TokenverificationEntity entity) {
        return _tokenRepository.save(entity);
    }

    public TokenverificationEntity findByUserIdAndType(Long userId, String tokenType) {
        return _tokenRepository.findByUserIdAndTokenType(userId, tokenType);
    }

    public void delete(TokenverificationEntity entity) {
        _tokenRepository.delete(entity);
    }
}
