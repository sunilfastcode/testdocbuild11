package com.fastcode.testdocbuild11.domain.extended.filmcategory;

import com.fastcode.testdocbuild11.domain.core.filmcategory.FilmCategoryManager;
import com.fastcode.testdocbuild11.domain.extended.category.ICategoryRepositoryExtended;
import com.fastcode.testdocbuild11.domain.extended.film.IFilmRepositoryExtended;
import org.springframework.stereotype.Component;

@Component("filmCategoryManagerExtended")
public class FilmCategoryManagerExtended extends FilmCategoryManager implements IFilmCategoryManagerExtended {

    public FilmCategoryManagerExtended(
        IFilmCategoryRepositoryExtended filmCategoryRepositoryExtended,
        ICategoryRepositoryExtended categoryRepositoryExtended,
        IFilmRepositoryExtended filmRepositoryExtended
    ) {
        super(filmCategoryRepositoryExtended, categoryRepositoryExtended, filmRepositoryExtended);
    }
    //Add your custom code here
}
