package com.fastcode.testdocbuild11.domain.extended.filmcategory;

import com.fastcode.testdocbuild11.domain.core.filmcategory.IFilmCategoryRepository;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("filmCategoryRepositoryExtended")
public interface IFilmCategoryRepositoryExtended extends IFilmCategoryRepository {
    //Add your custom code here
}
