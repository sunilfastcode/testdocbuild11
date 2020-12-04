package com.fastcode.testdocbuild11.domain.core.country;

import com.fastcode.testdocbuild11.domain.core.city.ICityRepository;
import com.querydsl.core.types.Predicate;
import java.time.*;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component("countryManager")
@RequiredArgsConstructor
public class CountryManager implements ICountryManager {

    @Qualifier("countryRepository")
    @NonNull
    protected final ICountryRepository _countryRepository;

    @Qualifier("cityRepository")
    @NonNull
    protected final ICityRepository _cityRepository;

    public CountryEntity create(CountryEntity country) {
        return _countryRepository.save(country);
    }

    public void delete(CountryEntity country) {
        _countryRepository.delete(country);
    }

    public CountryEntity update(CountryEntity country) {
        return _countryRepository.save(country);
    }

    public CountryEntity findById(Integer countryId) {
        Optional<CountryEntity> dbCountry = _countryRepository.findById(countryId);
        return dbCountry.orElse(null);
    }

    public Page<CountryEntity> findAll(Predicate predicate, Pageable pageable) {
        return _countryRepository.findAll(predicate, pageable);
    }
}
