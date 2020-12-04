package com.fastcode.testdocbuild11.application.core.payment;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.testdocbuild11.application.core.payment.dto.*;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.fastcode.testdocbuild11.domain.core.customer.CustomerEntity;
import com.fastcode.testdocbuild11.domain.core.customer.ICustomerManager;
import com.fastcode.testdocbuild11.domain.core.payment.*;
import com.fastcode.testdocbuild11.domain.core.payment.PaymentEntity;
import com.fastcode.testdocbuild11.domain.core.payment.QPaymentEntity;
import com.fastcode.testdocbuild11.domain.core.rental.IRentalManager;
import com.fastcode.testdocbuild11.domain.core.rental.RentalEntity;
import com.fastcode.testdocbuild11.domain.core.staff.IStaffManager;
import com.fastcode.testdocbuild11.domain.core.staff.StaffEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class PaymentAppServiceTest {

    @InjectMocks
    @Spy
    protected PaymentAppService _appService;

    @Mock
    protected IPaymentManager _paymentManager;

    @Mock
    protected ICustomerManager _customerManager;

    @Mock
    protected IRentalManager _rentalManager;

    @Mock
    protected IStaffManager _staffManager;

    @Mock
    protected IPaymentMapper _mapper;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    protected static Integer ID = 15;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_appService);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findPaymentById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Mockito.when(_paymentManager.findById(any(Integer.class))).thenReturn(null);
        Assertions.assertThat(_appService.findById(ID)).isEqualTo(null);
    }

    @Test
    public void findPaymentById_IdIsNotNullAndIdExists_ReturnPayment() {
        PaymentEntity payment = mock(PaymentEntity.class);
        Mockito.when(_paymentManager.findById(any(Integer.class))).thenReturn(payment);

        Assertions
            .assertThat(_appService.findById(ID))
            .isEqualTo(_mapper.paymentEntityToFindPaymentByIdOutput(payment));
    }

    @Test
    public void createPayment_PaymentIsNotNullAndPaymentDoesNotExist_StorePayment() {
        PaymentEntity paymentEntity = mock(PaymentEntity.class);
        CreatePaymentInput paymentInput = new CreatePaymentInput();

        CustomerEntity customer = mock(CustomerEntity.class);
        paymentInput.setCustomerId((short) 15);

        Mockito.when(_customerManager.findById(any(Integer.class))).thenReturn(customer);

        RentalEntity rental = mock(RentalEntity.class);
        paymentInput.setRentalId(15);

        Mockito.when(_rentalManager.findById(any(Integer.class))).thenReturn(rental);

        StaffEntity staff = mock(StaffEntity.class);
        paymentInput.setStaffId((short) 15);

        Mockito.when(_staffManager.findById(any(Integer.class))).thenReturn(staff);

        Mockito
            .when(_mapper.createPaymentInputToPaymentEntity(any(CreatePaymentInput.class)))
            .thenReturn(paymentEntity);
        Mockito.when(_paymentManager.create(any(PaymentEntity.class))).thenReturn(paymentEntity);

        Assertions
            .assertThat(_appService.create(paymentInput))
            .isEqualTo(_mapper.paymentEntityToCreatePaymentOutput(paymentEntity));
    }

    @Test
    public void createPayment_PaymentIsNotNullAndPaymentDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {
        CreatePaymentInput payment = mock(CreatePaymentInput.class);

        Mockito.when(_mapper.createPaymentInputToPaymentEntity(any(CreatePaymentInput.class))).thenReturn(null);
        Assertions.assertThat(_appService.create(payment)).isEqualTo(null);
    }

    @Test
    public void createPayment_PaymentIsNotNullAndPaymentDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {
        CreatePaymentInput payment = new CreatePaymentInput();

        payment.setCustomerId((short) 15);

        Mockito.when(_customerManager.findById(any(Integer.class))).thenReturn(null);
        Assertions.assertThat(_appService.create(payment)).isEqualTo(null);
    }

    @Test
    public void updatePayment_PaymentIsNotNullAndPaymentDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {
        UpdatePaymentInput payment = mock(UpdatePaymentInput.class);
        PaymentEntity paymentEntity = mock(PaymentEntity.class);

        Mockito
            .when(_mapper.updatePaymentInputToPaymentEntity(any(UpdatePaymentInput.class)))
            .thenReturn(paymentEntity);
        Assertions.assertThat(_appService.update(ID, payment)).isEqualTo(null);
    }

    @Test
    public void updatePayment_PaymentIsNotNullAndPaymentDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {
        UpdatePaymentInput payment = new UpdatePaymentInput();
        payment.setCustomerId((short) 15);

        Mockito.when(_customerManager.findById(any(Integer.class))).thenReturn(null);
        Assertions.assertThat(_appService.update(ID, payment)).isEqualTo(null);
    }

    @Test
    public void updatePayment_PaymentIdIsNotNullAndIdExists_ReturnUpdatedPayment() {
        PaymentEntity paymentEntity = mock(PaymentEntity.class);
        UpdatePaymentInput payment = mock(UpdatePaymentInput.class);

        Mockito
            .when(_mapper.updatePaymentInputToPaymentEntity(any(UpdatePaymentInput.class)))
            .thenReturn(paymentEntity);
        Mockito.when(_paymentManager.update(any(PaymentEntity.class))).thenReturn(paymentEntity);
        Assertions
            .assertThat(_appService.update(ID, payment))
            .isEqualTo(_mapper.paymentEntityToUpdatePaymentOutput(paymentEntity));
    }

    @Test
    public void deletePayment_PaymentIsNotNullAndPaymentExists_PaymentRemoved() {
        PaymentEntity paymentEntity = mock(PaymentEntity.class);
        Mockito.when(_paymentManager.findById(any(Integer.class))).thenReturn(paymentEntity);

        _appService.delete(ID);
        verify(_paymentManager).delete(paymentEntity);
    }

    @Test
    public void find_ListIsEmpty_ReturnList() throws Exception {
        List<PaymentEntity> list = new ArrayList<>();
        Page<PaymentEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindPaymentByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_paymentManager.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void find_ListIsNotEmpty_ReturnList() throws Exception {
        List<PaymentEntity> list = new ArrayList<>();
        PaymentEntity payment = mock(PaymentEntity.class);
        list.add(payment);
        Page<PaymentEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindPaymentByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");
        output.add(_mapper.paymentEntityToFindPaymentByIdOutput(payment));

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_paymentManager.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
        QPaymentEntity payment = QPaymentEntity.paymentEntity;
        SearchFields searchFields = new SearchFields();
        searchFields.setOperator("equals");
        searchFields.setSearchValue("xyz");
        Map<String, SearchFields> map = new HashMap<>();
        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("xyz", String.valueOf(ID));
        BooleanBuilder builder = new BooleanBuilder();
        Assertions.assertThat(_appService.searchKeyValuePair(payment, map, searchMap)).isEqualTo(builder);
    }

    @Test(expected = Exception.class)
    public void checkProperties_PropertyDoesNotExist_ThrowException() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("xyz");
        _appService.checkProperties(list);
    }

    @Test
    public void checkProperties_PropertyExists_ReturnNothing() throws Exception {
        List<String> list = new ArrayList<>();
        _appService.checkProperties(list);
    }

    @Test
    public void search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
        Map<String, SearchFields> map = new HashMap<>();
        QPaymentEntity payment = QPaymentEntity.paymentEntity;
        List<SearchFields> fieldsList = new ArrayList<>();
        SearchFields fields = new SearchFields();
        SearchCriteria search = new SearchCriteria();
        search.setType(3);
        search.setValue("xyz");
        search.setOperator("equals");
        fields.setOperator("equals");
        fields.setSearchValue("xyz");
        fieldsList.add(fields);
        search.setFields(fieldsList);
        BooleanBuilder builder = new BooleanBuilder();
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
        Mockito
            .doReturn(builder)
            .when(_appService)
            .searchKeyValuePair(any(QPaymentEntity.class), any(HashMap.class), any(HashMap.class));

        Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
    }

    @Test
    public void search_StringIsNull_ReturnNull() throws Exception {
        Assertions.assertThat(_appService.search(null)).isEqualTo(null);
    }

    //Customer
    @Test
    public void GetCustomer_IfPaymentIdAndCustomerIdIsNotNullAndPaymentExists_ReturnCustomer() {
        PaymentEntity paymentEntity = mock(PaymentEntity.class);
        CustomerEntity customer = mock(CustomerEntity.class);

        Mockito.when(_paymentManager.findById(any(Integer.class))).thenReturn(paymentEntity);
        Mockito.when(_paymentManager.getCustomer(any(Integer.class))).thenReturn(customer);
        Assertions
            .assertThat(_appService.getCustomer(ID))
            .isEqualTo(_mapper.customerEntityToGetCustomerOutput(customer, paymentEntity));
    }

    @Test
    public void GetCustomer_IfPaymentIdAndCustomerIdIsNotNullAndPaymentDoesNotExist_ReturnNull() {
        Mockito.when(_paymentManager.findById(any(Integer.class))).thenReturn(null);
        Assertions.assertThat(_appService.getCustomer(ID)).isEqualTo(null);
    }

    //Rental
    @Test
    public void GetRental_IfPaymentIdAndRentalIdIsNotNullAndPaymentExists_ReturnRental() {
        PaymentEntity paymentEntity = mock(PaymentEntity.class);
        RentalEntity rental = mock(RentalEntity.class);

        Mockito.when(_paymentManager.findById(any(Integer.class))).thenReturn(paymentEntity);
        Mockito.when(_paymentManager.getRental(any(Integer.class))).thenReturn(rental);
        Assertions
            .assertThat(_appService.getRental(ID))
            .isEqualTo(_mapper.rentalEntityToGetRentalOutput(rental, paymentEntity));
    }

    @Test
    public void GetRental_IfPaymentIdAndRentalIdIsNotNullAndPaymentDoesNotExist_ReturnNull() {
        Mockito.when(_paymentManager.findById(any(Integer.class))).thenReturn(null);
        Assertions.assertThat(_appService.getRental(ID)).isEqualTo(null);
    }

    //Staff
    @Test
    public void GetStaff_IfPaymentIdAndStaffIdIsNotNullAndPaymentExists_ReturnStaff() {
        PaymentEntity paymentEntity = mock(PaymentEntity.class);
        StaffEntity staff = mock(StaffEntity.class);

        Mockito.when(_paymentManager.findById(any(Integer.class))).thenReturn(paymentEntity);
        Mockito.when(_paymentManager.getStaff(any(Integer.class))).thenReturn(staff);
        Assertions
            .assertThat(_appService.getStaff(ID))
            .isEqualTo(_mapper.staffEntityToGetStaffOutput(staff, paymentEntity));
    }

    @Test
    public void GetStaff_IfPaymentIdAndStaffIdIsNotNullAndPaymentDoesNotExist_ReturnNull() {
        Mockito.when(_paymentManager.findById(any(Integer.class))).thenReturn(null);
        Assertions.assertThat(_appService.getStaff(ID)).isEqualTo(null);
    }
}
