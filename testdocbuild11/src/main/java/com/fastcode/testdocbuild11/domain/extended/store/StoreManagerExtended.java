package com.fastcode.testdocbuild11.domain.extended.store;

import com.fastcode.testdocbuild11.domain.core.store.StoreManager;
import com.fastcode.testdocbuild11.domain.extended.address.IAddressRepositoryExtended;
import com.fastcode.testdocbuild11.domain.extended.staff.IStaffRepositoryExtended;
import org.springframework.stereotype.Component;

@Component("storeManagerExtended")
public class StoreManagerExtended extends StoreManager implements IStoreManagerExtended {

    public StoreManagerExtended(
        IStoreRepositoryExtended storeRepositoryExtended,
        IAddressRepositoryExtended addressRepositoryExtended,
        IStaffRepositoryExtended staffRepositoryExtended
    ) {
        super(storeRepositoryExtended, addressRepositoryExtended, staffRepositoryExtended);
    }
    //Add your custom code here
}
