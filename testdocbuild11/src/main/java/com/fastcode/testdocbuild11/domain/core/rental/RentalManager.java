package com.fastcode.testdocbuild11.domain.core.rental;

import com.fastcode.testdocbuild11.domain.core.customer.CustomerEntity;
import com.fastcode.testdocbuild11.domain.core.customer.ICustomerRepository;
import com.fastcode.testdocbuild11.domain.core.inventory.IInventoryRepository;
import com.fastcode.testdocbuild11.domain.core.inventory.InventoryEntity;
import com.fastcode.testdocbuild11.domain.core.payment.IPaymentRepository;
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

@Component("rentalManager")
@RequiredArgsConstructor
public class RentalManager implements IRentalManager {

    @Qualifier("rentalRepository")
    @NonNull
    protected final IRentalRepository _rentalRepository;

    @Qualifier("customerRepository")
    @NonNull
    protected final ICustomerRepository _customerRepository;

    @Qualifier("inventoryRepository")
    @NonNull
    protected final IInventoryRepository _inventoryRepository;

    @Qualifier("paymentRepository")
    @NonNull
    protected final IPaymentRepository _paymentRepository;

    @Qualifier("staffRepository")
    @NonNull
    protected final IStaffRepository _staffRepository;

    public RentalEntity create(RentalEntity rental) {
        return _rentalRepository.save(rental);
    }

    public void delete(RentalEntity rental) {
        _rentalRepository.delete(rental);
    }

    public RentalEntity update(RentalEntity rental) {
        return _rentalRepository.save(rental);
    }

    public RentalEntity findById(Integer rentalId) {
        Optional<RentalEntity> dbRental = _rentalRepository.findById(rentalId);
        return dbRental.orElse(null);
    }

    public Page<RentalEntity> findAll(Predicate predicate, Pageable pageable) {
        return _rentalRepository.findAll(predicate, pageable);
    }

    public CustomerEntity getCustomer(Integer rentalId) {
        Optional<RentalEntity> dbRental = _rentalRepository.findById(rentalId);
        if (dbRental.isPresent()) {
            RentalEntity existingRental = dbRental.get();
            return existingRental.getCustomer();
        } else {
            return null;
        }
    }

    public InventoryEntity getInventory(Integer rentalId) {
        Optional<RentalEntity> dbRental = _rentalRepository.findById(rentalId);
        if (dbRental.isPresent()) {
            RentalEntity existingRental = dbRental.get();
            return existingRental.getInventory();
        } else {
            return null;
        }
    }

    public StaffEntity getStaff(Integer rentalId) {
        Optional<RentalEntity> dbRental = _rentalRepository.findById(rentalId);
        if (dbRental.isPresent()) {
            RentalEntity existingRental = dbRental.get();
            return existingRental.getStaff();
        } else {
            return null;
        }
    }
}
