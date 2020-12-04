package com.fastcode.testdocbuild11.domain.extended.language;

import com.fastcode.testdocbuild11.domain.core.language.LanguageManager;
import com.fastcode.testdocbuild11.domain.extended.film.IFilmRepositoryExtended;
import org.springframework.stereotype.Component;

@Component("languageManagerExtended")
public class LanguageManagerExtended extends LanguageManager implements ILanguageManagerExtended {

    public LanguageManagerExtended(
        ILanguageRepositoryExtended languageRepositoryExtended,
        IFilmRepositoryExtended filmRepositoryExtended
    ) {
        super(languageRepositoryExtended, filmRepositoryExtended);
    }
    //Add your custom code here
}
