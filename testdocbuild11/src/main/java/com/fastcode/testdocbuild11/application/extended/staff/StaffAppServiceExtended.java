package com.fastcode.testdocbuild11.application.extended.staff;

import com.fastcode.testdocbuild11.application.core.staff.StaffAppService;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.domain.extended.address.IAddressManagerExtended;
import com.fastcode.testdocbuild11.domain.extended.staff.IStaffManagerExtended;
import com.fastcode.testdocbuild11.domain.extended.store.IStoreManagerExtended;
import org.springframework.stereotype.Service;

@Service("staffAppServiceExtended")
public class StaffAppServiceExtended extends StaffAppService implements IStaffAppServiceExtended {

    public StaffAppServiceExtended(
        IStaffManagerExtended staffManagerExtended,
        IAddressManagerExtended addressManagerExtended,
        IStoreManagerExtended storeManagerExtended,
        IStaffMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(staffManagerExtended, addressManagerExtended, storeManagerExtended, mapper, logHelper);
    }
    //Add your custom code here

}
