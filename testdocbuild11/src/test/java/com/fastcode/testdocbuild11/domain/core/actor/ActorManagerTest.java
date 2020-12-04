package com.fastcode.testdocbuild11.domain.core.actor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.fastcode.testdocbuild11.domain.core.filmactor.IFilmActorRepository;
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
public class ActorManagerTest {

    @InjectMocks
    protected ActorManager _actorManager;

    @Mock
    protected IActorRepository _actorRepository;

    @Mock
    protected IFilmActorRepository _filmactorRepository;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    protected static Integer ID = 15;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_actorManager);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findActorById_IdIsNotNullAndIdExists_ReturnActor() {
        ActorEntity actor = mock(ActorEntity.class);

        Optional<ActorEntity> dbActor = Optional.of((ActorEntity) actor);
        Mockito.when(_actorRepository.findById(any(Integer.class))).thenReturn(dbActor);
        Assertions.assertThat(_actorManager.findById(ID)).isEqualTo(actor);
    }

    @Test
    public void findActorById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Mockito.<Optional<ActorEntity>>when(_actorRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        Assertions.assertThat(_actorManager.findById(ID)).isEqualTo(null);
    }

    @Test
    public void createActor_ActorIsNotNullAndActorDoesNotExist_StoreActor() {
        ActorEntity actor = mock(ActorEntity.class);
        Mockito.when(_actorRepository.save(any(ActorEntity.class))).thenReturn(actor);
        Assertions.assertThat(_actorManager.create(actor)).isEqualTo(actor);
    }

    @Test
    public void deleteActor_ActorExists_RemoveActor() {
        ActorEntity actor = mock(ActorEntity.class);
        _actorManager.delete(actor);
        verify(_actorRepository).delete(actor);
    }

    @Test
    public void updateActor_ActorIsNotNullAndActorExists_UpdateActor() {
        ActorEntity actor = mock(ActorEntity.class);
        Mockito.when(_actorRepository.save(any(ActorEntity.class))).thenReturn(actor);
        Assertions.assertThat(_actorManager.update(actor)).isEqualTo(actor);
    }

    @Test
    public void findAll_PageableIsNotNull_ReturnPage() {
        Page<ActorEntity> actor = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        Predicate predicate = mock(Predicate.class);

        Mockito.when(_actorRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(actor);
        Assertions.assertThat(_actorManager.findAll(predicate, pageable)).isEqualTo(actor);
    }
}
