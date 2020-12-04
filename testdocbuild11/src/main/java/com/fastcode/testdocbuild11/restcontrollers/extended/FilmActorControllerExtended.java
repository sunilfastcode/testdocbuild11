package com.fastcode.testdocbuild11.restcontrollers.extended;

import com.fastcode.testdocbuild11.application.extended.actor.IActorAppServiceExtended;
import com.fastcode.testdocbuild11.application.extended.film.IFilmAppServiceExtended;
import com.fastcode.testdocbuild11.application.extended.filmactor.IFilmActorAppServiceExtended;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.restcontrollers.core.FilmActorController;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/filmActor/extended")
public class FilmActorControllerExtended extends FilmActorController {

    public FilmActorControllerExtended(
        IFilmActorAppServiceExtended filmActorAppServiceExtended,
        IActorAppServiceExtended actorAppServiceExtended,
        IFilmAppServiceExtended filmAppServiceExtended,
        LoggingHelper helper,
        Environment env
    ) {
        super(filmActorAppServiceExtended, actorAppServiceExtended, filmAppServiceExtended, helper, env);
    }
    //Add your custom code here

}
