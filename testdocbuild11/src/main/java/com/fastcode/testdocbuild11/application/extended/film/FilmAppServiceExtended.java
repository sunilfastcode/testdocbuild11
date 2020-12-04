package com.fastcode.testdocbuild11.application.extended.film;

import com.fastcode.testdocbuild11.application.core.film.FilmAppService;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.domain.extended.film.IFilmManagerExtended;
import com.fastcode.testdocbuild11.domain.extended.language.ILanguageManagerExtended;
import org.springframework.stereotype.Service;

@Service("filmAppServiceExtended")
public class FilmAppServiceExtended extends FilmAppService implements IFilmAppServiceExtended {

    public FilmAppServiceExtended(
        IFilmManagerExtended filmManagerExtended,
        ILanguageManagerExtended languageManagerExtended,
        IFilmMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(filmManagerExtended, languageManagerExtended, mapper, logHelper);
    }
    //Add your custom code here

}
