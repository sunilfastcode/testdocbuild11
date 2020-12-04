package com.fastcode.testdocbuild11.domain.extended.actor;

import com.fastcode.testdocbuild11.domain.core.actor.ActorManager;
import com.fastcode.testdocbuild11.domain.extended.filmactor.IFilmActorRepositoryExtended;
import org.springframework.stereotype.Component;

@Component("actorManagerExtended")
public class ActorManagerExtended extends ActorManager implements IActorManagerExtended {

    public ActorManagerExtended(
        IActorRepositoryExtended actorRepositoryExtended,
        IFilmActorRepositoryExtended filmActorRepositoryExtended
    ) {
        super(actorRepositoryExtended, filmActorRepositoryExtended);
    }
    //Add your custom code here
}
