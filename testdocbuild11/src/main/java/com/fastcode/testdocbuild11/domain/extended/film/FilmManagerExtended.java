package com.fastcode.testdocbuild11.domain.extended.film;

import com.fastcode.testdocbuild11.domain.core.film.FilmManager;
import com.fastcode.testdocbuild11.domain.extended.filmactor.IFilmActorRepositoryExtended;
import com.fastcode.testdocbuild11.domain.extended.filmcategory.IFilmCategoryRepositoryExtended;
import com.fastcode.testdocbuild11.domain.extended.inventory.IInventoryRepositoryExtended;
import com.fastcode.testdocbuild11.domain.extended.language.ILanguageRepositoryExtended;
import org.springframework.stereotype.Component;

@Component("filmManagerExtended")
public class FilmManagerExtended extends FilmManager implements IFilmManagerExtended {

    public FilmManagerExtended(
        IFilmRepositoryExtended filmRepositoryExtended,
        IFilmActorRepositoryExtended filmActorRepositoryExtended,
        IFilmCategoryRepositoryExtended filmCategoryRepositoryExtended,
        IInventoryRepositoryExtended inventoryRepositoryExtended,
        ILanguageRepositoryExtended languageRepositoryExtended
    ) {
        super(
            filmRepositoryExtended,
            filmActorRepositoryExtended,
            filmCategoryRepositoryExtended,
            inventoryRepositoryExtended,
            languageRepositoryExtended
        );
    }
    //Add your custom code here
}
