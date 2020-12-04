package com.fastcode.testdocbuild11.restcontrollers.extended;

import com.fastcode.testdocbuild11.application.extended.address.IAddressAppServiceExtended;
import com.fastcode.testdocbuild11.application.extended.city.ICityAppServiceExtended;
import com.fastcode.testdocbuild11.application.extended.customer.ICustomerAppServiceExtended;
import com.fastcode.testdocbuild11.application.extended.staff.IStaffAppServiceExtended;
import com.fastcode.testdocbuild11.application.extended.store.IStoreAppServiceExtended;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.restcontrollers.core.AddressController;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address/extended")
public class AddressControllerExtended extends AddressController {

    public AddressControllerExtended(
        IAddressAppServiceExtended addressAppServiceExtended,
        ICityAppServiceExtended cityAppServiceExtended,
        ICustomerAppServiceExtended customerAppServiceExtended,
        IStaffAppServiceExtended staffAppServiceExtended,
        IStoreAppServiceExtended storeAppServiceExtended,
        LoggingHelper helper,
        Environment env
    ) {
        super(
            addressAppServiceExtended,
            cityAppServiceExtended,
            customerAppServiceExtended,
            staffAppServiceExtended,
            storeAppServiceExtended,
            helper,
            env
        );
    }
    //Add your custom code here

}
