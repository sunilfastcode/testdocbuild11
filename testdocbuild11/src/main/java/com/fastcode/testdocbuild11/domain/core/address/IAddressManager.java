package com.fastcode.testdocbuild11.domain.core.address;

import com.fastcode.testdocbuild11.domain.core.city.CityEntity;
import com.querydsl.core.types.Predicate;
import java.time.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAddressManager {
    AddressEntity create(AddressEntity address);

    void delete(AddressEntity address);

    AddressEntity update(AddressEntity address);

    AddressEntity findById(Integer id);

    Page<AddressEntity> findAll(Predicate predicate, Pageable pageable);

    CityEntity getCity(Integer addressId);
}
