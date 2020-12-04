package com.fastcode.testdocbuild11.domain.core.payment;

import com.fastcode.testdocbuild11.domain.core.customer.CustomerEntity;
import com.fastcode.testdocbuild11.domain.core.customer.ICustomerRepository;
import com.fastcode.testdocbuild11.domain.core.rental.IRentalRepository;
import com.fastcode.testdocbuild11.domain.core.rental.RentalEntity;
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

@Component("paymentManager")
@RequiredArgsConstructor
public class PaymentManager implements IPaymentManager {

    @Qualifier("paymentRepository")
    @NonNull
    protected final IPaymentRepository _paymentRepository;

    @Qualifier("customerRepository")
    @NonNull
    protected final ICustomerRepository _customerRepository;

    @Qualifier("rentalRepository")
    @NonNull
    protected final IRentalRepository _rentalRepository;

    @Qualifier("staffRepository")
    @NonNull
    protected final IStaffRepository _staffRepository;

    public PaymentEntity create(PaymentEntity payment) {
        return _paymentRepository.save(payment);
    }

    public void delete(PaymentEntity payment) {
        _paymentRepository.delete(payment);
    }

    public PaymentEntity update(PaymentEntity payment) {
        return _paymentRepository.save(payment);
    }

    public PaymentEntity findById(Integer paymentId) {
        Optional<PaymentEntity> dbPayment = _paymentRepository.findById(paymentId);
        return dbPayment.orElse(null);
    }

    public Page<PaymentEntity> findAll(Predicate predicate, Pageable pageable) {
        return _paymentRepository.findAll(predicate, pageable);
    }

    public CustomerEntity getCustomer(Integer paymentId) {
        Optional<PaymentEntity> dbPayment = _paymentRepository.findById(paymentId);
        if (dbPayment.isPresent()) {
            PaymentEntity existingPayment = dbPayment.get();
            return existingPayment.getCustomer();
        } else {
            return null;
        }
    }

    public RentalEntity getRental(Integer paymentId) {
        Optional<PaymentEntity> dbPayment = _paymentRepository.findById(paymentId);
        if (dbPayment.isPresent()) {
            PaymentEntity existingPayment = dbPayment.get();
            return existingPayment.getRental();
        } else {
            return null;
        }
    }

    public StaffEntity getStaff(Integer paymentId) {
        Optional<PaymentEntity> dbPayment = _paymentRepository.findById(paymentId);
        if (dbPayment.isPresent()) {
            PaymentEntity existingPayment = dbPayment.get();
            return existingPayment.getStaff();
        } else {
            return null;
        }
    }
}
