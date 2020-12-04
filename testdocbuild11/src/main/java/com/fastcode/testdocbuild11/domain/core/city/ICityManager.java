package com.fastcode.testdocbuild11.domain.core.city;

import com.fastcode.testdocbuild11.domain.core.country.CountryEntity;
import com.querydsl.core.types.Predicate;
import java.time.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICityManager {
    CityEntity create(CityEntity city);

    void delete(CityEntity city);

    CityEntity update(CityEntity city);

    CityEntity findById(Integer id);

    Page<CityEntity> findAll(Predicate predicate, Pageable pageable);

    CountryEntity getCountry(Integer cityId);
}
