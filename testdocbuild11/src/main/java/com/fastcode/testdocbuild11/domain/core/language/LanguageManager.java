package com.fastcode.testdocbuild11.domain.core.language;

import com.fastcode.testdocbuild11.domain.core.film.IFilmRepository;
import com.querydsl.core.types.Predicate;
import java.time.*;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component("languageManager")
@RequiredArgsConstructor
public class LanguageManager implements ILanguageManager {

    @Qualifier("languageRepository")
    @NonNull
    protected final ILanguageRepository _languageRepository;

    @Qualifier("filmRepository")
    @NonNull
    protected final IFilmRepository _filmRepository;

    public LanguageEntity create(LanguageEntity language) {
        return _languageRepository.save(language);
    }

    public void delete(LanguageEntity language) {
        _languageRepository.delete(language);
    }

    public LanguageEntity update(LanguageEntity language) {
        return _languageRepository.save(language);
    }

    public LanguageEntity findById(Integer languageId) {
        Optional<LanguageEntity> dbLanguage = _languageRepository.findById(languageId);
        return dbLanguage.orElse(null);
    }

    public Page<LanguageEntity> findAll(Predicate predicate, Pageable pageable) {
        return _languageRepository.findAll(predicate, pageable);
    }
}
