package com.fastcode.testdocbuild11.domain.extended.customer;

import com.fastcode.testdocbuild11.domain.core.customer.CustomerManager;
import com.fastcode.testdocbuild11.domain.extended.address.IAddressRepositoryExtended;
import com.fastcode.testdocbuild11.domain.extended.payment.IPaymentRepositoryExtended;
import com.fastcode.testdocbuild11.domain.extended.rental.IRentalRepositoryExtended;
import org.springframework.stereotype.Component;

@Component("customerManagerExtended")
public class CustomerManagerExtended extends CustomerManager implements ICustomerManagerExtended {

    public CustomerManagerExtended(
        ICustomerRepositoryExtended customerRepositoryExtended,
        IAddressRepositoryExtended addressRepositoryExtended,
        IPaymentRepositoryExtended paymentRepositoryExtended,
        IRentalRepositoryExtended rentalRepositoryExtended
    ) {
        super(
            customerRepositoryExtended,
            addressRepositoryExtended,
            paymentRepositoryExtended,
            rentalRepositoryExtended
        );
    }
    //Add your custom code here
}
