package com.fastcode.testdocbuild11.domain.extended.city;

import com.fastcode.testdocbuild11.domain.core.city.ICityRepository;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("cityRepositoryExtended")
public interface ICityRepositoryExtended extends ICityRepository {
    //Add your custom code here
}
