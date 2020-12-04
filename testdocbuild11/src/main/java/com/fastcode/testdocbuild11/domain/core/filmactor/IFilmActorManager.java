package com.fastcode.testdocbuild11.domain.core.filmactor;

import com.fastcode.testdocbuild11.domain.core.actor.ActorEntity;
import com.fastcode.testdocbuild11.domain.core.film.FilmEntity;
import com.querydsl.core.types.Predicate;
import java.time.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFilmActorManager {
    FilmActorEntity create(FilmActorEntity filmActor);

    void delete(FilmActorEntity filmActor);

    FilmActorEntity update(FilmActorEntity filmActor);

    FilmActorEntity findById(FilmActorId filmActorId);

    Page<FilmActorEntity> findAll(Predicate predicate, Pageable pageable);

    ActorEntity getActor(FilmActorId filmActorId);

    FilmEntity getFilm(FilmActorId filmActorId);
}
