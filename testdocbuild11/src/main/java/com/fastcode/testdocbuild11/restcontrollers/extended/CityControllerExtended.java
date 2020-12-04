package com.fastcode.testdocbuild11.restcontrollers.extended;

import com.fastcode.testdocbuild11.application.extended.address.IAddressAppServiceExtended;
import com.fastcode.testdocbuild11.application.extended.city.ICityAppServiceExtended;
import com.fastcode.testdocbuild11.application.extended.country.ICountryAppServiceExtended;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.restcontrollers.core.CityController;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/city/extended")
public class CityControllerExtended extends CityController {

    public CityControllerExtended(
        ICityAppServiceExtended cityAppServiceExtended,
        IAddressAppServiceExtended addressAppServiceExtended,
        ICountryAppServiceExtended countryAppServiceExtended,
        LoggingHelper helper,
        Environment env
    ) {
        super(cityAppServiceExtended, addressAppServiceExtended, countryAppServiceExtended, helper, env);
    }
    //Add your custom code here

}
