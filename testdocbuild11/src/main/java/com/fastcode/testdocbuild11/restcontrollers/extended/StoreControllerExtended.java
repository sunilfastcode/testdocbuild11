package com.fastcode.testdocbuild11.restcontrollers.extended;

import com.fastcode.testdocbuild11.application.extended.address.IAddressAppServiceExtended;
import com.fastcode.testdocbuild11.application.extended.staff.IStaffAppServiceExtended;
import com.fastcode.testdocbuild11.application.extended.store.IStoreAppServiceExtended;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.restcontrollers.core.StoreController;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/store/extended")
public class StoreControllerExtended extends StoreController {

    public StoreControllerExtended(
        IStoreAppServiceExtended storeAppServiceExtended,
        IAddressAppServiceExtended addressAppServiceExtended,
        IStaffAppServiceExtended staffAppServiceExtended,
        LoggingHelper helper,
        Environment env
    ) {
        super(storeAppServiceExtended, addressAppServiceExtended, staffAppServiceExtended, helper, env);
    }
    //Add your custom code here

}
