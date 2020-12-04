package com.fastcode.testdocbuild11.application.core.inventory;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.testdocbuild11.application.core.inventory.dto.*;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.fastcode.testdocbuild11.domain.core.film.FilmEntity;
import com.fastcode.testdocbuild11.domain.core.film.IFilmManager;
import com.fastcode.testdocbuild11.domain.core.inventory.*;
import com.fastcode.testdocbuild11.domain.core.inventory.InventoryEntity;
import com.fastcode.testdocbuild11.domain.core.inventory.QInventoryEntity;
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
public class InventoryAppServiceTest {

    @InjectMocks
    @Spy
    protected InventoryAppService _appService;

    @Mock
    protected IInventoryManager _inventoryManager;

    @Mock
    protected IFilmManager _filmManager;

    @Mock
    protected IInventoryMapper _mapper;

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
    public void findInventoryById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Mockito.when(_inventoryManager.findById(any(Integer.class))).thenReturn(null);
        Assertions.assertThat(_appService.findById(ID)).isEqualTo(null);
    }

    @Test
    public void findInventoryById_IdIsNotNullAndIdExists_ReturnInventory() {
        InventoryEntity inventory = mock(InventoryEntity.class);
        Mockito.when(_inventoryManager.findById(any(Integer.class))).thenReturn(inventory);

        Assertions
            .assertThat(_appService.findById(ID))
            .isEqualTo(_mapper.inventoryEntityToFindInventoryByIdOutput(inventory));
    }

    @Test
    public void createInventory_InventoryIsNotNullAndInventoryDoesNotExist_StoreInventory() {
        InventoryEntity inventoryEntity = mock(InventoryEntity.class);
        CreateInventoryInput inventoryInput = new CreateInventoryInput();

        FilmEntity film = mock(FilmEntity.class);
        inventoryInput.setFilmId((short) 15);

        Mockito.when(_filmManager.findById(any(Integer.class))).thenReturn(film);

        Mockito
            .when(_mapper.createInventoryInputToInventoryEntity(any(CreateInventoryInput.class)))
            .thenReturn(inventoryEntity);
        Mockito.when(_inventoryManager.create(any(InventoryEntity.class))).thenReturn(inventoryEntity);

        Assertions
            .assertThat(_appService.create(inventoryInput))
            .isEqualTo(_mapper.inventoryEntityToCreateInventoryOutput(inventoryEntity));
    }

    @Test
    public void createInventory_InventoryIsNotNullAndInventoryDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {
        CreateInventoryInput inventory = mock(CreateInventoryInput.class);

        Mockito.when(_mapper.createInventoryInputToInventoryEntity(any(CreateInventoryInput.class))).thenReturn(null);
        Assertions.assertThat(_appService.create(inventory)).isEqualTo(null);
    }

    @Test
    public void createInventory_InventoryIsNotNullAndInventoryDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {
        CreateInventoryInput inventory = new CreateInventoryInput();

        inventory.setFilmId((short) 15);

        Mockito.when(_filmManager.findById(any(Integer.class))).thenReturn(null);
        Assertions.assertThat(_appService.create(inventory)).isEqualTo(null);
    }

    @Test
    public void updateInventory_InventoryIsNotNullAndInventoryDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {
        UpdateInventoryInput inventory = mock(UpdateInventoryInput.class);
        InventoryEntity inventoryEntity = mock(InventoryEntity.class);

        Mockito
            .when(_mapper.updateInventoryInputToInventoryEntity(any(UpdateInventoryInput.class)))
            .thenReturn(inventoryEntity);
        Assertions.assertThat(_appService.update(ID, inventory)).isEqualTo(null);
    }

    @Test
    public void updateInventory_InventoryIsNotNullAndInventoryDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {
        UpdateInventoryInput inventory = new UpdateInventoryInput();
        inventory.setFilmId((short) 15);

        Mockito.when(_filmManager.findById(any(Integer.class))).thenReturn(null);
        Assertions.assertThat(_appService.update(ID, inventory)).isEqualTo(null);
    }

    @Test
    public void updateInventory_InventoryIdIsNotNullAndIdExists_ReturnUpdatedInventory() {
        InventoryEntity inventoryEntity = mock(InventoryEntity.class);
        UpdateInventoryInput inventory = mock(UpdateInventoryInput.class);

        Mockito
            .when(_mapper.updateInventoryInputToInventoryEntity(any(UpdateInventoryInput.class)))
            .thenReturn(inventoryEntity);
        Mockito.when(_inventoryManager.update(any(InventoryEntity.class))).thenReturn(inventoryEntity);
        Assertions
            .assertThat(_appService.update(ID, inventory))
            .isEqualTo(_mapper.inventoryEntityToUpdateInventoryOutput(inventoryEntity));
    }

    @Test
    public void deleteInventory_InventoryIsNotNullAndInventoryExists_InventoryRemoved() {
        InventoryEntity inventoryEntity = mock(InventoryEntity.class);
        Mockito.when(_inventoryManager.findById(any(Integer.class))).thenReturn(inventoryEntity);

        _appService.delete(ID);
        verify(_inventoryManager).delete(inventoryEntity);
    }

    @Test
    public void find_ListIsEmpty_ReturnList() throws Exception {
        List<InventoryEntity> list = new ArrayList<>();
        Page<InventoryEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindInventoryByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_inventoryManager.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void find_ListIsNotEmpty_ReturnList() throws Exception {
        List<InventoryEntity> list = new ArrayList<>();
        InventoryEntity inventory = mock(InventoryEntity.class);
        list.add(inventory);
        Page<InventoryEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindInventoryByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");
        output.add(_mapper.inventoryEntityToFindInventoryByIdOutput(inventory));

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_inventoryManager.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
        QInventoryEntity inventory = QInventoryEntity.inventoryEntity;
        SearchFields searchFields = new SearchFields();
        searchFields.setOperator("equals");
        searchFields.setSearchValue("xyz");
        Map<String, SearchFields> map = new HashMap<>();
        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("xyz", String.valueOf(ID));
        BooleanBuilder builder = new BooleanBuilder();
        Assertions.assertThat(_appService.searchKeyValuePair(inventory, map, searchMap)).isEqualTo(builder);
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
        QInventoryEntity inventory = QInventoryEntity.inventoryEntity;
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
            .searchKeyValuePair(any(QInventoryEntity.class), any(HashMap.class), any(HashMap.class));

        Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
    }

    @Test
    public void search_StringIsNull_ReturnNull() throws Exception {
        Assertions.assertThat(_appService.search(null)).isEqualTo(null);
    }

    //Film
    @Test
    public void GetFilm_IfInventoryIdAndFilmIdIsNotNullAndInventoryExists_ReturnFilm() {
        InventoryEntity inventoryEntity = mock(InventoryEntity.class);
        FilmEntity film = mock(FilmEntity.class);

        Mockito.when(_inventoryManager.findById(any(Integer.class))).thenReturn(inventoryEntity);
        Mockito.when(_inventoryManager.getFilm(any(Integer.class))).thenReturn(film);
        Assertions
            .assertThat(_appService.getFilm(ID))
            .isEqualTo(_mapper.filmEntityToGetFilmOutput(film, inventoryEntity));
    }

    @Test
    public void GetFilm_IfInventoryIdAndFilmIdIsNotNullAndInventoryDoesNotExist_ReturnNull() {
        Mockito.when(_inventoryManager.findById(any(Integer.class))).thenReturn(null);
        Assertions.assertThat(_appService.getFilm(ID)).isEqualTo(null);
    }

    @Test
    public void ParserentalsJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        String keyString = "15";
        joinColumnMap.put("inventoryId", keyString);
        Assertions.assertThat(_appService.parseRentalsJoinColumn(keyString)).isEqualTo(joinColumnMap);
    }
}
