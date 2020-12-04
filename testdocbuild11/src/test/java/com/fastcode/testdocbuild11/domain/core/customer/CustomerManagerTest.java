package com.fastcode.testdocbuild11.domain.core.customer;

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
public class CustomerManagerTest {

    @InjectMocks
    protected CustomerManager _customerManager;

    @Mock
    protected ICustomerRepository _customerRepository;

    @Mock
    protected IAddressRepository _addressRepository;

    @Mock
    protected IPaymentRepository _paymentRepository;

    @Mock
    protected IRentalRepository _rentalRepository;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    protected static Integer ID = 15;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_customerManager);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findCustomerById_IdIsNotNullAndIdExists_ReturnCustomer() {
        CustomerEntity customer = mock(CustomerEntity.class);

        Optional<CustomerEntity> dbCustomer = Optional.of((CustomerEntity) customer);
        Mockito.when(_customerRepository.findById(any(Integer.class))).thenReturn(dbCustomer);
        Assertions.assertThat(_customerManager.findById(ID)).isEqualTo(customer);
    }

    @Test
    public void findCustomerById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Mockito
            .<Optional<CustomerEntity>>when(_customerRepository.findById(any(Integer.class)))
            .thenReturn(Optional.empty());
        Assertions.assertThat(_customerManager.findById(ID)).isEqualTo(null);
    }

    @Test
    public void createCustomer_CustomerIsNotNullAndCustomerDoesNotExist_StoreCustomer() {
        CustomerEntity customer = mock(CustomerEntity.class);
        Mockito.when(_customerRepository.save(any(CustomerEntity.class))).thenReturn(customer);
        Assertions.assertThat(_customerManager.create(customer)).isEqualTo(customer);
    }

    @Test
    public void deleteCustomer_CustomerExists_RemoveCustomer() {
        CustomerEntity customer = mock(CustomerEntity.class);
        _customerManager.delete(customer);
        verify(_customerRepository).delete(customer);
    }

    @Test
    public void updateCustomer_CustomerIsNotNullAndCustomerExists_UpdateCustomer() {
        CustomerEntity customer = mock(CustomerEntity.class);
        Mockito.when(_customerRepository.save(any(CustomerEntity.class))).thenReturn(customer);
        Assertions.assertThat(_customerManager.update(customer)).isEqualTo(customer);
    }

    @Test
    public void findAll_PageableIsNotNull_ReturnPage() {
        Page<CustomerEntity> customer = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        Predicate predicate = mock(Predicate.class);

        Mockito.when(_customerRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(customer);
        Assertions.assertThat(_customerManager.findAll(predicate, pageable)).isEqualTo(customer);
    }

    //Address
    @Test
    public void getAddress_if_CustomerIdIsNotNull_returnAddress() {
        CustomerEntity customerEntity = mock(CustomerEntity.class);
        AddressEntity address = mock(AddressEntity.class);

        Optional<CustomerEntity> dbCustomer = Optional.of((CustomerEntity) customerEntity);
        Mockito.<Optional<CustomerEntity>>when(_customerRepository.findById(any(Integer.class))).thenReturn(dbCustomer);
        Mockito.when(customerEntity.getAddress()).thenReturn(address);
        Assertions.assertThat(_customerManager.getAddress(ID)).isEqualTo(address);
    }
}
