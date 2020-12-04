package com.fastcode.testdocbuild11.domain.core.address;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.fastcode.testdocbuild11.domain.core.city.CityEntity;
import com.fastcode.testdocbuild11.domain.core.city.ICityRepository;
import com.fastcode.testdocbuild11.domain.core.customer.ICustomerRepository;
import com.fastcode.testdocbuild11.domain.core.staff.IStaffRepository;
import com.fastcode.testdocbuild11.domain.core.store.IStoreRepository;
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
public class AddressManagerTest {

    @InjectMocks
    protected AddressManager _addressManager;

    @Mock
    protected IAddressRepository _addressRepository;

    @Mock
    protected ICityRepository _cityRepository;

    @Mock
    protected ICustomerRepository _customerRepository;

    @Mock
    protected IStaffRepository _staffRepository;

    @Mock
    protected IStoreRepository _storeRepository;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    protected static Integer ID = 15;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_addressManager);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findAddressById_IdIsNotNullAndIdExists_ReturnAddress() {
        AddressEntity address = mock(AddressEntity.class);

        Optional<AddressEntity> dbAddress = Optional.of((AddressEntity) address);
        Mockito.when(_addressRepository.findById(any(Integer.class))).thenReturn(dbAddress);
        Assertions.assertThat(_addressManager.findById(ID)).isEqualTo(address);
    }

    @Test
    public void findAddressById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Mockito
            .<Optional<AddressEntity>>when(_addressRepository.findById(any(Integer.class)))
            .thenReturn(Optional.empty());
        Assertions.assertThat(_addressManager.findById(ID)).isEqualTo(null);
    }

    @Test
    public void createAddress_AddressIsNotNullAndAddressDoesNotExist_StoreAddress() {
        AddressEntity address = mock(AddressEntity.class);
        Mockito.when(_addressRepository.save(any(AddressEntity.class))).thenReturn(address);
        Assertions.assertThat(_addressManager.create(address)).isEqualTo(address);
    }

    @Test
    public void deleteAddress_AddressExists_RemoveAddress() {
        AddressEntity address = mock(AddressEntity.class);
        _addressManager.delete(address);
        verify(_addressRepository).delete(address);
    }

    @Test
    public void updateAddress_AddressIsNotNullAndAddressExists_UpdateAddress() {
        AddressEntity address = mock(AddressEntity.class);
        Mockito.when(_addressRepository.save(any(AddressEntity.class))).thenReturn(address);
        Assertions.assertThat(_addressManager.update(address)).isEqualTo(address);
    }

    @Test
    public void findAll_PageableIsNotNull_ReturnPage() {
        Page<AddressEntity> address = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        Predicate predicate = mock(Predicate.class);

        Mockito.when(_addressRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(address);
        Assertions.assertThat(_addressManager.findAll(predicate, pageable)).isEqualTo(address);
    }

    //City
    @Test
    public void getCity_if_AddressIdIsNotNull_returnCity() {
        AddressEntity addressEntity = mock(AddressEntity.class);
        CityEntity city = mock(CityEntity.class);

        Optional<AddressEntity> dbAddress = Optional.of((AddressEntity) addressEntity);
        Mockito.<Optional<AddressEntity>>when(_addressRepository.findById(any(Integer.class))).thenReturn(dbAddress);
        Mockito.when(addressEntity.getCity()).thenReturn(city);
        Assertions.assertThat(_addressManager.getCity(ID)).isEqualTo(city);
    }
}
