package com.fastcode.testdocbuild11.application.core.actor;

import com.fastcode.testdocbuild11.application.core.actor.dto.*;
import com.fastcode.testdocbuild11.commons.search.SearchCriteria;
import java.util.*;
import org.springframework.data.domain.Pageable;

public interface IActorAppService {
    //CRUD Operations

    CreateActorOutput create(CreateActorInput actor);

    void delete(Integer id);

    UpdateActorOutput update(Integer id, UpdateActorInput input);

    FindActorByIdOutput findById(Integer id);

    List<FindActorByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;

    //Join Column Parsers

    Map<String, String> parseFilmActorsJoinColumn(String keysString);
}
