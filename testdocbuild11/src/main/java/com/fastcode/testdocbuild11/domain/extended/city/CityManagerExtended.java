package com.fastcode.testdocbuild11.domain.extended.city;

import com.fastcode.testdocbuild11.domain.core.city.CityManager;
import com.fastcode.testdocbuild11.domain.extended.address.IAddressRepositoryExtended;
import com.fastcode.testdocbuild11.domain.extended.country.ICountryRepositoryExtended;
import org.springframework.stereotype.Component;

@Component("cityManagerExtended")
public class CityManagerExtended extends CityManager implements ICityManagerExtended {

    public CityManagerExtended(
        ICityRepositoryExtended cityRepositoryExtended,
        IAddressRepositoryExtended addressRepositoryExtended,
        ICountryRepositoryExtended countryRepositoryExtended
    ) {
        super(cityRepositoryExtended, addressRepositoryExtended, countryRepositoryExtended);
    }
    //Add your custom code here
}
