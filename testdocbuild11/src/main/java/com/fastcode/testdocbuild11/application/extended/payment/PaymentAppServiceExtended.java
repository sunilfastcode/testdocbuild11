package com.fastcode.testdocbuild11.application.extended.payment;

import com.fastcode.testdocbuild11.application.core.payment.PaymentAppService;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.domain.extended.customer.ICustomerManagerExtended;
import com.fastcode.testdocbuild11.domain.extended.payment.IPaymentManagerExtended;
import com.fastcode.testdocbuild11.domain.extended.rental.IRentalManagerExtended;
import com.fastcode.testdocbuild11.domain.extended.staff.IStaffManagerExtended;
import org.springframework.stereotype.Service;

@Service("paymentAppServiceExtended")
public class PaymentAppServiceExtended extends PaymentAppService implements IPaymentAppServiceExtended {

    public PaymentAppServiceExtended(
        IPaymentManagerExtended paymentManagerExtended,
        ICustomerManagerExtended customerManagerExtended,
        IRentalManagerExtended rentalManagerExtended,
        IStaffManagerExtended staffManagerExtended,
        IPaymentMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(
            paymentManagerExtended,
            customerManagerExtended,
            rentalManagerExtended,
            staffManagerExtended,
            mapper,
            logHelper
        );
    }
    //Add your custom code here

}
