package com.fastcode.testdocbuild11.domain.core.store;

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
public class StoreManagerTest {

    @InjectMocks
    protected StoreManager _storeManager;

    @Mock
    protected IStoreRepository _storeRepository;

    @Mock
    protected IAddressRepository _addressRepository;

    @Mock
    protected IStaffRepository _staffRepository;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    protected static Integer ID = 15;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_storeManager);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findStoreById_IdIsNotNullAndIdExists_ReturnStore() {
        StoreEntity store = mock(StoreEntity.class);

        Optional<StoreEntity> dbStore = Optional.of((StoreEntity) store);
        Mockito.when(_storeRepository.findById(any(Integer.class))).thenReturn(dbStore);
        Assertions.assertThat(_storeManager.findById(ID)).isEqualTo(store);
    }

    @Test
    public void findStoreById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Mockito.<Optional<StoreEntity>>when(_storeRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        Assertions.assertThat(_storeManager.findById(ID)).isEqualTo(null);
    }

    @Test
    public void createStore_StoreIsNotNullAndStoreDoesNotExist_StoreStore() {
        StoreEntity store = mock(StoreEntity.class);
        Mockito.when(_storeRepository.save(any(StoreEntity.class))).thenReturn(store);
        Assertions.assertThat(_storeManager.create(store)).isEqualTo(store);
    }

    @Test
    public void deleteStore_StoreExists_RemoveStore() {
        StoreEntity store = mock(StoreEntity.class);
        _storeManager.delete(store);
        verify(_storeRepository).delete(store);
    }

    @Test
    public void updateStore_StoreIsNotNullAndStoreExists_UpdateStore() {
        StoreEntity store = mock(StoreEntity.class);
        Mockito.when(_storeRepository.save(any(StoreEntity.class))).thenReturn(store);
        Assertions.assertThat(_storeManager.update(store)).isEqualTo(store);
    }

    @Test
    public void findAll_PageableIsNotNull_ReturnPage() {
        Page<StoreEntity> store = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        Predicate predicate = mock(Predicate.class);

        Mockito.when(_storeRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(store);
        Assertions.assertThat(_storeManager.findAll(predicate, pageable)).isEqualTo(store);
    }

    //Address
    @Test
    public void getAddress_if_StoreIdIsNotNull_returnAddress() {
        StoreEntity storeEntity = mock(StoreEntity.class);
        AddressEntity address = mock(AddressEntity.class);

        Optional<StoreEntity> dbStore = Optional.of((StoreEntity) storeEntity);
        Mockito.<Optional<StoreEntity>>when(_storeRepository.findById(any(Integer.class))).thenReturn(dbStore);
        Mockito.when(storeEntity.getAddress()).thenReturn(address);
        Assertions.assertThat(_storeManager.getAddress(ID)).isEqualTo(address);
    }

    //Staff
    @Test
    public void getStaff_if_StoreIdIsNotNull_returnStaff() {
        StoreEntity storeEntity = mock(StoreEntity.class);
        StaffEntity staff = mock(StaffEntity.class);

        Optional<StoreEntity> dbStore = Optional.of((StoreEntity) storeEntity);
        Mockito.<Optional<StoreEntity>>when(_storeRepository.findById(any(Integer.class))).thenReturn(dbStore);
        Mockito.when(storeEntity.getStaff()).thenReturn(staff);
        Assertions.assertThat(_storeManager.getStaff(ID)).isEqualTo(staff);
    }
}
