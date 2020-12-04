package com.fastcode.testdocbuild11.domain.extended.country;

import com.fastcode.testdocbuild11.domain.core.country.CountryManager;
import com.fastcode.testdocbuild11.domain.extended.city.ICityRepositoryExtended;
import org.springframework.stereotype.Component;

@Component("countryManagerExtended")
public class CountryManagerExtended extends CountryManager implements ICountryManagerExtended {

    public CountryManagerExtended(
        ICountryRepositoryExtended countryRepositoryExtended,
        ICityRepositoryExtended cityRepositoryExtended
    ) {
        super(countryRepositoryExtended, cityRepositoryExtended);
    }
    //Add your custom code here
}
