package com.fastcode.testdocbuild11.domain.extended.store;

import com.fastcode.testdocbuild11.domain.core.store.IStoreRepository;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("storeRepositoryExtended")
public interface IStoreRepositoryExtended extends IStoreRepository {
    //Add your custom code here
}
