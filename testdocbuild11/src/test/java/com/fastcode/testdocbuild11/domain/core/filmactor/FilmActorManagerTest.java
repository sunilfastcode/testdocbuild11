package com.fastcode.testdocbuild11.domain.core.filmactor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.fastcode.testdocbuild11.domain.core.actor.ActorEntity;
import com.fastcode.testdocbuild11.domain.core.actor.IActorRepository;
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
public class FilmActorManagerTest {

    @InjectMocks
    protected FilmActorManager _filmActorManager;

    @Mock
    protected IFilmActorRepository _filmActorRepository;

    @Mock
    protected IActorRepository _actorRepository;

    @Mock
    protected IFilmRepository _filmRepository;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    @Mock
    protected FilmActorId filmActorId;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_filmActorManager);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findFilmActorById_IdIsNotNullAndIdExists_ReturnFilmActor() {
        FilmActorEntity filmActor = mock(FilmActorEntity.class);

        Optional<FilmActorEntity> dbFilmActor = Optional.of((FilmActorEntity) filmActor);
        Mockito.when(_filmActorRepository.findById(any(FilmActorId.class))).thenReturn(dbFilmActor);
        Assertions.assertThat(_filmActorManager.findById(filmActorId)).isEqualTo(filmActor);
    }

    @Test
    public void findFilmActorById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Mockito
            .<Optional<FilmActorEntity>>when(_filmActorRepository.findById(any(FilmActorId.class)))
            .thenReturn(Optional.empty());
        Assertions.assertThat(_filmActorManager.findById(filmActorId)).isEqualTo(null);
    }

    @Test
    public void createFilmActor_FilmActorIsNotNullAndFilmActorDoesNotExist_StoreFilmActor() {
        FilmActorEntity filmActor = mock(FilmActorEntity.class);
        Mockito.when(_filmActorRepository.save(any(FilmActorEntity.class))).thenReturn(filmActor);
        Assertions.assertThat(_filmActorManager.create(filmActor)).isEqualTo(filmActor);
    }

    @Test
    public void deleteFilmActor_FilmActorExists_RemoveFilmActor() {
        FilmActorEntity filmActor = mock(FilmActorEntity.class);
        _filmActorManager.delete(filmActor);
        verify(_filmActorRepository).delete(filmActor);
    }

    @Test
    public void updateFilmActor_FilmActorIsNotNullAndFilmActorExists_UpdateFilmActor() {
        FilmActorEntity filmActor = mock(FilmActorEntity.class);
        Mockito.when(_filmActorRepository.save(any(FilmActorEntity.class))).thenReturn(filmActor);
        Assertions.assertThat(_filmActorManager.update(filmActor)).isEqualTo(filmActor);
    }

    @Test
    public void findAll_PageableIsNotNull_ReturnPage() {
        Page<FilmActorEntity> filmActor = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        Predicate predicate = mock(Predicate.class);

        Mockito.when(_filmActorRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(filmActor);
        Assertions.assertThat(_filmActorManager.findAll(predicate, pageable)).isEqualTo(filmActor);
    }

    //Actor
    @Test
    public void getActor_if_FilmActorIdIsNotNull_returnActor() {
        FilmActorEntity filmActorEntity = mock(FilmActorEntity.class);
        ActorEntity actor = mock(ActorEntity.class);

        Optional<FilmActorEntity> dbFilmActor = Optional.of((FilmActorEntity) filmActorEntity);
        Mockito
            .<Optional<FilmActorEntity>>when(_filmActorRepository.findById(any(FilmActorId.class)))
            .thenReturn(dbFilmActor);
        Mockito.when(filmActorEntity.getActor()).thenReturn(actor);
        Assertions.assertThat(_filmActorManager.getActor(filmActorId)).isEqualTo(actor);
    }

    //Film
    @Test
    public void getFilm_if_FilmActorIdIsNotNull_returnFilm() {
        FilmActorEntity filmActorEntity = mock(FilmActorEntity.class);
        FilmEntity film = mock(FilmEntity.class);

        Optional<FilmActorEntity> dbFilmActor = Optional.of((FilmActorEntity) filmActorEntity);
        Mockito
            .<Optional<FilmActorEntity>>when(_filmActorRepository.findById(any(FilmActorId.class)))
            .thenReturn(dbFilmActor);
        Mockito.when(filmActorEntity.getFilm()).thenReturn(film);
        Assertions.assertThat(_filmActorManager.getFilm(filmActorId)).isEqualTo(film);
    }
}
