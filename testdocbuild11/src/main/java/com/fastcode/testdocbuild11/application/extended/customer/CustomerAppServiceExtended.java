package com.fastcode.testdocbuild11.application.extended.customer;

import com.fastcode.testdocbuild11.application.core.customer.CustomerAppService;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.domain.extended.address.IAddressManagerExtended;
import com.fastcode.testdocbuild11.domain.extended.customer.ICustomerManagerExtended;
import org.springframework.stereotype.Service;

@Service("customerAppServiceExtended")
public class CustomerAppServiceExtended extends CustomerAppService implements ICustomerAppServiceExtended {

    public CustomerAppServiceExtended(
        ICustomerManagerExtended customerManagerExtended,
        IAddressManagerExtended addressManagerExtended,
        ICustomerMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(customerManagerExtended, addressManagerExtended, mapper, logHelper);
    }
    //Add your custom code here

}
