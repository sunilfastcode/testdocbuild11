package com.fastcode.testdocbuild11.domain.core.store;

import com.fastcode.testdocbuild11.domain.core.address.AddressEntity;
import com.fastcode.testdocbuild11.domain.core.address.IAddressRepository;
import com.fastcode.testdocbuild11.domain.core.staff.IStaffRepository;
import com.fastcode.testdocbuild11.domain.core.staff.StaffEntity;
import com.querydsl.core.types.Predicate;
import java.time.*;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component("storeManager")
@RequiredArgsConstructor
public class StoreManager implements IStoreManager {

    @Qualifier("storeRepository")
    @NonNull
    protected final IStoreRepository _storeRepository;

    @Qualifier("addressRepository")
    @NonNull
    protected final IAddressRepository _addressRepository;

    @Qualifier("staffRepository")
    @NonNull
    protected final IStaffRepository _staffRepository;

    public StoreEntity create(StoreEntity store) {
        return _storeRepository.save(store);
    }

    public void delete(StoreEntity store) {
        _storeRepository.delete(store);
    }

    public StoreEntity update(StoreEntity store) {
        return _storeRepository.save(store);
    }

    public StoreEntity findById(Integer storeId) {
        Optional<StoreEntity> dbStore = _storeRepository.findById(storeId);
        return dbStore.orElse(null);
    }

    public Page<StoreEntity> findAll(Predicate predicate, Pageable pageable) {
        return _storeRepository.findAll(predicate, pageable);
    }

    public AddressEntity getAddress(Integer storeId) {
        Optional<StoreEntity> dbStore = _storeRepository.findById(storeId);
        if (dbStore.isPresent()) {
            StoreEntity existingStore = dbStore.get();
            return existingStore.getAddress();
        } else {
            return null;
        }
    }

    public StaffEntity getStaff(Integer storeId) {
        Optional<StoreEntity> dbStore = _storeRepository.findById(storeId);
        if (dbStore.isPresent()) {
            StoreEntity existingStore = dbStore.get();
            return existingStore.getStaff();
        } else {
            return null;
        }
    }
}
