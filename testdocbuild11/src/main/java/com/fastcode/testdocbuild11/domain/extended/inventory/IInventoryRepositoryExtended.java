package com.fastcode.testdocbuild11.domain.extended.inventory;

import com.fastcode.testdocbuild11.domain.core.inventory.IInventoryRepository;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("inventoryRepositoryExtended")
public interface IInventoryRepositoryExtended extends IInventoryRepository {
    //Add your custom code here
}
