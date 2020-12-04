package com.fastcode.testdocbuild11.restcontrollers.extended;

import com.fastcode.testdocbuild11.application.extended.address.IAddressAppServiceExtended;
import com.fastcode.testdocbuild11.application.extended.payment.IPaymentAppServiceExtended;
import com.fastcode.testdocbuild11.application.extended.rental.IRentalAppServiceExtended;
import com.fastcode.testdocbuild11.application.extended.staff.IStaffAppServiceExtended;
import com.fastcode.testdocbuild11.application.extended.store.IStoreAppServiceExtended;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.restcontrollers.core.StaffController;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/staff/extended")
public class StaffControllerExtended extends StaffController {

    public StaffControllerExtended(
        IStaffAppServiceExtended staffAppServiceExtended,
        IAddressAppServiceExtended addressAppServiceExtended,
        IPaymentAppServiceExtended paymentAppServiceExtended,
        IRentalAppServiceExtended rentalAppServiceExtended,
        IStoreAppServiceExtended storeAppServiceExtended,
        LoggingHelper helper,
        Environment env
    ) {
        super(
            staffAppServiceExtended,
            addressAppServiceExtended,
            paymentAppServiceExtended,
            rentalAppServiceExtended,
            storeAppServiceExtended,
            helper,
            env
        );
    }
    //Add your custom code here

}
