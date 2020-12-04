package com.fastcode.testdocbuild11.domain.extended.address;

import com.fastcode.testdocbuild11.domain.core.address.AddressManager;
import com.fastcode.testdocbuild11.domain.extended.city.ICityRepositoryExtended;
import com.fastcode.testdocbuild11.domain.extended.customer.ICustomerRepositoryExtended;
import com.fastcode.testdocbuild11.domain.extended.staff.IStaffRepositoryExtended;
import com.fastcode.testdocbuild11.domain.extended.store.IStoreRepositoryExtended;
import org.springframework.stereotype.Component;

@Component("addressManagerExtended")
public class AddressManagerExtended extends AddressManager implements IAddressManagerExtended {

    public AddressManagerExtended(
        IAddressRepositoryExtended addressRepositoryExtended,
        ICityRepositoryExtended cityRepositoryExtended,
        ICustomerRepositoryExtended customerRepositoryExtended,
        IStaffRepositoryExtended staffRepositoryExtended,
        IStoreRepositoryExtended storeRepositoryExtended
    ) {
        super(
            addressRepositoryExtended,
            cityRepositoryExtended,
            customerRepositoryExtended,
            staffRepositoryExtended,
            storeRepositoryExtended
        );
    }
    //Add your custom code here
}
