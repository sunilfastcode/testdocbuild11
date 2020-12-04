package com.fastcode.testdocbuild11.domain.core.payment;

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
import com.fastcode.testdocbuild11.domain.core.rental.IRentalRepository;
import com.fastcode.testdocbuild11.domain.core.rental.RentalEntity;
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
public class PaymentManagerTest {

    @InjectMocks
    protected PaymentManager _paymentManager;

    @Mock
    protected IPaymentRepository _paymentRepository;

    @Mock
    protected ICustomerRepository _customerRepository;

    @Mock
    protected IRentalRepository _rentalRepository;

    @Mock
    protected IStaffRepository _staffRepository;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    protected static Integer ID = 15;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_paymentManager);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findPaymentById_IdIsNotNullAndIdExists_ReturnPayment() {
        PaymentEntity payment = mock(PaymentEntity.class);

        Optional<PaymentEntity> dbPayment = Optional.of((PaymentEntity) payment);
        Mockito.when(_paymentRepository.findById(any(Integer.class))).thenReturn(dbPayment);
        Assertions.assertThat(_paymentManager.findById(ID)).isEqualTo(payment);
    }

    @Test
    public void findPaymentById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Mockito
            .<Optional<PaymentEntity>>when(_paymentRepository.findById(any(Integer.class)))
            .thenReturn(Optional.empty());
        Assertions.assertThat(_paymentManager.findById(ID)).isEqualTo(null);
    }

    @Test
    public void createPayment_PaymentIsNotNullAndPaymentDoesNotExist_StorePayment() {
        PaymentEntity payment = mock(PaymentEntity.class);
        Mockito.when(_paymentRepository.save(any(PaymentEntity.class))).thenReturn(payment);
        Assertions.assertThat(_paymentManager.create(payment)).isEqualTo(payment);
    }

    @Test
    public void deletePayment_PaymentExists_RemovePayment() {
        PaymentEntity payment = mock(PaymentEntity.class);
        _paymentManager.delete(payment);
        verify(_paymentRepository).delete(payment);
    }

    @Test
    public void updatePayment_PaymentIsNotNullAndPaymentExists_UpdatePayment() {
        PaymentEntity payment = mock(PaymentEntity.class);
        Mockito.when(_paymentRepository.save(any(PaymentEntity.class))).thenReturn(payment);
        Assertions.assertThat(_paymentManager.update(payment)).isEqualTo(payment);
    }

    @Test
    public void findAll_PageableIsNotNull_ReturnPage() {
        Page<PaymentEntity> payment = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        Predicate predicate = mock(Predicate.class);

        Mockito.when(_paymentRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(payment);
        Assertions.assertThat(_paymentManager.findAll(predicate, pageable)).isEqualTo(payment);
    }

    //Customer
    @Test
    public void getCustomer_if_PaymentIdIsNotNull_returnCustomer() {
        PaymentEntity paymentEntity = mock(PaymentEntity.class);
        CustomerEntity customer = mock(CustomerEntity.class);

        Optional<PaymentEntity> dbPayment = Optional.of((PaymentEntity) paymentEntity);
        Mockito.<Optional<PaymentEntity>>when(_paymentRepository.findById(any(Integer.class))).thenReturn(dbPayment);
        Mockito.when(paymentEntity.getCustomer()).thenReturn(customer);
        Assertions.assertThat(_paymentManager.getCustomer(ID)).isEqualTo(customer);
    }

    //Rental
    @Test
    public void getRental_if_PaymentIdIsNotNull_returnRental() {
        PaymentEntity paymentEntity = mock(PaymentEntity.class);
        RentalEntity rental = mock(RentalEntity.class);

        Optional<PaymentEntity> dbPayment = Optional.of((PaymentEntity) paymentEntity);
        Mockito.<Optional<PaymentEntity>>when(_paymentRepository.findById(any(Integer.class))).thenReturn(dbPayment);
        Mockito.when(paymentEntity.getRental()).thenReturn(rental);
        Assertions.assertThat(_paymentManager.getRental(ID)).isEqualTo(rental);
    }

    //Staff
    @Test
    public void getStaff_if_PaymentIdIsNotNull_returnStaff() {
        PaymentEntity paymentEntity = mock(PaymentEntity.class);
        StaffEntity staff = mock(StaffEntity.class);

        Optional<PaymentEntity> dbPayment = Optional.of((PaymentEntity) paymentEntity);
        Mockito.<Optional<PaymentEntity>>when(_paymentRepository.findById(any(Integer.class))).thenReturn(dbPayment);
        Mockito.when(paymentEntity.getStaff()).thenReturn(staff);
        Assertions.assertThat(_paymentManager.getStaff(ID)).isEqualTo(staff);
    }
}
