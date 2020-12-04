package com.fastcode.testdocbuild11.domain.core.customer;

import com.fastcode.testdocbuild11.domain.core.address.AddressEntity;
import com.querydsl.core.types.Predicate;
import java.time.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICustomerManager {
    CustomerEntity create(CustomerEntity customer);

    void delete(CustomerEntity customer);

    CustomerEntity update(CustomerEntity customer);

    CustomerEntity findById(Integer id);

    Page<CustomerEntity> findAll(Predicate predicate, Pageable pageable);

    AddressEntity getAddress(Integer customerId);
}
