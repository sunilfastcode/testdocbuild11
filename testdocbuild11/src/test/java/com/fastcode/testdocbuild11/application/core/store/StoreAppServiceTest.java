package com.fastcode.testdocbuild11.application.core.store;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.testdocbuild11.application.core.store.dto.*;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.fastcode.testdocbuild11.domain.core.address.AddressEntity;
import com.fastcode.testdocbuild11.domain.core.address.IAddressManager;
import com.fastcode.testdocbuild11.domain.core.staff.IStaffManager;
import com.fastcode.testdocbuild11.domain.core.staff.StaffEntity;
import com.fastcode.testdocbuild11.domain.core.store.*;
import com.fastcode.testdocbuild11.domain.core.store.QStoreEntity;
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
public class StoreAppServiceTest {

    @InjectMocks
    @Spy
    protected StoreAppService _appService;

    @Mock
    protected IStoreManager _storeManager;

    @Mock
    protected IAddressManager _addressManager;

    @Mock
    protected IStaffManager _staffManager;

    @Mock
    protected IStoreMapper _mapper;

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
    public void findStoreById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Mockito.when(_storeManager.findById(any(Integer.class))).thenReturn(null);
        Assertions.assertThat(_appService.findById(ID)).isEqualTo(null);
    }

    @Test
    public void findStoreById_IdIsNotNullAndIdExists_ReturnStore() {
        StoreEntity store = mock(StoreEntity.class);
        Mockito.when(_storeManager.findById(any(Integer.class))).thenReturn(store);

        Assertions.assertThat(_appService.findById(ID)).isEqualTo(_mapper.storeEntityToFindStoreByIdOutput(store));
    }

    @Test
    public void createStore_StoreIsNotNullAndStoreDoesNotExist_StoreStore() {
        StoreEntity storeEntity = mock(StoreEntity.class);
        CreateStoreInput storeInput = new CreateStoreInput();

        AddressEntity address = mock(AddressEntity.class);
        storeInput.setAddressId((short) 15);

        Mockito.when(_addressManager.findById(any(Integer.class))).thenReturn(address);

        StaffEntity staff = mock(StaffEntity.class);
        storeInput.setManagerStaffId((short) 15);
        Mockito.when(_staffManager.findById(any(Integer.class))).thenReturn(staff);

        Mockito.when(_mapper.createStoreInputToStoreEntity(any(CreateStoreInput.class))).thenReturn(storeEntity);
        Mockito.when(_storeManager.create(any(StoreEntity.class))).thenReturn(storeEntity);

        Assertions
            .assertThat(_appService.create(storeInput))
            .isEqualTo(_mapper.storeEntityToCreateStoreOutput(storeEntity));
    }

    @Test
    public void createStore_StoreIsNotNullAndStoreDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {
        CreateStoreInput store = mock(CreateStoreInput.class);

        Mockito.when(_mapper.createStoreInputToStoreEntity(any(CreateStoreInput.class))).thenReturn(null);
        Assertions.assertThat(_appService.create(store)).isEqualTo(null);
    }

    @Test
    public void createStore_StoreIsNotNullAndStoreDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {
        CreateStoreInput store = new CreateStoreInput();

        store.setAddressId((short) 15);

        Mockito.when(_addressManager.findById(any(Integer.class))).thenReturn(null);
        Assertions.assertThat(_appService.create(store)).isEqualTo(null);
    }

    @Test
    public void updateStore_StoreIsNotNullAndStoreDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {
        UpdateStoreInput store = mock(UpdateStoreInput.class);
        StoreEntity storeEntity = mock(StoreEntity.class);

        Mockito.when(_mapper.updateStoreInputToStoreEntity(any(UpdateStoreInput.class))).thenReturn(storeEntity);
        Assertions.assertThat(_appService.update(ID, store)).isEqualTo(null);
    }

    @Test
    public void updateStore_StoreIsNotNullAndStoreDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {
        UpdateStoreInput store = new UpdateStoreInput();
        store.setAddressId((short) 15);

        Mockito.when(_addressManager.findById(any(Integer.class))).thenReturn(null);
        Assertions.assertThat(_appService.update(ID, store)).isEqualTo(null);
    }

    @Test
    public void updateStore_StoreIdIsNotNullAndIdExists_ReturnUpdatedStore() {
        StoreEntity storeEntity = mock(StoreEntity.class);
        UpdateStoreInput store = mock(UpdateStoreInput.class);

        Mockito.when(_mapper.updateStoreInputToStoreEntity(any(UpdateStoreInput.class))).thenReturn(storeEntity);
        Mockito.when(_storeManager.update(any(StoreEntity.class))).thenReturn(storeEntity);
        Assertions
            .assertThat(_appService.update(ID, store))
            .isEqualTo(_mapper.storeEntityToUpdateStoreOutput(storeEntity));
    }

    @Test
    public void deleteStore_StoreIsNotNullAndStoreExists_StoreRemoved() {
        StoreEntity storeEntity = mock(StoreEntity.class);
        Mockito.when(_storeManager.findById(any(Integer.class))).thenReturn(storeEntity);

        _appService.delete(ID);
        verify(_storeManager).delete(storeEntity);
    }

    @Test
    public void find_ListIsEmpty_ReturnList() throws Exception {
        List<StoreEntity> list = new ArrayList<>();
        Page<StoreEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindStoreByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_storeManager.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void find_ListIsNotEmpty_ReturnList() throws Exception {
        List<StoreEntity> list = new ArrayList<>();
        StoreEntity store = mock(StoreEntity.class);
        list.add(store);
        Page<StoreEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindStoreByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");
        output.add(_mapper.storeEntityToFindStoreByIdOutput(store));

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_storeManager.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
        QStoreEntity store = QStoreEntity.storeEntity;
        SearchFields searchFields = new SearchFields();
        searchFields.setOperator("equals");
        searchFields.setSearchValue("xyz");
        Map<String, SearchFields> map = new HashMap<>();
        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("xyz", String.valueOf(ID));
        BooleanBuilder builder = new BooleanBuilder();
        Assertions.assertThat(_appService.searchKeyValuePair(store, map, searchMap)).isEqualTo(builder);
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
        QStoreEntity store = QStoreEntity.storeEntity;
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
            .searchKeyValuePair(any(QStoreEntity.class), any(HashMap.class), any(HashMap.class));

        Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
    }

    @Test
    public void search_StringIsNull_ReturnNull() throws Exception {
        Assertions.assertThat(_appService.search(null)).isEqualTo(null);
    }

    //Address
    @Test
    public void GetAddress_IfStoreIdAndAddressIdIsNotNullAndStoreExists_ReturnAddress() {
        StoreEntity storeEntity = mock(StoreEntity.class);
        AddressEntity address = mock(AddressEntity.class);

        Mockito.when(_storeManager.findById(any(Integer.class))).thenReturn(storeEntity);
        Mockito.when(_storeManager.getAddress(any(Integer.class))).thenReturn(address);
        Assertions
            .assertThat(_appService.getAddress(ID))
            .isEqualTo(_mapper.addressEntityToGetAddressOutput(address, storeEntity));
    }

    @Test
    public void GetAddress_IfStoreIdAndAddressIdIsNotNullAndStoreDoesNotExist_ReturnNull() {
        Mockito.when(_storeManager.findById(any(Integer.class))).thenReturn(null);
        Assertions.assertThat(_appService.getAddress(ID)).isEqualTo(null);
    }

    //Staff
    @Test
    public void GetStaff_IfStoreIdAndStaffIdIsNotNullAndStoreExists_ReturnStaff() {
        StoreEntity storeEntity = mock(StoreEntity.class);
        StaffEntity staff = mock(StaffEntity.class);

        Mockito.when(_storeManager.findById(any(Integer.class))).thenReturn(storeEntity);
        Mockito.when(_storeManager.getStaff(any(Integer.class))).thenReturn(staff);
        Assertions
            .assertThat(_appService.getStaff(ID))
            .isEqualTo(_mapper.staffEntityToGetStaffOutput(staff, storeEntity));
    }

    @Test
    public void GetStaff_IfStoreIdAndStaffIdIsNotNullAndStoreDoesNotExist_ReturnNull() {
        Mockito.when(_storeManager.findById(any(Integer.class))).thenReturn(null);
        Assertions.assertThat(_appService.getStaff(ID)).isEqualTo(null);
    }
}
