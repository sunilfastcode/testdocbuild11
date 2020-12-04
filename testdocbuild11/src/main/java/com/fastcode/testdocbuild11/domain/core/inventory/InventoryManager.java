package com.fastcode.testdocbuild11.domain.core.inventory;

import com.fastcode.testdocbuild11.domain.core.film.FilmEntity;
import com.fastcode.testdocbuild11.domain.core.film.IFilmRepository;
import com.fastcode.testdocbuild11.domain.core.rental.IRentalRepository;
import com.querydsl.core.types.Predicate;
import java.time.*;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component("inventoryManager")
@RequiredArgsConstructor
public class InventoryManager implements IInventoryManager {

    @Qualifier("inventoryRepository")
    @NonNull
    protected final IInventoryRepository _inventoryRepository;

    @Qualifier("filmRepository")
    @NonNull
    protected final IFilmRepository _filmRepository;

    @Qualifier("rentalRepository")
    @NonNull
    protected final IRentalRepository _rentalRepository;

    public InventoryEntity create(InventoryEntity inventory) {
        return _inventoryRepository.save(inventory);
    }

    public void delete(InventoryEntity inventory) {
        _inventoryRepository.delete(inventory);
    }

    public InventoryEntity update(InventoryEntity inventory) {
        return _inventoryRepository.save(inventory);
    }

    public InventoryEntity findById(Integer inventoryId) {
        Optional<InventoryEntity> dbInventory = _inventoryRepository.findById(inventoryId);
        return dbInventory.orElse(null);
    }

    public Page<InventoryEntity> findAll(Predicate predicate, Pageable pageable) {
        return _inventoryRepository.findAll(predicate, pageable);
    }

    public FilmEntity getFilm(Integer inventoryId) {
        Optional<InventoryEntity> dbInventory = _inventoryRepository.findById(inventoryId);
        if (dbInventory.isPresent()) {
            InventoryEntity existingInventory = dbInventory.get();
            return existingInventory.getFilm();
        } else {
            return null;
        }
    }
}
