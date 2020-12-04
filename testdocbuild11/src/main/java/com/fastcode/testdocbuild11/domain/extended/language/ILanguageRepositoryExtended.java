package com.fastcode.testdocbuild11.domain.extended.language;

import com.fastcode.testdocbuild11.domain.core.language.ILanguageRepository;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("languageRepositoryExtended")
public interface ILanguageRepositoryExtended extends ILanguageRepository {
    //Add your custom code here
}
