package com.fastcode.testdocbuild11.application.extended.store;

import com.fastcode.testdocbuild11.application.core.store.StoreAppService;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.domain.extended.address.IAddressManagerExtended;
import com.fastcode.testdocbuild11.domain.extended.staff.IStaffManagerExtended;
import com.fastcode.testdocbuild11.domain.extended.store.IStoreManagerExtended;
import org.springframework.stereotype.Service;

@Service("storeAppServiceExtended")
public class StoreAppServiceExtended extends StoreAppService implements IStoreAppServiceExtended {

    public StoreAppServiceExtended(
        IStoreManagerExtended storeManagerExtended,
        IAddressManagerExtended addressManagerExtended,
        IStaffManagerExtended staffManagerExtended,
        IStoreMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(storeManagerExtended, addressManagerExtended, staffManagerExtended, mapper, logHelper);
    }
    //Add your custom code here

}
