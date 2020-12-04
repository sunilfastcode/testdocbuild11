package com.fastcode.testdocbuild11.domain.core.category;

import com.fastcode.testdocbuild11.domain.core.filmcategory.IFilmCategoryRepository;
import com.querydsl.core.types.Predicate;
import java.time.*;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component("categoryManager")
@RequiredArgsConstructor
public class CategoryManager implements ICategoryManager {

    @Qualifier("categoryRepository")
    @NonNull
    protected final ICategoryRepository _categoryRepository;

    @Qualifier("filmCategoryRepository")
    @NonNull
    protected final IFilmCategoryRepository _filmcategoryRepository;

    public CategoryEntity create(CategoryEntity category) {
        return _categoryRepository.save(category);
    }

    public void delete(CategoryEntity category) {
        _categoryRepository.delete(category);
    }

    public CategoryEntity update(CategoryEntity category) {
        return _categoryRepository.save(category);
    }

    public CategoryEntity findById(Integer categoryId) {
        Optional<CategoryEntity> dbCategory = _categoryRepository.findById(categoryId);
        return dbCategory.orElse(null);
    }

    public Page<CategoryEntity> findAll(Predicate predicate, Pageable pageable) {
        return _categoryRepository.findAll(predicate, pageable);
    }
}
