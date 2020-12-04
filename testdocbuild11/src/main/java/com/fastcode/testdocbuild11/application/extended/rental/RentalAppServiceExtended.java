package com.fastcode.testdocbuild11.application.extended.rental;

import com.fastcode.testdocbuild11.application.core.rental.RentalAppService;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.domain.extended.customer.ICustomerManagerExtended;
import com.fastcode.testdocbuild11.domain.extended.inventory.IInventoryManagerExtended;
import com.fastcode.testdocbuild11.domain.extended.rental.IRentalManagerExtended;
import com.fastcode.testdocbuild11.domain.extended.staff.IStaffManagerExtended;
import org.springframework.stereotype.Service;

@Service("rentalAppServiceExtended")
public class RentalAppServiceExtended extends RentalAppService implements IRentalAppServiceExtended {

    public RentalAppServiceExtended(
        IRentalManagerExtended rentalManagerExtended,
        ICustomerManagerExtended customerManagerExtended,
        IInventoryManagerExtended inventoryManagerExtended,
        IStaffManagerExtended staffManagerExtended,
        IRentalMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(
            rentalManagerExtended,
            customerManagerExtended,
            inventoryManagerExtended,
            staffManagerExtended,
            mapper,
            logHelper
        );
    }
    //Add your custom code here

}
