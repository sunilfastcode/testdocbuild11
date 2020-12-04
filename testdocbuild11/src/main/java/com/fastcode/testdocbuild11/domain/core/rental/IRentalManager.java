package com.fastcode.testdocbuild11.domain.core.rental;

import com.fastcode.testdocbuild11.domain.core.customer.CustomerEntity;
import com.fastcode.testdocbuild11.domain.core.inventory.InventoryEntity;
import com.fastcode.testdocbuild11.domain.core.staff.StaffEntity;
import com.querydsl.core.types.Predicate;
import java.time.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRentalManager {
    RentalEntity create(RentalEntity rental);

    void delete(RentalEntity rental);

    RentalEntity update(RentalEntity rental);

    RentalEntity findById(Integer id);

    Page<RentalEntity> findAll(Predicate predicate, Pageable pageable);

    CustomerEntity getCustomer(Integer rentalId);

    InventoryEntity getInventory(Integer rentalId);

    StaffEntity getStaff(Integer rentalId);
}
