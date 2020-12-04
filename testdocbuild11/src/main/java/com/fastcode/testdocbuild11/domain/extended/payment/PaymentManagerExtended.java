package com.fastcode.testdocbuild11.domain.extended.payment;

import com.fastcode.testdocbuild11.domain.core.payment.PaymentManager;
import com.fastcode.testdocbuild11.domain.extended.customer.ICustomerRepositoryExtended;
import com.fastcode.testdocbuild11.domain.extended.rental.IRentalRepositoryExtended;
import com.fastcode.testdocbuild11.domain.extended.staff.IStaffRepositoryExtended;
import org.springframework.stereotype.Component;

@Component("paymentManagerExtended")
public class PaymentManagerExtended extends PaymentManager implements IPaymentManagerExtended {

    public PaymentManagerExtended(
        IPaymentRepositoryExtended paymentRepositoryExtended,
        ICustomerRepositoryExtended customerRepositoryExtended,
        IRentalRepositoryExtended rentalRepositoryExtended,
        IStaffRepositoryExtended staffRepositoryExtended
    ) {
        super(paymentRepositoryExtended, customerRepositoryExtended, rentalRepositoryExtended, staffRepositoryExtended);
    }
    //Add your custom code here
}
