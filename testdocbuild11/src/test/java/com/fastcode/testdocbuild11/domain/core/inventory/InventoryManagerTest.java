package com.fastcode.testdocbuild11.domain.core.inventory;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.fastcode.testdocbuild11.domain.core.film.FilmEntity;
import com.fastcode.testdocbuild11.domain.core.film.IFilmRepository;
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
public class InventoryManagerTest {

    @InjectMocks
    protected InventoryManager _inventoryManager;

    @Mock
    protected IInventoryRepository _inventoryRepository;

    @Mock
    protected IFilmRepository _filmRepository;

    @Mock
    protected IRentalRepository _rentalRepository;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    protected static Integer ID = 15;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_inventoryManager);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findInventoryById_IdIsNotNullAndIdExists_ReturnInventory() {
        InventoryEntity inventory = mock(InventoryEntity.class);

        Optional<InventoryEntity> dbInventory = Optional.of((InventoryEntity) inventory);
        Mockito.when(_inventoryRepository.findById(any(Integer.class))).thenReturn(dbInventory);
        Assertions.assertThat(_inventoryManager.findById(ID)).isEqualTo(inventory);
    }

    @Test
    public void findInventoryById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Mockito
            .<Optional<InventoryEntity>>when(_inventoryRepository.findById(any(Integer.class)))
            .thenReturn(Optional.empty());
        Assertions.assertThat(_inventoryManager.findById(ID)).isEqualTo(null);
    }

    @Test
    public void createInventory_InventoryIsNotNullAndInventoryDoesNotExist_StoreInventory() {
        InventoryEntity inventory = mock(InventoryEntity.class);
        Mockito.when(_inventoryRepository.save(any(InventoryEntity.class))).thenReturn(inventory);
        Assertions.assertThat(_inventoryManager.create(inventory)).isEqualTo(inventory);
    }

    @Test
    public void deleteInventory_InventoryExists_RemoveInventory() {
        InventoryEntity inventory = mock(InventoryEntity.class);
        _inventoryManager.delete(inventory);
        verify(_inventoryRepository).delete(inventory);
    }

    @Test
    public void updateInventory_InventoryIsNotNullAndInventoryExists_UpdateInventory() {
        InventoryEntity inventory = mock(InventoryEntity.class);
        Mockito.when(_inventoryRepository.save(any(InventoryEntity.class))).thenReturn(inventory);
        Assertions.assertThat(_inventoryManager.update(inventory)).isEqualTo(inventory);
    }

    @Test
    public void findAll_PageableIsNotNull_ReturnPage() {
        Page<InventoryEntity> inventory = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        Predicate predicate = mock(Predicate.class);

        Mockito.when(_inventoryRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(inventory);
        Assertions.assertThat(_inventoryManager.findAll(predicate, pageable)).isEqualTo(inventory);
    }

    //Film
    @Test
    public void getFilm_if_InventoryIdIsNotNull_returnFilm() {
        InventoryEntity inventoryEntity = mock(InventoryEntity.class);
        FilmEntity film = mock(FilmEntity.class);

        Optional<InventoryEntity> dbInventory = Optional.of((InventoryEntity) inventoryEntity);
        Mockito
            .<Optional<InventoryEntity>>when(_inventoryRepository.findById(any(Integer.class)))
            .thenReturn(dbInventory);
        Mockito.when(inventoryEntity.getFilm()).thenReturn(film);
        Assertions.assertThat(_inventoryManager.getFilm(ID)).isEqualTo(film);
    }
}
