package com.fastcode.testdocbuild11.application.extended.filmcategory;

import com.fastcode.testdocbuild11.application.core.filmcategory.FilmCategoryAppService;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.domain.extended.category.ICategoryManagerExtended;
import com.fastcode.testdocbuild11.domain.extended.film.IFilmManagerExtended;
import com.fastcode.testdocbuild11.domain.extended.filmcategory.IFilmCategoryManagerExtended;
import org.springframework.stereotype.Service;

@Service("filmCategoryAppServiceExtended")
public class FilmCategoryAppServiceExtended extends FilmCategoryAppService implements IFilmCategoryAppServiceExtended {

    public FilmCategoryAppServiceExtended(
        IFilmCategoryManagerExtended filmCategoryManagerExtended,
        ICategoryManagerExtended categoryManagerExtended,
        IFilmManagerExtended filmManagerExtended,
        IFilmCategoryMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(filmCategoryManagerExtended, categoryManagerExtended, filmManagerExtended, mapper, logHelper);
    }
    //Add your custom code here

}
