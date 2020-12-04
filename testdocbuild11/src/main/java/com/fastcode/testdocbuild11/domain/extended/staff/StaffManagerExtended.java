package com.fastcode.testdocbuild11.domain.extended.staff;

import com.fastcode.testdocbuild11.domain.core.staff.StaffManager;
import com.fastcode.testdocbuild11.domain.extended.address.IAddressRepositoryExtended;
import com.fastcode.testdocbuild11.domain.extended.payment.IPaymentRepositoryExtended;
import com.fastcode.testdocbuild11.domain.extended.rental.IRentalRepositoryExtended;
import com.fastcode.testdocbuild11.domain.extended.store.IStoreRepositoryExtended;
import org.springframework.stereotype.Component;

@Component("staffManagerExtended")
public class StaffManagerExtended extends StaffManager implements IStaffManagerExtended {

    public StaffManagerExtended(
        IStaffRepositoryExtended staffRepositoryExtended,
        IAddressRepositoryExtended addressRepositoryExtended,
        IPaymentRepositoryExtended paymentRepositoryExtended,
        IRentalRepositoryExtended rentalRepositoryExtended,
        IStoreRepositoryExtended storeRepositoryExtended
    ) {
        super(
            staffRepositoryExtended,
            addressRepositoryExtended,
            paymentRepositoryExtended,
            rentalRepositoryExtended,
            storeRepositoryExtended
        );
    }
    //Add your custom code here
}
