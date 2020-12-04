package com.fastcode.testdocbuild11.domain.core.film;

import com.fastcode.testdocbuild11.domain.core.language.LanguageEntity;
import com.querydsl.core.types.Predicate;
import java.time.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFilmManager {
    FilmEntity create(FilmEntity film);

    void delete(FilmEntity film);

    FilmEntity update(FilmEntity film);

    FilmEntity findById(Integer id);

    Page<FilmEntity> findAll(Predicate predicate, Pageable pageable);

    LanguageEntity getLanguage(Integer filmId);
}
