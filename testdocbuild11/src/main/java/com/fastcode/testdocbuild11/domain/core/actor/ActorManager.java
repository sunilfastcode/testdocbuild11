package com.fastcode.testdocbuild11.domain.core.actor;

import com.fastcode.testdocbuild11.domain.core.filmactor.IFilmActorRepository;
import com.querydsl.core.types.Predicate;
import java.time.*;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component("actorManager")
@RequiredArgsConstructor
public class ActorManager implements IActorManager {

    @Qualifier("actorRepository")
    @NonNull
    protected final IActorRepository _actorRepository;

    @Qualifier("filmActorRepository")
    @NonNull
    protected final IFilmActorRepository _filmactorRepository;

    public ActorEntity create(ActorEntity actor) {
        return _actorRepository.save(actor);
    }

    public void delete(ActorEntity actor) {
        _actorRepository.delete(actor);
    }

    public ActorEntity update(ActorEntity actor) {
        return _actorRepository.save(actor);
    }

    public ActorEntity findById(Integer actorId) {
        Optional<ActorEntity> dbActor = _actorRepository.findById(actorId);
        return dbActor.orElse(null);
    }

    public Page<ActorEntity> findAll(Predicate predicate, Pageable pageable) {
        return _actorRepository.findAll(predicate, pageable);
    }
}
