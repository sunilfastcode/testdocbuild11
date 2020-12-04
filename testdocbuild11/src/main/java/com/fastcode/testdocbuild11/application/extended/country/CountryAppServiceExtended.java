package com.fastcode.testdocbuild11.application.extended.country;

import com.fastcode.testdocbuild11.application.core.country.CountryAppService;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.domain.extended.country.ICountryManagerExtended;
import org.springframework.stereotype.Service;

@Service("countryAppServiceExtended")
public class CountryAppServiceExtended extends CountryAppService implements ICountryAppServiceExtended {

    public CountryAppServiceExtended(
        ICountryManagerExtended countryManagerExtended,
        ICountryMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(countryManagerExtended, mapper, logHelper);
    }
    //Add your custom code here

}
