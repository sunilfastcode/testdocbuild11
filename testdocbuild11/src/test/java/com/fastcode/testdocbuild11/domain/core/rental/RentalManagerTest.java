package com.fastcode.testdocbuild11.domain.core.rental;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
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
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class RentalManagerTest {

    @InjectMocks
    protected RentalManager _rentalManager;

    @Mock
    protected IRentalRepository _rentalRepository;

    @Mock
    protected ICustomerRepository _customerRepository;

    @Mock
    protected IInventoryRepository _inventoryRepository;

    @Mock
    protected IPaymentRepository _paymentRepository;

    @Mock
    protected IStaffRepository _staffRepository;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    protected static Integer ID = 15;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_rentalManager);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findRentalById_IdIsNotNullAndIdExists_ReturnRental() {
        RentalEntity rental = mock(RentalEntity.class);

        Optional<RentalEntity> dbRental = Optional.of((RentalEntity) rental);
        Mockito.when(_rentalRepository.findById(any(Integer.class))).thenReturn(dbRental);
        Assertions.assertThat(_rentalManager.findById(ID)).isEqualTo(rental);
    }

    @Test
    public void findRentalById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Mockito
            .<Optional<RentalEntity>>when(_rentalRepository.findById(any(Integer.class)))
            .thenReturn(Optional.empty());
        Assertions.assertThat(_rentalManager.findById(ID)).isEqualTo(null);
    }

    @Test
    public void createRental_RentalIsNotNullAndRentalDoesNotExist_StoreRental() {
        RentalEntity rental = mock(RentalEntity.class);
        Mockito.when(_rentalRepository.save(any(RentalEntity.class))).thenReturn(rental);
        Assertions.assertThat(_rentalManager.create(rental)).isEqualTo(rental);
    }

    @Test
    public void deleteRental_RentalExists_RemoveRental() {
        RentalEntity rental = mock(RentalEntity.class);
        _rentalManager.delete(rental);
        verify(_rentalRepository).delete(rental);
    }

    @Test
    public void updateRental_RentalIsNotNullAndRentalExists_UpdateRental() {
        RentalEntity rental = mock(RentalEntity.class);
        Mockito.when(_rentalRepository.save(any(RentalEntity.class))).thenReturn(rental);
        Assertions.assertThat(_rentalManager.update(rental)).isEqualTo(rental);
    }

    @Test
    public void findAll_PageableIsNotNull_ReturnPage() {
        Page<RentalEntity> rental = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        Predicate predicate = mock(Predicate.class);

        Mockito.when(_rentalRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(rental);
        Assertions.assertThat(_rentalManager.findAll(predicate, pageable)).isEqualTo(rental);
    }

    //Customer
    @Test
    public void getCustomer_if_RentalIdIsNotNull_returnCustomer() {
        RentalEntity rentalEntity = mock(RentalEntity.class);
        CustomerEntity customer = mock(CustomerEntity.class);

        Optional<RentalEntity> dbRental = Optional.of((RentalEntity) rentalEntity);
        Mockito.<Optional<RentalEntity>>when(_rentalRepository.findById(any(Integer.class))).thenReturn(dbRental);
        Mockito.when(rentalEntity.getCustomer()).thenReturn(customer);
        Assertions.assertThat(_rentalManager.getCustomer(ID)).isEqualTo(customer);
    }

    //Inventory
    @Test
    public void getInventory_if_RentalIdIsNotNull_returnInventory() {
        RentalEntity rentalEntity = mock(RentalEntity.class);
        InventoryEntity inventory = mock(InventoryEntity.class);

        Optional<RentalEntity> dbRental = Optional.of((RentalEntity) rentalEntity);
        Mockito.<Optional<RentalEntity>>when(_rentalRepository.findById(any(Integer.class))).thenReturn(dbRental);
        Mockito.when(rentalEntity.getInventory()).thenReturn(inventory);
        Assertions.assertThat(_rentalManager.getInventory(ID)).isEqualTo(inventory);
    }

    //Staff
    @Test
    public void getStaff_if_RentalIdIsNotNull_returnStaff() {
        RentalEntity rentalEntity = mock(RentalEntity.class);
        StaffEntity staff = mock(StaffEntity.class);

        Optional<RentalEntity> dbRental = Optional.of((RentalEntity) rentalEntity);
        Mockito.<Optional<RentalEntity>>when(_rentalRepository.findById(any(Integer.class))).thenReturn(dbRental);
        Mockito.when(rentalEntity.getStaff()).thenReturn(staff);
        Assertions.assertThat(_rentalManager.getStaff(ID)).isEqualTo(staff);
    }
}
