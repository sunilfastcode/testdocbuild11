package com.fastcode.testdocbuild11.domain.extended.filmactor;

import com.fastcode.testdocbuild11.domain.core.filmactor.FilmActorManager;
import com.fastcode.testdocbuild11.domain.extended.actor.IActorRepositoryExtended;
import com.fastcode.testdocbuild11.domain.extended.film.IFilmRepositoryExtended;
import org.springframework.stereotype.Component;

@Component("filmActorManagerExtended")
public class FilmActorManagerExtended extends FilmActorManager implements IFilmActorManagerExtended {

    public FilmActorManagerExtended(
        IFilmActorRepositoryExtended filmActorRepositoryExtended,
        IActorRepositoryExtended actorRepositoryExtended,
        IFilmRepositoryExtended filmRepositoryExtended
    ) {
        super(filmActorRepositoryExtended, actorRepositoryExtended, filmRepositoryExtended);
    }
    //Add your custom code here
}
