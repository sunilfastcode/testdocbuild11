package com.fastcode.testdocbuild11.domain.extended.rental;

import com.fastcode.testdocbuild11.domain.core.rental.RentalManager;
import com.fastcode.testdocbuild11.domain.extended.customer.ICustomerRepositoryExtended;
import com.fastcode.testdocbuild11.domain.extended.inventory.IInventoryRepositoryExtended;
import com.fastcode.testdocbuild11.domain.extended.payment.IPaymentRepositoryExtended;
import com.fastcode.testdocbuild11.domain.extended.staff.IStaffRepositoryExtended;
import org.springframework.stereotype.Component;

@Component("rentalManagerExtended")
public class RentalManagerExtended extends RentalManager implements IRentalManagerExtended {

    public RentalManagerExtended(
        IRentalRepositoryExtended rentalRepositoryExtended,
        ICustomerRepositoryExtended customerRepositoryExtended,
        IInventoryRepositoryExtended inventoryRepositoryExtended,
        IPaymentRepositoryExtended paymentRepositoryExtended,
        IStaffRepositoryExtended staffRepositoryExtended
    ) {
        super(
            rentalRepositoryExtended,
            customerRepositoryExtended,
            inventoryRepositoryExtended,
            paymentRepositoryExtended,
            staffRepositoryExtended
        );
    }
    //Add your custom code here
}
