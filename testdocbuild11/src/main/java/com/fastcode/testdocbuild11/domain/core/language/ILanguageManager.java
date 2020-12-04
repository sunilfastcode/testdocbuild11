package com.fastcode.testdocbuild11.domain.core.language;

import com.querydsl.core.types.Predicate;
import java.time.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ILanguageManager {
    LanguageEntity create(LanguageEntity language);

    void delete(LanguageEntity language);

    LanguageEntity update(LanguageEntity language);

    LanguageEntity findById(Integer id);

    Page<LanguageEntity> findAll(Predicate predicate, Pageable pageable);
}
