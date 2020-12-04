package com.fastcode.testdocbuild11.domain.core.actor;

import com.querydsl.core.types.Predicate;
import java.time.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IActorManager {
    ActorEntity create(ActorEntity actor);

    void delete(ActorEntity actor);

    ActorEntity update(ActorEntity actor);

    ActorEntity findById(Integer id);

    Page<ActorEntity> findAll(Predicate predicate, Pageable pageable);
}
