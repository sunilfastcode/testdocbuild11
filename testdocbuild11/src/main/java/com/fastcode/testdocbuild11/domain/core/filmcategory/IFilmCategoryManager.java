package com.fastcode.testdocbuild11.domain.core.filmcategory;

import com.fastcode.testdocbuild11.domain.core.category.CategoryEntity;
import com.fastcode.testdocbuild11.domain.core.film.FilmEntity;
import com.querydsl.core.types.Predicate;
import java.time.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFilmCategoryManager {
    FilmCategoryEntity create(FilmCategoryEntity filmCategory);

    void delete(FilmCategoryEntity filmCategory);

    FilmCategoryEntity update(FilmCategoryEntity filmCategory);

    FilmCategoryEntity findById(FilmCategoryId filmCategoryId);

    Page<FilmCategoryEntity> findAll(Predicate predicate, Pageable pageable);

    CategoryEntity getCategory(FilmCategoryId filmCategoryId);

    FilmEntity getFilm(FilmCategoryId filmCategoryId);
}
