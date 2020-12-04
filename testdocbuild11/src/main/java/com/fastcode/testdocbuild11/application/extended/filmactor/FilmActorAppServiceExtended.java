package com.fastcode.testdocbuild11.application.extended.filmactor;

import com.fastcode.testdocbuild11.application.core.filmactor.FilmActorAppService;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.domain.extended.actor.IActorManagerExtended;
import com.fastcode.testdocbuild11.domain.extended.film.IFilmManagerExtended;
import com.fastcode.testdocbuild11.domain.extended.filmactor.IFilmActorManagerExtended;
import org.springframework.stereotype.Service;

@Service("filmActorAppServiceExtended")
public class FilmActorAppServiceExtended extends FilmActorAppService implements IFilmActorAppServiceExtended {

    public FilmActorAppServiceExtended(
        IFilmActorManagerExtended filmActorManagerExtended,
        IActorManagerExtended actorManagerExtended,
        IFilmManagerExtended filmManagerExtended,
        IFilmActorMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(filmActorManagerExtended, actorManagerExtended, filmManagerExtended, mapper, logHelper);
    }
    //Add your custom code here

}
