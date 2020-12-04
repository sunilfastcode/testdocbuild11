package com.fastcode.testdocbuild11.application.core.actor;

import com.fastcode.testdocbuild11.application.core.actor.dto.*;
import com.fastcode.testdocbuild11.domain.core.actor.ActorEntity;
import java.time.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IActorMapper {
    ActorEntity createActorInputToActorEntity(CreateActorInput actorDto);

    CreateActorOutput actorEntityToCreateActorOutput(ActorEntity entity);

    ActorEntity updateActorInputToActorEntity(UpdateActorInput actorDto);

    UpdateActorOutput actorEntityToUpdateActorOutput(ActorEntity entity);

    FindActorByIdOutput actorEntityToFindActorByIdOutput(ActorEntity entity);
}
