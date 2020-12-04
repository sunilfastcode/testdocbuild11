package com.fastcode.testdocbuild11.application.core.filmactor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.testdocbuild11.application.core.filmactor.dto.*;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.fastcode.testdocbuild11.domain.core.actor.ActorEntity;
import com.fastcode.testdocbuild11.domain.core.actor.IActorManager;
import com.fastcode.testdocbuild11.domain.core.film.FilmEntity;
import com.fastcode.testdocbuild11.domain.core.film.IFilmManager;
import com.fastcode.testdocbuild11.domain.core.filmactor.*;
import com.fastcode.testdocbuild11.domain.core.filmactor.FilmActorEntity;
import com.fastcode.testdocbuild11.domain.core.filmactor.FilmActorId;
import com.fastcode.testdocbuild11.domain.core.filmactor.QFilmActorEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class FilmActorAppServiceTest {

    @InjectMocks
    @Spy
    protected FilmActorAppService _appService;

    @Mock
    protected IFilmActorManager _filmActorManager;

    @Mock
    protected IActorManager _actorManager;

    @Mock
    protected IFilmManager _filmManager;

    @Mock
    protected IFilmActorMapper _mapper;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    @Mock
    protected FilmActorId filmActorId;

    private static final Long ID = 15L;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_appService);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findFilmActorById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Mockito.when(_filmActorManager.findById(any(FilmActorId.class))).thenReturn(null);
        Assertions.assertThat(_appService.findById(filmActorId)).isEqualTo(null);
    }

    @Test
    public void findFilmActorById_IdIsNotNullAndIdExists_ReturnFilmActor() {
        FilmActorEntity filmActor = mock(FilmActorEntity.class);
        Mockito.when(_filmActorManager.findById(any(FilmActorId.class))).thenReturn(filmActor);

        Assertions
            .assertThat(_appService.findById(filmActorId))
            .isEqualTo(_mapper.filmActorEntityToFindFilmActorByIdOutput(filmActor));
    }

    @Test
    public void createFilmActor_FilmActorIsNotNullAndFilmActorDoesNotExist_StoreFilmActor() {
        FilmActorEntity filmActorEntity = mock(FilmActorEntity.class);
        CreateFilmActorInput filmActorInput = new CreateFilmActorInput();

        ActorEntity actor = mock(ActorEntity.class);
        filmActorInput.setActorId((short) 15);

        Mockito.when(_actorManager.findById(any(Integer.class))).thenReturn(actor);

        FilmEntity film = mock(FilmEntity.class);
        filmActorInput.setFilmId((short) 15);

        Mockito.when(_filmManager.findById(any(Integer.class))).thenReturn(film);

        Mockito
            .when(_mapper.createFilmActorInputToFilmActorEntity(any(CreateFilmActorInput.class)))
            .thenReturn(filmActorEntity);
        Mockito.when(_filmActorManager.create(any(FilmActorEntity.class))).thenReturn(filmActorEntity);

        Assertions
            .assertThat(_appService.create(filmActorInput))
            .isEqualTo(_mapper.filmActorEntityToCreateFilmActorOutput(filmActorEntity));
    }

    @Test
    public void createFilmActor_FilmActorIsNotNullAndFilmActorDoesNotExistAndChildIsNullAndChildIsNotMandatory_StoreFilmActor() {
        FilmActorEntity filmActorEntity = mock(FilmActorEntity.class);
        CreateFilmActorInput filmActor = mock(CreateFilmActorInput.class);

        Mockito
            .when(_mapper.createFilmActorInputToFilmActorEntity(any(CreateFilmActorInput.class)))
            .thenReturn(filmActorEntity);
        Mockito.when(_filmActorManager.create(any(FilmActorEntity.class))).thenReturn(filmActorEntity);
        Assertions
            .assertThat(_appService.create(filmActor))
            .isEqualTo(_mapper.filmActorEntityToCreateFilmActorOutput(filmActorEntity));
    }

    @Test
    public void updateFilmActor_FilmActorIsNotNullAndFilmActorDoesNotExistAndChildIsNullAndChildIsNotMandatory_ReturnUpdatedFilmActor() {
        FilmActorEntity filmActorEntity = mock(FilmActorEntity.class);
        UpdateFilmActorInput filmActor = mock(UpdateFilmActorInput.class);

        Mockito
            .when(_mapper.updateFilmActorInputToFilmActorEntity(any(UpdateFilmActorInput.class)))
            .thenReturn(filmActorEntity);
        Mockito.when(_filmActorManager.update(any(FilmActorEntity.class))).thenReturn(filmActorEntity);
        Assertions
            .assertThat(_appService.update(filmActorId, filmActor))
            .isEqualTo(_mapper.filmActorEntityToUpdateFilmActorOutput(filmActorEntity));
    }

    @Test
    public void updateFilmActor_FilmActorIdIsNotNullAndIdExists_ReturnUpdatedFilmActor() {
        FilmActorEntity filmActorEntity = mock(FilmActorEntity.class);
        UpdateFilmActorInput filmActor = mock(UpdateFilmActorInput.class);

        Mockito
            .when(_mapper.updateFilmActorInputToFilmActorEntity(any(UpdateFilmActorInput.class)))
            .thenReturn(filmActorEntity);
        Mockito.when(_filmActorManager.update(any(FilmActorEntity.class))).thenReturn(filmActorEntity);
        Assertions
            .assertThat(_appService.update(filmActorId, filmActor))
            .isEqualTo(_mapper.filmActorEntityToUpdateFilmActorOutput(filmActorEntity));
    }

    @Test
    public void deleteFilmActor_FilmActorIsNotNullAndFilmActorExists_FilmActorRemoved() {
        FilmActorEntity filmActorEntity = mock(FilmActorEntity.class);
        Mockito.when(_filmActorManager.findById(any(FilmActorId.class))).thenReturn(filmActorEntity);

        _appService.delete(filmActorId);
        verify(_filmActorManager).delete(filmActorEntity);
    }

    @Test
    public void find_ListIsEmpty_ReturnList() throws Exception {
        List<FilmActorEntity> list = new ArrayList<>();
        Page<FilmActorEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindFilmActorByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_filmActorManager.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void find_ListIsNotEmpty_ReturnList() throws Exception {
        List<FilmActorEntity> list = new ArrayList<>();
        FilmActorEntity filmActor = mock(FilmActorEntity.class);
        list.add(filmActor);
        Page<FilmActorEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindFilmActorByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");
        output.add(_mapper.filmActorEntityToFindFilmActorByIdOutput(filmActor));

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_filmActorManager.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
        QFilmActorEntity filmActor = QFilmActorEntity.filmActorEntity;
        SearchFields searchFields = new SearchFields();
        searchFields.setOperator("equals");
        searchFields.setSearchValue("xyz");
        Map<String, SearchFields> map = new HashMap<>();
        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("xyz", String.valueOf(ID));
        BooleanBuilder builder = new BooleanBuilder();
        Assertions.assertThat(_appService.searchKeyValuePair(filmActor, map, searchMap)).isEqualTo(builder);
    }

    @Test(expected = Exception.class)
    public void checkProperties_PropertyDoesNotExist_ThrowException() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("xyz");
        _appService.checkProperties(list);
    }

    @Test
    public void checkProperties_PropertyExists_ReturnNothing() throws Exception {
        List<String> list = new ArrayList<>();
        _appService.checkProperties(list);
    }

    @Test
    public void search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
        Map<String, SearchFields> map = new HashMap<>();
        QFilmActorEntity filmActor = QFilmActorEntity.filmActorEntity;
        List<SearchFields> fieldsList = new ArrayList<>();
        SearchFields fields = new SearchFields();
        SearchCriteria search = new SearchCriteria();
        search.setType(3);
        search.setValue("xyz");
        search.setOperator("equals");
        fields.setOperator("equals");
        fields.setSearchValue("xyz");
        fieldsList.add(fields);
        search.setFields(fieldsList);
        BooleanBuilder builder = new BooleanBuilder();
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
        Mockito
            .doReturn(builder)
            .when(_appService)
            .searchKeyValuePair(any(QFilmActorEntity.class), any(HashMap.class), any(HashMap.class));

        Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
    }

    @Test
    public void search_StringIsNull_ReturnNull() throws Exception {
        Assertions.assertThat(_appService.search(null)).isEqualTo(null);
    }

    //Actor
    @Test
    public void GetActor_IfFilmActorIdAndActorIdIsNotNullAndFilmActorExists_ReturnActor() {
        FilmActorEntity filmActorEntity = mock(FilmActorEntity.class);
        ActorEntity actor = mock(ActorEntity.class);

        Mockito.when(_filmActorManager.findById(any(FilmActorId.class))).thenReturn(filmActorEntity);
        Mockito.when(_filmActorManager.getActor(any(FilmActorId.class))).thenReturn(actor);
        Assertions
            .assertThat(_appService.getActor(filmActorId))
            .isEqualTo(_mapper.actorEntityToGetActorOutput(actor, filmActorEntity));
    }

    @Test
    public void GetActor_IfFilmActorIdAndActorIdIsNotNullAndFilmActorDoesNotExist_ReturnNull() {
        Mockito.when(_filmActorManager.findById(any(FilmActorId.class))).thenReturn(null);
        Assertions.assertThat(_appService.getActor(filmActorId)).isEqualTo(null);
    }

    //Film
    @Test
    public void GetFilm_IfFilmActorIdAndFilmIdIsNotNullAndFilmActorExists_ReturnFilm() {
        FilmActorEntity filmActorEntity = mock(FilmActorEntity.class);
        FilmEntity film = mock(FilmEntity.class);

        Mockito.when(_filmActorManager.findById(any(FilmActorId.class))).thenReturn(filmActorEntity);
        Mockito.when(_filmActorManager.getFilm(any(FilmActorId.class))).thenReturn(film);
        Assertions
            .assertThat(_appService.getFilm(filmActorId))
            .isEqualTo(_mapper.filmEntityToGetFilmOutput(film, filmActorEntity));
    }

    @Test
    public void GetFilm_IfFilmActorIdAndFilmIdIsNotNullAndFilmActorDoesNotExist_ReturnNull() {
        Mockito.when(_filmActorManager.findById(any(FilmActorId.class))).thenReturn(null);
        Assertions.assertThat(_appService.getFilm(filmActorId)).isEqualTo(null);
    }

    @Test
    public void ParseFilmActorKey_KeysStringIsNotEmptyAndKeyValuePairExists_ReturnFilmActorId() {
        String keyString = "actorId=15,filmId=15";

        FilmActorId filmActorId = new FilmActorId();
        filmActorId.setActorId((short) 15);
        filmActorId.setFilmId((short) 15);

        Assertions.assertThat(_appService.parseFilmActorKey(keyString)).isEqualToComparingFieldByField(filmActorId);
    }

    @Test
    public void ParseFilmActorKey_KeysStringIsEmpty_ReturnNull() {
        String keyString = "";
        Assertions.assertThat(_appService.parseFilmActorKey(keyString)).isEqualTo(null);
    }

    @Test
    public void ParseFilmActorKey_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        String keyString = "actorId";

        Assertions.assertThat(_appService.parseFilmActorKey(keyString)).isEqualTo(null);
    }
}
