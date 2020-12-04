package com.fastcode.testdocbuild11.domain.core.staff;

import com.fastcode.testdocbuild11.domain.core.address.AddressEntity;
import com.fastcode.testdocbuild11.domain.core.store.StoreEntity;
import com.querydsl.core.types.Predicate;
import java.time.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IStaffManager {
    StaffEntity create(StaffEntity staff);

    void delete(StaffEntity staff);

    StaffEntity update(StaffEntity staff);

    StaffEntity findById(Integer id);

    Page<StaffEntity> findAll(Predicate predicate, Pageable pageable);

    AddressEntity getAddress(Integer staffId);

    StoreEntity getStore(Integer staffId);
}
