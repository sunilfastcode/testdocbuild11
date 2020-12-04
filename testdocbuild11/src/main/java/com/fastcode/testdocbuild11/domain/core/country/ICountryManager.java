package com.fastcode.testdocbuild11.domain.core.country;

import com.querydsl.core.types.Predicate;
import java.time.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICountryManager {
    CountryEntity create(CountryEntity country);

    void delete(CountryEntity country);

    CountryEntity update(CountryEntity country);

    CountryEntity findById(Integer id);

    Page<CountryEntity> findAll(Predicate predicate, Pageable pageable);
}
