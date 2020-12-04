package com.fastcode.testdocbuild11.restcontrollers.extended;

import com.fastcode.testdocbuild11.application.extended.customer.ICustomerAppServiceExtended;
import com.fastcode.testdocbuild11.application.extended.payment.IPaymentAppServiceExtended;
import com.fastcode.testdocbuild11.application.extended.rental.IRentalAppServiceExtended;
import com.fastcode.testdocbuild11.application.extended.staff.IStaffAppServiceExtended;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.restcontrollers.core.PaymentController;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment/extended")
public class PaymentControllerExtended extends PaymentController {

    public PaymentControllerExtended(
        IPaymentAppServiceExtended paymentAppServiceExtended,
        ICustomerAppServiceExtended customerAppServiceExtended,
        IRentalAppServiceExtended rentalAppServiceExtended,
        IStaffAppServiceExtended staffAppServiceExtended,
        LoggingHelper helper,
        Environment env
    ) {
        super(
            paymentAppServiceExtended,
            customerAppServiceExtended,
            rentalAppServiceExtended,
            staffAppServiceExtended,
            helper,
            env
        );
    }
    //Add your custom code here

}
