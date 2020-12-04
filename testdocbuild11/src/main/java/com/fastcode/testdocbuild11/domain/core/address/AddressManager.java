package com.fastcode.testdocbuild11.domain.core.address;

import com.fastcode.testdocbuild11.domain.core.city.CityEntity;
import com.fastcode.testdocbuild11.domain.core.city.ICityRepository;
import com.fastcode.testdocbuild11.domain.core.customer.ICustomerRepository;
import com.fastcode.testdocbuild11.domain.core.staff.IStaffRepository;
import com.fastcode.testdocbuild11.domain.core.store.IStoreRepository;
import com.querydsl.core.types.Predicate;
import java.time.*;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component("addressManager")
@RequiredArgsConstructor
public class AddressManager implements IAddressManager {

    @Qualifier("addressRepository")
    @NonNull
    protected final IAddressRepository _addressRepository;

    @Qualifier("cityRepository")
    @NonNull
    protected final ICityRepository _cityRepository;

    @Qualifier("customerRepository")
    @NonNull
    protected final ICustomerRepository _customerRepository;

    @Qualifier("staffRepository")
    @NonNull
    protected final IStaffRepository _staffRepository;

    @Qualifier("storeRepository")
    @NonNull
    protected final IStoreRepository _storeRepository;

    public AddressEntity create(AddressEntity address) {
        return _addressRepository.save(address);
    }

    public void delete(AddressEntity address) {
        _addressRepository.delete(address);
    }

    public AddressEntity update(AddressEntity address) {
        return _addressRepository.save(address);
    }

    public AddressEntity findById(Integer addressId) {
        Optional<AddressEntity> dbAddress = _addressRepository.findById(addressId);
        return dbAddress.orElse(null);
    }

    public Page<AddressEntity> findAll(Predicate predicate, Pageable pageable) {
        return _addressRepository.findAll(predicate, pageable);
    }

    public CityEntity getCity(Integer addressId) {
        Optional<AddressEntity> dbAddress = _addressRepository.findById(addressId);
        if (dbAddress.isPresent()) {
            AddressEntity existingAddress = dbAddress.get();
            return existingAddress.getCity();
        } else {
            return null;
        }
    }
}
