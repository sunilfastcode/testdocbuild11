package com.fastcode.testdocbuild11.application.extended.language;

import com.fastcode.testdocbuild11.application.core.language.LanguageAppService;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.domain.extended.language.ILanguageManagerExtended;
import org.springframework.stereotype.Service;

@Service("languageAppServiceExtended")
public class LanguageAppServiceExtended extends LanguageAppService implements ILanguageAppServiceExtended {

    public LanguageAppServiceExtended(
        ILanguageManagerExtended languageManagerExtended,
        ILanguageMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(languageManagerExtended, mapper, logHelper);
    }
    //Add your custom code here

}
