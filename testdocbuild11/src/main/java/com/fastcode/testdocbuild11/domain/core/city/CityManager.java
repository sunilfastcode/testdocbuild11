package com.fastcode.testdocbuild11.domain.core.city;

import com.fastcode.testdocbuild11.domain.core.address.IAddressRepository;
import com.fastcode.testdocbuild11.domain.core.country.CountryEntity;
import com.fastcode.testdocbuild11.domain.core.country.ICountryRepository;
import com.querydsl.core.types.Predicate;
import java.time.*;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component("cityManager")
@RequiredArgsConstructor
public class CityManager implements ICityManager {

    @Qualifier("cityRepository")
    @NonNull
    protected final ICityRepository _cityRepository;

    @Qualifier("addressRepository")
    @NonNull
    protected final IAddressRepository _addressRepository;

    @Qualifier("countryRepository")
    @NonNull
    protected final ICountryRepository _countryRepository;

    public CityEntity create(CityEntity city) {
        return _cityRepository.save(city);
    }

    public void delete(CityEntity city) {
        _cityRepository.delete(city);
    }

    public CityEntity update(CityEntity city) {
        return _cityRepository.save(city);
    }

    public CityEntity findById(Integer cityId) {
        Optional<CityEntity> dbCity = _cityRepository.findById(cityId);
        return dbCity.orElse(null);
    }

    public Page<CityEntity> findAll(Predicate predicate, Pageable pageable) {
        return _cityRepository.findAll(predicate, pageable);
    }

    public CountryEntity getCountry(Integer cityId) {
        Optional<CityEntity> dbCity = _cityRepository.findById(cityId);
        if (dbCity.isPresent()) {
            CityEntity existingCity = dbCity.get();
            return existingCity.getCountry();
        } else {
            return null;
        }
    }
}
