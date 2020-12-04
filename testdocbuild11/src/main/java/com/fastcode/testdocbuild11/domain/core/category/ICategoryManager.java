package com.fastcode.testdocbuild11.domain.core.category;

import com.querydsl.core.types.Predicate;
import java.time.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICategoryManager {
    CategoryEntity create(CategoryEntity category);

    void delete(CategoryEntity category);

    CategoryEntity update(CategoryEntity category);

    CategoryEntity findById(Integer id);

    Page<CategoryEntity> findAll(Predicate predicate, Pageable pageable);
}
