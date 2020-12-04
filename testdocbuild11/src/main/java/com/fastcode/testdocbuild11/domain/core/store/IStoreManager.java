package com.fastcode.testdocbuild11.domain.core.store;

import com.fastcode.testdocbuild11.domain.core.address.AddressEntity;
import com.fastcode.testdocbuild11.domain.core.staff.StaffEntity;
import com.querydsl.core.types.Predicate;
import java.time.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IStoreManager {
    StoreEntity create(StoreEntity store);

    void delete(StoreEntity store);

    StoreEntity update(StoreEntity store);

    StoreEntity findById(Integer id);

    Page<StoreEntity> findAll(Predicate predicate, Pageable pageable);

    AddressEntity getAddress(Integer storeId);

    StaffEntity getStaff(Integer storeId);
}
