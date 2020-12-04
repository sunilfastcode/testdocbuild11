package com.fastcode.testdocbuild11.domain.core.customer;

import com.fastcode.testdocbuild11.domain.core.address.AddressEntity;
import com.fastcode.testdocbuild11.domain.core.address.IAddressRepository;
import com.fastcode.testdocbuild11.domain.core.payment.IPaymentRepository;
import com.fastcode.testdocbuild11.domain.core.rental.IRentalRepository;
import com.querydsl.core.types.Predicate;
import java.time.*;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component("customerManager")
@RequiredArgsConstructor
public class CustomerManager implements ICustomerManager {

    @Qualifier("customerRepository")
    @NonNull
    protected final ICustomerRepository _customerRepository;

    @Qualifier("addressRepository")
    @NonNull
    protected final IAddressRepository _addressRepository;

    @Qualifier("paymentRepository")
    @NonNull
    protected final IPaymentRepository _paymentRepository;

    @Qualifier("rentalRepository")
    @NonNull
    protected final IRentalRepository _rentalRepository;

    public CustomerEntity create(CustomerEntity customer) {
        return _customerRepository.save(customer);
    }

    public void delete(CustomerEntity customer) {
        _customerRepository.delete(customer);
    }

    public CustomerEntity update(CustomerEntity customer) {
        return _customerRepository.save(customer);
    }

    public CustomerEntity findById(Integer customerId) {
        Optional<CustomerEntity> dbCustomer = _customerRepository.findById(customerId);
        return dbCustomer.orElse(null);
    }

    public Page<CustomerEntity> findAll(Predicate predicate, Pageable pageable) {
        return _customerRepository.findAll(predicate, pageable);
    }

    public AddressEntity getAddress(Integer customerId) {
        Optional<CustomerEntity> dbCustomer = _customerRepository.findById(customerId);
        if (dbCustomer.isPresent()) {
            CustomerEntity existingCustomer = dbCustomer.get();
            return existingCustomer.getAddress();
        } else {
            return null;
        }
    }
}
