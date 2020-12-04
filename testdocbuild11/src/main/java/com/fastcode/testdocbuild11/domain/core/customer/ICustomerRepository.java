package com.fastcode.testdocbuild11.domain.core.customer;

import java.time.*;
import java.util.*;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("customerRepository")
public interface ICustomerRepository
    extends JpaRepository<CustomerEntity, Integer>, QuerydslPredicateExecutor<CustomerEntity> {}
