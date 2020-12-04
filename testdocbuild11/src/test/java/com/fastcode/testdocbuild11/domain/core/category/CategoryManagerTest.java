package com.fastcode.testdocbuild11.domain.core.category;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.fastcode.testdocbuild11.domain.core.filmcategory.IFilmCategoryRepository;
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
public class CategoryManagerTest {

    @InjectMocks
    protected CategoryManager _categoryManager;

    @Mock
    protected ICategoryRepository _categoryRepository;

    @Mock
    protected IFilmCategoryRepository _filmcategoryRepository;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    protected static Integer ID = 15;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_categoryManager);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findCategoryById_IdIsNotNullAndIdExists_ReturnCategory() {
        CategoryEntity category = mock(CategoryEntity.class);

        Optional<CategoryEntity> dbCategory = Optional.of((CategoryEntity) category);
        Mockito.when(_categoryRepository.findById(any(Integer.class))).thenReturn(dbCategory);
        Assertions.assertThat(_categoryManager.findById(ID)).isEqualTo(category);
    }

    @Test
    public void findCategoryById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Mockito
            .<Optional<CategoryEntity>>when(_categoryRepository.findById(any(Integer.class)))
            .thenReturn(Optional.empty());
        Assertions.assertThat(_categoryManager.findById(ID)).isEqualTo(null);
    }

    @Test
    public void createCategory_CategoryIsNotNullAndCategoryDoesNotExist_StoreCategory() {
        CategoryEntity category = mock(CategoryEntity.class);
        Mockito.when(_categoryRepository.save(any(CategoryEntity.class))).thenReturn(category);
        Assertions.assertThat(_categoryManager.create(category)).isEqualTo(category);
    }

    @Test
    public void deleteCategory_CategoryExists_RemoveCategory() {
        CategoryEntity category = mock(CategoryEntity.class);
        _categoryManager.delete(category);
        verify(_categoryRepository).delete(category);
    }

    @Test
    public void updateCategory_CategoryIsNotNullAndCategoryExists_UpdateCategory() {
        CategoryEntity category = mock(CategoryEntity.class);
        Mockito.when(_categoryRepository.save(any(CategoryEntity.class))).thenReturn(category);
        Assertions.assertThat(_categoryManager.update(category)).isEqualTo(category);
    }

    @Test
    public void findAll_PageableIsNotNull_ReturnPage() {
        Page<CategoryEntity> category = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        Predicate predicate = mock(Predicate.class);

        Mockito.when(_categoryRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(category);
        Assertions.assertThat(_categoryManager.findAll(predicate, pageable)).isEqualTo(category);
    }
}
