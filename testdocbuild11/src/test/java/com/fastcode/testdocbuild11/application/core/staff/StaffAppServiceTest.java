package com.fastcode.testdocbuild11.application.core.staff;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.testdocbuild11.application.core.staff.dto.*;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.fastcode.testdocbuild11.domain.core.address.AddressEntity;
import com.fastcode.testdocbuild11.domain.core.address.IAddressManager;
import com.fastcode.testdocbuild11.domain.core.staff.*;
import com.fastcode.testdocbuild11.domain.core.staff.QStaffEntity;
import com.fastcode.testdocbuild11.domain.core.staff.StaffEntity;
import com.fastcode.testdocbuild11.domain.core.store.IStoreManager;
import com.fastcode.testdocbuild11.domain.core.store.StoreEntity;
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
public class StaffAppServiceTest {

    @InjectMocks
    @Spy
    protected StaffAppService _appService;

    @Mock
    protected IStaffManager _staffManager;

    @Mock
    protected IAddressManager _addressManager;

    @Mock
    protected IStoreManager _storeManager;

    @Mock
    protected IStaffMapper _mapper;

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
    public void findStaffById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Mockito.when(_staffManager.findById(any(Integer.class))).thenReturn(null);
        Assertions.assertThat(_appService.findById(ID)).isEqualTo(null);
    }

    @Test
    public void findStaffById_IdIsNotNullAndIdExists_ReturnStaff() {
        StaffEntity staff = mock(StaffEntity.class);
        Mockito.when(_staffManager.findById(any(Integer.class))).thenReturn(staff);

        Assertions.assertThat(_appService.findById(ID)).isEqualTo(_mapper.staffEntityToFindStaffByIdOutput(staff));
    }

    @Test
    public void createStaff_StaffIsNotNullAndStaffDoesNotExist_StoreStaff() {
        StaffEntity staffEntity = mock(StaffEntity.class);
        CreateStaffInput staffInput = new CreateStaffInput();

        AddressEntity address = mock(AddressEntity.class);
        staffInput.setAddressId((short) 15);

        Mockito.when(_addressManager.findById(any(Integer.class))).thenReturn(address);

        Mockito.when(_mapper.createStaffInputToStaffEntity(any(CreateStaffInput.class))).thenReturn(staffEntity);
        Mockito.when(_staffManager.create(any(StaffEntity.class))).thenReturn(staffEntity);

        Assertions
            .assertThat(_appService.create(staffInput))
            .isEqualTo(_mapper.staffEntityToCreateStaffOutput(staffEntity));
    }

    @Test
    public void createStaff_StaffIsNotNullAndStaffDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {
        CreateStaffInput staff = mock(CreateStaffInput.class);

        Mockito.when(_mapper.createStaffInputToStaffEntity(any(CreateStaffInput.class))).thenReturn(null);
        Assertions.assertThat(_appService.create(staff)).isEqualTo(null);
    }

    @Test
    public void createStaff_StaffIsNotNullAndStaffDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {
        CreateStaffInput staff = new CreateStaffInput();

        staff.setAddressId((short) 15);

        Mockito.when(_addressManager.findById(any(Integer.class))).thenReturn(null);
        Assertions.assertThat(_appService.create(staff)).isEqualTo(null);
    }

    @Test
    public void updateStaff_StaffIsNotNullAndStaffDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {
        UpdateStaffInput staff = mock(UpdateStaffInput.class);
        StaffEntity staffEntity = mock(StaffEntity.class);

        Mockito.when(_mapper.updateStaffInputToStaffEntity(any(UpdateStaffInput.class))).thenReturn(staffEntity);
        Assertions.assertThat(_appService.update(ID, staff)).isEqualTo(null);
    }

    @Test
    public void updateStaff_StaffIsNotNullAndStaffDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {
        UpdateStaffInput staff = new UpdateStaffInput();
        staff.setAddressId((short) 15);

        Mockito.when(_addressManager.findById(any(Integer.class))).thenReturn(null);
        Assertions.assertThat(_appService.update(ID, staff)).isEqualTo(null);
    }

    @Test
    public void updateStaff_StaffIdIsNotNullAndIdExists_ReturnUpdatedStaff() {
        StaffEntity staffEntity = mock(StaffEntity.class);
        UpdateStaffInput staff = mock(UpdateStaffInput.class);

        Mockito.when(_mapper.updateStaffInputToStaffEntity(any(UpdateStaffInput.class))).thenReturn(staffEntity);
        Mockito.when(_staffManager.update(any(StaffEntity.class))).thenReturn(staffEntity);
        Assertions
            .assertThat(_appService.update(ID, staff))
            .isEqualTo(_mapper.staffEntityToUpdateStaffOutput(staffEntity));
    }

    @Test
    public void deleteStaff_StaffIsNotNullAndStaffExists_StaffRemoved() {
        StaffEntity staffEntity = mock(StaffEntity.class);
        Mockito.when(_staffManager.findById(any(Integer.class))).thenReturn(staffEntity);

        _appService.delete(ID);
        verify(_staffManager).delete(staffEntity);
    }

    @Test
    public void find_ListIsEmpty_ReturnList() throws Exception {
        List<StaffEntity> list = new ArrayList<>();
        Page<StaffEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindStaffByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_staffManager.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void find_ListIsNotEmpty_ReturnList() throws Exception {
        List<StaffEntity> list = new ArrayList<>();
        StaffEntity staff = mock(StaffEntity.class);
        list.add(staff);
        Page<StaffEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindStaffByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");
        output.add(_mapper.staffEntityToFindStaffByIdOutput(staff));

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_staffManager.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
        QStaffEntity staff = QStaffEntity.staffEntity;
        SearchFields searchFields = new SearchFields();
        searchFields.setOperator("equals");
        searchFields.setSearchValue("xyz");
        Map<String, SearchFields> map = new HashMap<>();
        map.put("email", searchFields);
        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("xyz", String.valueOf(ID));
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(staff.email.eq("xyz"));
        Assertions.assertThat(_appService.searchKeyValuePair(staff, map, searchMap)).isEqualTo(builder);
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
        list.add("password");
        list.add("username");
        _appService.checkProperties(list);
    }

    @Test
    public void search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
        Map<String, SearchFields> map = new HashMap<>();
        QStaffEntity staff = QStaffEntity.staffEntity;
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
        builder.or(staff.email.eq("xyz"));
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
        Mockito
            .doReturn(builder)
            .when(_appService)
            .searchKeyValuePair(any(QStaffEntity.class), any(HashMap.class), any(HashMap.class));

        Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
    }

    @Test
    public void search_StringIsNull_ReturnNull() throws Exception {
        Assertions.assertThat(_appService.search(null)).isEqualTo(null);
    }

    //Address
    @Test
    public void GetAddress_IfStaffIdAndAddressIdIsNotNullAndStaffExists_ReturnAddress() {
        StaffEntity staffEntity = mock(StaffEntity.class);
        AddressEntity address = mock(AddressEntity.class);

        Mockito.when(_staffManager.findById(any(Integer.class))).thenReturn(staffEntity);
        Mockito.when(_staffManager.getAddress(any(Integer.class))).thenReturn(address);
        Assertions
            .assertThat(_appService.getAddress(ID))
            .isEqualTo(_mapper.addressEntityToGetAddressOutput(address, staffEntity));
    }

    @Test
    public void GetAddress_IfStaffIdAndAddressIdIsNotNullAndStaffDoesNotExist_ReturnNull() {
        Mockito.when(_staffManager.findById(any(Integer.class))).thenReturn(null);
        Assertions.assertThat(_appService.getAddress(ID)).isEqualTo(null);
    }

    //Store
    @Test
    public void GetStore_IfStaffIdAndStoreIdIsNotNullAndStaffExists_ReturnStore() {
        StaffEntity staffEntity = mock(StaffEntity.class);
        StoreEntity store = mock(StoreEntity.class);

        Mockito.when(_staffManager.findById(any(Integer.class))).thenReturn(staffEntity);
        Mockito.when(_staffManager.getStore(any(Integer.class))).thenReturn(store);
        Assertions
            .assertThat(_appService.getStore(ID))
            .isEqualTo(_mapper.storeEntityToGetStoreOutput(store, staffEntity));
    }

    @Test
    public void GetStore_IfStaffIdAndStoreIdIsNotNullAndStaffDoesNotExist_ReturnNull() {
        Mockito.when(_staffManager.findById(any(Integer.class))).thenReturn(null);
        Assertions.assertThat(_appService.getStore(ID)).isEqualTo(null);
    }

    @Test
    public void ParsepaymentsJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        String keyString = "15";
        joinColumnMap.put("staffId", keyString);
        Assertions.assertThat(_appService.parsePaymentsJoinColumn(keyString)).isEqualTo(joinColumnMap);
    }

    @Test
    public void ParserentalsJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        String keyString = "15";
        joinColumnMap.put("staffId", keyString);
        Assertions.assertThat(_appService.parseRentalsJoinColumn(keyString)).isEqualTo(joinColumnMap);
    }
}
