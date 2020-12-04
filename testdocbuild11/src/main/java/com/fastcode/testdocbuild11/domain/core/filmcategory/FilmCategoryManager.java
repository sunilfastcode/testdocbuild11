package com.fastcode.testdocbuild11.domain.core.filmcategory;

import com.fastcode.testdocbuild11.domain.core.category.CategoryEntity;
import com.fastcode.testdocbuild11.domain.core.category.ICategoryRepository;
import com.fastcode.testdocbuild11.domain.core.film.FilmEntity;
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

@Component("filmCategoryManager")
@RequiredArgsConstructor
public class FilmCategoryManager implements IFilmCategoryManager {

    @Qualifier("filmCategoryRepository")
    @NonNull
    protected final IFilmCategoryRepository _filmCategoryRepository;

    @Qualifier("categoryRepository")
    @NonNull
    protected final ICategoryRepository _categoryRepository;

    @Qualifier("filmRepository")
    @NonNull
    protected final IFilmRepository _filmRepository;

    public FilmCategoryEntity create(FilmCategoryEntity filmCategory) {
        return _filmCategoryRepository.save(filmCategory);
    }

    public void delete(FilmCategoryEntity filmCategory) {
        _filmCategoryRepository.delete(filmCategory);
    }

    public FilmCategoryEntity update(FilmCategoryEntity filmCategory) {
        return _filmCategoryRepository.save(filmCategory);
    }

    public FilmCategoryEntity findById(FilmCategoryId filmCategoryId) {
        Optional<FilmCategoryEntity> dbFilmCategory = _filmCategoryRepository.findById(filmCategoryId);
        return dbFilmCategory.orElse(null);
    }

    public Page<FilmCategoryEntity> findAll(Predicate predicate, Pageable pageable) {
        return _filmCategoryRepository.findAll(predicate, pageable);
    }

    public CategoryEntity getCategory(FilmCategoryId filmCategoryId) {
        Optional<FilmCategoryEntity> dbFilmCategory = _filmCategoryRepository.findById(filmCategoryId);
        if (dbFilmCategory.isPresent()) {
            FilmCategoryEntity existingFilmCategory = dbFilmCategory.get();
            return existingFilmCategory.getCategory();
        } else {
            return null;
        }
    }

    public FilmEntity getFilm(FilmCategoryId filmCategoryId) {
        Optional<FilmCategoryEntity> dbFilmCategory = _filmCategoryRepository.findById(filmCategoryId);
        if (dbFilmCategory.isPresent()) {
            FilmCategoryEntity existingFilmCategory = dbFilmCategory.get();
            return existingFilmCategory.getFilm();
        } else {
            return null;
        }
    }
}
