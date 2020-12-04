package com.fastcode.testdocbuild11.domain.extended.filmactor;

import com.fastcode.testdocbuild11.domain.core.filmactor.IFilmActorRepository;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("filmActorRepositoryExtended")
public interface IFilmActorRepositoryExtended extends IFilmActorRepository {
    //Add your custom code here
}
