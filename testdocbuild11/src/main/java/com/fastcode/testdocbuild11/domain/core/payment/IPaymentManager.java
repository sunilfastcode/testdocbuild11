package com.fastcode.testdocbuild11.domain.core.payment;

import com.fastcode.testdocbuild11.domain.core.customer.CustomerEntity;
import com.fastcode.testdocbuild11.domain.core.rental.RentalEntity;
import com.fastcode.testdocbuild11.domain.core.staff.StaffEntity;
import com.querydsl.core.types.Predicate;
import java.time.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPaymentManager {
    PaymentEntity create(PaymentEntity payment);

    void delete(PaymentEntity payment);

    PaymentEntity update(PaymentEntity payment);

    PaymentEntity findById(Integer id);

    Page<PaymentEntity> findAll(Predicate predicate, Pageable pageable);

    CustomerEntity getCustomer(Integer paymentId);

    RentalEntity getRental(Integer paymentId);

    StaffEntity getStaff(Integer paymentId);
}
