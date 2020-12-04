package com.fastcode.testdocbuild11.domain.core.inventory;

import com.fastcode.testdocbuild11.domain.core.film.FilmEntity;
import com.querydsl.core.types.Predicate;
import java.time.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IInventoryManager {
    InventoryEntity create(InventoryEntity inventory);

    void delete(InventoryEntity inventory);

    InventoryEntity update(InventoryEntity inventory);

    InventoryEntity findById(Integer id);

    Page<InventoryEntity> findAll(Predicate predicate, Pageable pageable);

    FilmEntity getFilm(Integer inventoryId);
}
