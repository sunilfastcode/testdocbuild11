package com.fastcode.testdocbuild11.domain.core.staff;

import com.fastcode.testdocbuild11.domain.core.address.AddressEntity;
import com.fastcode.testdocbuild11.domain.core.address.IAddressRepository;
import com.fastcode.testdocbuild11.domain.core.payment.IPaymentRepository;
import com.fastcode.testdocbuild11.domain.core.rental.IRentalRepository;
import com.fastcode.testdocbuild11.domain.core.store.IStoreRepository;
import com.fastcode.testdocbuild11.domain.core.store.StoreEntity;
import com.querydsl.core.types.Predicate;
import java.time.*;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component("staffManager")
@RequiredArgsConstructor
public class StaffManager implements IStaffManager {

    @Qualifier("staffRepository")
    @NonNull
    protected final IStaffRepository _staffRepository;

    @Qualifier("addressRepository")
    @NonNull
    protected final IAddressRepository _addressRepository;

    @Qualifier("paymentRepository")
    @NonNull
    protected final IPaymentRepository _paymentRepository;

    @Qualifier("rentalRepository")
    @NonNull
    protected final IRentalRepository _rentalRepository;

    @Qualifier("storeRepository")
    @NonNull
    protected final IStoreRepository _storeRepository;

    public StaffEntity create(StaffEntity staff) {
        return _staffRepository.save(staff);
    }

    public void delete(StaffEntity staff) {
        _staffRepository.delete(staff);
    }

    public StaffEntity update(StaffEntity staff) {
        return _staffRepository.save(staff);
    }

    public StaffEntity findById(Integer staffId) {
        Optional<StaffEntity> dbStaff = _staffRepository.findById(staffId);
        return dbStaff.orElse(null);
    }

    public Page<StaffEntity> findAll(Predicate predicate, Pageable pageable) {
        return _staffRepository.findAll(predicate, pageable);
    }

    public AddressEntity getAddress(Integer staffId) {
        Optional<StaffEntity> dbStaff = _staffRepository.findById(staffId);
        if (dbStaff.isPresent()) {
            StaffEntity existingStaff = dbStaff.get();
            return existingStaff.getAddress();
        } else {
            return null;
        }
    }

    public StoreEntity getStore(Integer staffId) {
        Optional<StaffEntity> dbStaff = _staffRepository.findById(staffId);
        if (dbStaff.isPresent()) {
            StaffEntity existingStaff = dbStaff.get();
            return existingStaff.getStore();
        } else {
            return null;
        }
    }
}
