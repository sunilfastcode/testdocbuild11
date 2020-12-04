package com.fastcode.testdocbuild11.application.extended.address;

import com.fastcode.testdocbuild11.application.core.address.AddressAppService;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.domain.extended.address.IAddressManagerExtended;
import com.fastcode.testdocbuild11.domain.extended.city.ICityManagerExtended;
import org.springframework.stereotype.Service;

@Service("addressAppServiceExtended")
public class AddressAppServiceExtended extends AddressAppService implements IAddressAppServiceExtended {

    public AddressAppServiceExtended(
        IAddressManagerExtended addressManagerExtended,
        ICityManagerExtended cityManagerExtended,
        IAddressMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(addressManagerExtended, cityManagerExtended, mapper, logHelper);
    }
    //Add your custom code here

}
