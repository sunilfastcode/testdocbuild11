package com.fastcode.testdocbuild11.application.core.customer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.testdocbuild11.application.core.customer.dto.*;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.fastcode.testdocbuild11.domain.core.address.AddressEntity;
import com.fastcode.testdocbuild11.domain.core.address.IAddressManager;
import com.fastcode.testdocbuild11.domain.core.customer.*;
import com.fastcode.testdocbuild11.domain.core.customer.CustomerEntity;
import com.fastcode.testdocbuild11.domain.core.customer.QCustomerEntity;
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
public class CustomerAppServiceTest {

    @InjectMocks
    @Spy
    protected CustomerAppService _appService;

    @Mock
    protected ICustomerManager _customerManager;

    @Mock
    protected IAddressManager _addressManager;

    @Mock
    protected ICustomerMapper _mapper;

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
    public void findCustomerById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Mockito.when(_customerManager.findById(any(Integer.class))).thenReturn(null);
        Assertions.assertThat(_appService.findById(ID)).isEqualTo(null);
    }

    @Test
    public void findCustomerById_IdIsNotNullAndIdExists_ReturnCustomer() {
        CustomerEntity customer = mock(CustomerEntity.class);
        Mockito.when(_customerManager.findById(any(Integer.class))).thenReturn(customer);

        Assertions
            .assertThat(_appService.findById(ID))
            .isEqualTo(_mapper.customerEntityToFindCustomerByIdOutput(customer));
    }

    @Test
    public void createCustomer_CustomerIsNotNullAndCustomerDoesNotExist_StoreCustomer() {
        CustomerEntity customerEntity = mock(CustomerEntity.class);
        CreateCustomerInput customerInput = new CreateCustomerInput();

        AddressEntity address = mock(AddressEntity.class);
        customerInput.setAddressId((short) 15);

        Mockito.when(_addressManager.findById(any(Integer.class))).thenReturn(address);

        Mockito
            .when(_mapper.createCustomerInputToCustomerEntity(any(CreateCustomerInput.class)))
            .thenReturn(customerEntity);
        Mockito.when(_customerManager.create(any(CustomerEntity.class))).thenReturn(customerEntity);

        Assertions
            .assertThat(_appService.create(customerInput))
            .isEqualTo(_mapper.customerEntityToCreateCustomerOutput(customerEntity));
    }

    @Test
    public void createCustomer_CustomerIsNotNullAndCustomerDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {
        CreateCustomerInput customer = mock(CreateCustomerInput.class);

        Mockito.when(_mapper.createCustomerInputToCustomerEntity(any(CreateCustomerInput.class))).thenReturn(null);
        Assertions.assertThat(_appService.create(customer)).isEqualTo(null);
    }

    @Test
    public void createCustomer_CustomerIsNotNullAndCustomerDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {
        CreateCustomerInput customer = new CreateCustomerInput();

        customer.setAddressId((short) 15);

        Mockito.when(_addressManager.findById(any(Integer.class))).thenReturn(null);
        Assertions.assertThat(_appService.create(customer)).isEqualTo(null);
    }

    @Test
    public void updateCustomer_CustomerIsNotNullAndCustomerDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {
        UpdateCustomerInput customer = mock(UpdateCustomerInput.class);
        CustomerEntity customerEntity = mock(CustomerEntity.class);

        Mockito
            .when(_mapper.updateCustomerInputToCustomerEntity(any(UpdateCustomerInput.class)))
            .thenReturn(customerEntity);
        Assertions.assertThat(_appService.update(ID, customer)).isEqualTo(null);
    }

    @Test
    public void updateCustomer_CustomerIsNotNullAndCustomerDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {
        UpdateCustomerInput customer = new UpdateCustomerInput();
        customer.setAddressId((short) 15);

        Mockito.when(_addressManager.findById(any(Integer.class))).thenReturn(null);
        Assertions.assertThat(_appService.update(ID, customer)).isEqualTo(null);
    }

    @Test
    public void updateCustomer_CustomerIdIsNotNullAndIdExists_ReturnUpdatedCustomer() {
        CustomerEntity customerEntity = mock(CustomerEntity.class);
        UpdateCustomerInput customer = mock(UpdateCustomerInput.class);

        Mockito
            .when(_mapper.updateCustomerInputToCustomerEntity(any(UpdateCustomerInput.class)))
            .thenReturn(customerEntity);
        Mockito.when(_customerManager.update(any(CustomerEntity.class))).thenReturn(customerEntity);
        Assertions
            .assertThat(_appService.update(ID, customer))
            .isEqualTo(_mapper.customerEntityToUpdateCustomerOutput(customerEntity));
    }

    @Test
    public void deleteCustomer_CustomerIsNotNullAndCustomerExists_CustomerRemoved() {
        CustomerEntity customerEntity = mock(CustomerEntity.class);
        Mockito.when(_customerManager.findById(any(Integer.class))).thenReturn(customerEntity);

        _appService.delete(ID);
        verify(_customerManager).delete(customerEntity);
    }

    @Test
    public void find_ListIsEmpty_ReturnList() throws Exception {
        List<CustomerEntity> list = new ArrayList<>();
        Page<CustomerEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindCustomerByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_customerManager.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void find_ListIsNotEmpty_ReturnList() throws Exception {
        List<CustomerEntity> list = new ArrayList<>();
        CustomerEntity customer = mock(CustomerEntity.class);
        list.add(customer);
        Page<CustomerEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindCustomerByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");
        output.add(_mapper.customerEntityToFindCustomerByIdOutput(customer));

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_customerManager.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
        QCustomerEntity customer = QCustomerEntity.customerEntity;
        SearchFields searchFields = new SearchFields();
        searchFields.setOperator("equals");
        searchFields.setSearchValue("xyz");
        Map<String, SearchFields> map = new HashMap<>();
        map.put("email", searchFields);
        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("xyz", String.valueOf(ID));
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(customer.email.eq("xyz"));
        Assertions.assertThat(_appService.searchKeyValuePair(customer, map, searchMap)).isEqualTo(builder);
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
        list.add("email");
        list.add("firstName");
        list.add("lastName");
        _appService.checkProperties(list);
    }

    @Test
    public void search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
        Map<String, SearchFields> map = new HashMap<>();
        QCustomerEntity customer = QCustomerEntity.customerEntity;
        List<SearchFields> fieldsList = new ArrayList<>();
        SearchFields fields = new SearchFields();
        SearchCriteria search = new SearchCriteria();
        search.setType(3);
        search.setValue("xyz");
        search.setOperator("equals");
        fields.setFieldName("email");
        fields.setOperator("equals");
        fields.setSearchValue("xyz");
        fieldsList.add(fields);
        search.setFields(fieldsList);
        BooleanBuilder builder = new BooleanBuilder();
        builder.or(customer.email.eq("xyz"));
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
        Mockito
            .doReturn(builder)
            .when(_appService)
            .searchKeyValuePair(any(QCustomerEntity.class), any(HashMap.class), any(HashMap.class));

        Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
    }

    @Test
    public void search_StringIsNull_ReturnNull() throws Exception {
        Assertions.assertThat(_appService.search(null)).isEqualTo(null);
    }

    //Address
    @Test
    public void GetAddress_IfCustomerIdAndAddressIdIsNotNullAndCustomerExists_ReturnAddress() {
        CustomerEntity customerEntity = mock(CustomerEntity.class);
        AddressEntity address = mock(AddressEntity.class);

        Mockito.when(_customerManager.findById(any(Integer.class))).thenReturn(customerEntity);
        Mockito.when(_customerManager.getAddress(any(Integer.class))).thenReturn(address);
        Assertions
            .assertThat(_appService.getAddress(ID))
            .isEqualTo(_mapper.addressEntityToGetAddressOutput(address, customerEntity));
    }

    @Test
    public void GetAddress_IfCustomerIdAndAddressIdIsNotNullAndCustomerDoesNotExist_ReturnNull() {
        Mockito.when(_customerManager.findById(any(Integer.class))).thenReturn(null);
        Assertions.assertThat(_appService.getAddress(ID)).isEqualTo(null);
    }

    @Test
    public void ParsepaymentsJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        String keyString = "15";
        joinColumnMap.put("customerId", keyString);
        Assertions.assertThat(_appService.parsePaymentsJoinColumn(keyString)).isEqualTo(joinColumnMap);
    }

    @Test
    public void ParserentalsJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        String keyString = "15";
        joinColumnMap.put("customerId", keyString);
        Assertions.assertThat(_appService.parseRentalsJoinColumn(keyString)).isEqualTo(joinColumnMap);
    }
}
