package com.fastcode.testdocbuild11.domain.core.filmactor;

import com.fastcode.testdocbuild11.domain.core.actor.ActorEntity;
import com.fastcode.testdocbuild11.domain.core.actor.IActorRepository;
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

@Component("filmActorManager")
@RequiredArgsConstructor
public class FilmActorManager implements IFilmActorManager {

    @Qualifier("filmActorRepository")
    @NonNull
    protected final IFilmActorRepository _filmActorRepository;

    @Qualifier("actorRepository")
    @NonNull
    protected final IActorRepository _actorRepository;

    @Qualifier("filmRepository")
    @NonNull
    protected final IFilmRepository _filmRepository;

    public FilmActorEntity create(FilmActorEntity filmActor) {
        return _filmActorRepository.save(filmActor);
    }

    public void delete(FilmActorEntity filmActor) {
        _filmActorRepository.delete(filmActor);
    }

    public FilmActorEntity update(FilmActorEntity filmActor) {
        return _filmActorRepository.save(filmActor);
    }

    public FilmActorEntity findById(FilmActorId filmActorId) {
        Optional<FilmActorEntity> dbFilmActor = _filmActorRepository.findById(filmActorId);
        return dbFilmActor.orElse(null);
    }

    public Page<FilmActorEntity> findAll(Predicate predicate, Pageable pageable) {
        return _filmActorRepository.findAll(predicate, pageable);
    }

    public ActorEntity getActor(FilmActorId filmActorId) {
        Optional<FilmActorEntity> dbFilmActor = _filmActorRepository.findById(filmActorId);
        if (dbFilmActor.isPresent()) {
            FilmActorEntity existingFilmActor = dbFilmActor.get();
            return existingFilmActor.getActor();
        } else {
            return null;
        }
    }

    public FilmEntity getFilm(FilmActorId filmActorId) {
        Optional<FilmActorEntity> dbFilmActor = _filmActorRepository.findById(filmActorId);
        if (dbFilmActor.isPresent()) {
            FilmActorEntity existingFilmActor = dbFilmActor.get();
            return existingFilmActor.getFilm();
        } else {
            return null;
        }
    }
}
