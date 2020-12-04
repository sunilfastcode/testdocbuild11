package com.fastcode.testdocbuild11.domain.core.film;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.fastcode.testdocbuild11.domain.core.filmactor.IFilmActorRepository;
import com.fastcode.testdocbuild11.domain.core.filmcategory.IFilmCategoryRepository;
import com.fastcode.testdocbuild11.domain.core.inventory.IInventoryRepository;
import com.fastcode.testdocbuild11.domain.core.language.ILanguageRepository;
import com.fastcode.testdocbuild11.domain.core.language.LanguageEntity;
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
public class FilmManagerTest {

    @InjectMocks
    protected FilmManager _filmManager;

    @Mock
    protected IFilmRepository _filmRepository;

    @Mock
    protected IFilmActorRepository _filmactorRepository;

    @Mock
    protected IFilmCategoryRepository _filmcategoryRepository;

    @Mock
    protected IInventoryRepository _inventoryRepository;

    @Mock
    protected ILanguageRepository _languageRepository;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    protected static Integer ID = 15;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_filmManager);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findFilmById_IdIsNotNullAndIdExists_ReturnFilm() {
        FilmEntity film = mock(FilmEntity.class);

        Optional<FilmEntity> dbFilm = Optional.of((FilmEntity) film);
        Mockito.when(_filmRepository.findById(any(Integer.class))).thenReturn(dbFilm);
        Assertions.assertThat(_filmManager.findById(ID)).isEqualTo(film);
    }

    @Test
    public void findFilmById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Mockito.<Optional<FilmEntity>>when(_filmRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        Assertions.assertThat(_filmManager.findById(ID)).isEqualTo(null);
    }

    @Test
    public void createFilm_FilmIsNotNullAndFilmDoesNotExist_StoreFilm() {
        FilmEntity film = mock(FilmEntity.class);
        Mockito.when(_filmRepository.save(any(FilmEntity.class))).thenReturn(film);
        Assertions.assertThat(_filmManager.create(film)).isEqualTo(film);
    }

    @Test
    public void deleteFilm_FilmExists_RemoveFilm() {
        FilmEntity film = mock(FilmEntity.class);
        _filmManager.delete(film);
        verify(_filmRepository).delete(film);
    }

    @Test
    public void updateFilm_FilmIsNotNullAndFilmExists_UpdateFilm() {
        FilmEntity film = mock(FilmEntity.class);
        Mockito.when(_filmRepository.save(any(FilmEntity.class))).thenReturn(film);
        Assertions.assertThat(_filmManager.update(film)).isEqualTo(film);
    }

    @Test
    public void findAll_PageableIsNotNull_ReturnPage() {
        Page<FilmEntity> film = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        Predicate predicate = mock(Predicate.class);

        Mockito.when(_filmRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(film);
        Assertions.assertThat(_filmManager.findAll(predicate, pageable)).isEqualTo(film);
    }

    //Language
    @Test
    public void getLanguage_if_FilmIdIsNotNull_returnLanguage() {
        FilmEntity filmEntity = mock(FilmEntity.class);
        LanguageEntity language = mock(LanguageEntity.class);

        Optional<FilmEntity> dbFilm = Optional.of((FilmEntity) filmEntity);
        Mockito.<Optional<FilmEntity>>when(_filmRepository.findById(any(Integer.class))).thenReturn(dbFilm);
        Mockito.when(filmEntity.getLanguage()).thenReturn(language);
        Assertions.assertThat(_filmManager.getLanguage(ID)).isEqualTo(language);
    }
}
