package com.fastcode.testdocbuild11.domain.core.film;

import com.fastcode.testdocbuild11.domain.core.filmactor.IFilmActorRepository;
import com.fastcode.testdocbuild11.domain.core.filmcategory.IFilmCategoryRepository;
import com.fastcode.testdocbuild11.domain.core.inventory.IInventoryRepository;
import com.fastcode.testdocbuild11.domain.core.language.ILanguageRepository;
import com.fastcode.testdocbuild11.domain.core.language.LanguageEntity;
import com.querydsl.core.types.Predicate;
import java.time.*;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component("filmManager")
@RequiredArgsConstructor
public class FilmManager implements IFilmManager {

    @Qualifier("filmRepository")
    @NonNull
    protected final IFilmRepository _filmRepository;

    @Qualifier("filmActorRepository")
    @NonNull
    protected final IFilmActorRepository _filmactorRepository;

    @Qualifier("filmCategoryRepository")
    @NonNull
    protected final IFilmCategoryRepository _filmcategoryRepository;

    @Qualifier("inventoryRepository")
    @NonNull
    protected final IInventoryRepository _inventoryRepository;

    @Qualifier("languageRepository")
    @NonNull
    protected final ILanguageRepository _languageRepository;

    public FilmEntity create(FilmEntity film) {
        return _filmRepository.save(film);
    }

    public void delete(FilmEntity film) {
        _filmRepository.delete(film);
    }

    public FilmEntity update(FilmEntity film) {
        return _filmRepository.save(film);
    }

    public FilmEntity findById(Integer filmId) {
        Optional<FilmEntity> dbFilm = _filmRepository.findById(filmId);
        return dbFilm.orElse(null);
    }

    public Page<FilmEntity> findAll(Predicate predicate, Pageable pageable) {
        return _filmRepository.findAll(predicate, pageable);
    }

    public LanguageEntity getLanguage(Integer filmId) {
        Optional<FilmEntity> dbFilm = _filmRepository.findById(filmId);
        if (dbFilm.isPresent()) {
            FilmEntity existingFilm = dbFilm.get();
            return existingFilm.getLanguage();
        } else {
            return null;
        }
    }
}
