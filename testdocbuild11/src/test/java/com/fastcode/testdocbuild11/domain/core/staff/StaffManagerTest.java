package com.fastcode.testdocbuild11.domain.core.staff;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.fastcode.testdocbuild11.domain.core.address.AddressEntity;
import com.fastcode.testdocbuild11.domain.core.address.IAddressRepository;
import com.fastcode.testdocbuild11.domain.core.payment.IPaymentRepository;
import com.fastcode.testdocbuild11.domain.core.rental.IRentalRepository;
import com.fastcode.testdocbuild11.domain.core.store.IStoreRepository;
import com.fastcode.testdocbuild11.domain.core.store.StoreEntity;
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
public class StaffManagerTest {

    @InjectMocks
    protected StaffManager _staffManager;

    @Mock
    protected IStaffRepository _staffRepository;

    @Mock
    protected IAddressRepository _addressRepository;

    @Mock
    protected IPaymentRepository _paymentRepository;

    @Mock
    protected IRentalRepository _rentalRepository;

    @Mock
    protected IStoreRepository _storeRepository;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    protected static Integer ID = 15;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_staffManager);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findStaffById_IdIsNotNullAndIdExists_ReturnStaff() {
        StaffEntity staff = mock(StaffEntity.class);

        Optional<StaffEntity> dbStaff = Optional.of((StaffEntity) staff);
        Mockito.when(_staffRepository.findById(any(Integer.class))).thenReturn(dbStaff);
        Assertions.assertThat(_staffManager.findById(ID)).isEqualTo(staff);
    }

    @Test
    public void findStaffById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Mockito.<Optional<StaffEntity>>when(_staffRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        Assertions.assertThat(_staffManager.findById(ID)).isEqualTo(null);
    }

    @Test
    public void createStaff_StaffIsNotNullAndStaffDoesNotExist_StoreStaff() {
        StaffEntity staff = mock(StaffEntity.class);
        Mockito.when(_staffRepository.save(any(StaffEntity.class))).thenReturn(staff);
        Assertions.assertThat(_staffManager.create(staff)).isEqualTo(staff);
    }

    @Test
    public void deleteStaff_StaffExists_RemoveStaff() {
        StaffEntity staff = mock(StaffEntity.class);
        _staffManager.delete(staff);
        verify(_staffRepository).delete(staff);
    }

    @Test
    public void updateStaff_StaffIsNotNullAndStaffExists_UpdateStaff() {
        StaffEntity staff = mock(StaffEntity.class);
        Mockito.when(_staffRepository.save(any(StaffEntity.class))).thenReturn(staff);
        Assertions.assertThat(_staffManager.update(staff)).isEqualTo(staff);
    }

    @Test
    public void findAll_PageableIsNotNull_ReturnPage() {
        Page<StaffEntity> staff = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        Predicate predicate = mock(Predicate.class);

        Mockito.when(_staffRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(staff);
        Assertions.assertThat(_staffManager.findAll(predicate, pageable)).isEqualTo(staff);
    }

    //Address
    @Test
    public void getAddress_if_StaffIdIsNotNull_returnAddress() {
        StaffEntity staffEntity = mock(StaffEntity.class);
        AddressEntity address = mock(AddressEntity.class);

        Optional<StaffEntity> dbStaff = Optional.of((StaffEntity) staffEntity);
        Mockito.<Optional<StaffEntity>>when(_staffRepository.findById(any(Integer.class))).thenReturn(dbStaff);
        Mockito.when(staffEntity.getAddress()).thenReturn(address);
        Assertions.assertThat(_staffManager.getAddress(ID)).isEqualTo(address);
    }

    //Store
    @Test
    public void getStore_if_StaffIdIsNotNull_returnStore() {
        StaffEntity staffEntity = mock(StaffEntity.class);
        StoreEntity store = mock(StoreEntity.class);

        Optional<StaffEntity> dbStaff = Optional.of((StaffEntity) staffEntity);
        Mockito.<Optional<StaffEntity>>when(_staffRepository.findById(any(Integer.class))).thenReturn(dbStaff);
        Mockito.when(staffEntity.getStore()).thenReturn(store);
        Assertions.assertThat(_staffManager.getStore(ID)).isEqualTo(store);
    }
}
