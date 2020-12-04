package com.fastcode.testdocbuild11.domain.core.filmcategory;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.fastcode.testdocbuild11.domain.core.category.CategoryEntity;
import com.fastcode.testdocbuild11.domain.core.category.ICategoryRepository;
import com.fastcode.testdocbuild11.domain.core.film.FilmEntity;
import com.fastcode.testdocbuild11.domain.core.film.IFilmRepository;
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
public class FilmCategoryManagerTest {

    @InjectMocks
    protected FilmCategoryManager _filmCategoryManager;

    @Mock
    protected IFilmCategoryRepository _filmCategoryRepository;

    @Mock
    protected ICategoryRepository _categoryRepository;

    @Mock
    protected IFilmRepository _filmRepository;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    @Mock
    protected FilmCategoryId filmCategoryId;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_filmCategoryManager);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findFilmCategoryById_IdIsNotNullAndIdExists_ReturnFilmCategory() {
        FilmCategoryEntity filmCategory = mock(FilmCategoryEntity.class);

        Optional<FilmCategoryEntity> dbFilmCategory = Optional.of((FilmCategoryEntity) filmCategory);
        Mockito.when(_filmCategoryRepository.findById(any(FilmCategoryId.class))).thenReturn(dbFilmCategory);
        Assertions.assertThat(_filmCategoryManager.findById(filmCategoryId)).isEqualTo(filmCategory);
    }

    @Test
    public void findFilmCategoryById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Mockito
            .<Optional<FilmCategoryEntity>>when(_filmCategoryRepository.findById(any(FilmCategoryId.class)))
            .thenReturn(Optional.empty());
        Assertions.assertThat(_filmCategoryManager.findById(filmCategoryId)).isEqualTo(null);
    }

    @Test
    public void createFilmCategory_FilmCategoryIsNotNullAndFilmCategoryDoesNotExist_StoreFilmCategory() {
        FilmCategoryEntity filmCategory = mock(FilmCategoryEntity.class);
        Mockito.when(_filmCategoryRepository.save(any(FilmCategoryEntity.class))).thenReturn(filmCategory);
        Assertions.assertThat(_filmCategoryManager.create(filmCategory)).isEqualTo(filmCategory);
    }

    @Test
    public void deleteFilmCategory_FilmCategoryExists_RemoveFilmCategory() {
        FilmCategoryEntity filmCategory = mock(FilmCategoryEntity.class);
        _filmCategoryManager.delete(filmCategory);
        verify(_filmCategoryRepository).delete(filmCategory);
    }

    @Test
    public void updateFilmCategory_FilmCategoryIsNotNullAndFilmCategoryExists_UpdateFilmCategory() {
        FilmCategoryEntity filmCategory = mock(FilmCategoryEntity.class);
        Mockito.when(_filmCategoryRepository.save(any(FilmCategoryEntity.class))).thenReturn(filmCategory);
        Assertions.assertThat(_filmCategoryManager.update(filmCategory)).isEqualTo(filmCategory);
    }

    @Test
    public void findAll_PageableIsNotNull_ReturnPage() {
        Page<FilmCategoryEntity> filmCategory = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        Predicate predicate = mock(Predicate.class);

        Mockito
            .when(_filmCategoryRepository.findAll(any(Predicate.class), any(Pageable.class)))
            .thenReturn(filmCategory);
        Assertions.assertThat(_filmCategoryManager.findAll(predicate, pageable)).isEqualTo(filmCategory);
    }

    //Category
    @Test
    public void getCategory_if_FilmCategoryIdIsNotNull_returnCategory() {
        FilmCategoryEntity filmCategoryEntity = mock(FilmCategoryEntity.class);
        CategoryEntity category = mock(CategoryEntity.class);

        Optional<FilmCategoryEntity> dbFilmCategory = Optional.of((FilmCategoryEntity) filmCategoryEntity);
        Mockito
            .<Optional<FilmCategoryEntity>>when(_filmCategoryRepository.findById(any(FilmCategoryId.class)))
            .thenReturn(dbFilmCategory);
        Mockito.when(filmCategoryEntity.getCategory()).thenReturn(category);
        Assertions.assertThat(_filmCategoryManager.getCategory(filmCategoryId)).isEqualTo(category);
    }

    //Film
    @Test
    public void getFilm_if_FilmCategoryIdIsNotNull_returnFilm() {
        FilmCategoryEntity filmCategoryEntity = mock(FilmCategoryEntity.class);
        FilmEntity film = mock(FilmEntity.class);

        Optional<FilmCategoryEntity> dbFilmCategory = Optional.of((FilmCategoryEntity) filmCategoryEntity);
        Mockito
            .<Optional<FilmCategoryEntity>>when(_filmCategoryRepository.findById(any(FilmCategoryId.class)))
            .thenReturn(dbFilmCategory);
        Mockito.when(filmCategoryEntity.getFilm()).thenReturn(film);
        Assertions.assertThat(_filmCategoryManager.getFilm(filmCategoryId)).isEqualTo(film);
    }
}
