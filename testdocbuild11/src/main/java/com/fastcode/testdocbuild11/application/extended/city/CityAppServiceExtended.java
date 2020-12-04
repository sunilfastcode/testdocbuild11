package com.fastcode.testdocbuild11.application.extended.city;

import com.fastcode.testdocbuild11.application.core.city.CityAppService;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.domain.extended.city.ICityManagerExtended;
import com.fastcode.testdocbuild11.domain.extended.country.ICountryManagerExtended;
import org.springframework.stereotype.Service;

@Service("cityAppServiceExtended")
public class CityAppServiceExtended extends CityAppService implements ICityAppServiceExtended {

    public CityAppServiceExtended(
        ICityManagerExtended cityManagerExtended,
        ICountryManagerExtended countryManagerExtended,
        ICityMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(cityManagerExtended, countryManagerExtended, mapper, logHelper);
    }
    //Add your custom code here

}
