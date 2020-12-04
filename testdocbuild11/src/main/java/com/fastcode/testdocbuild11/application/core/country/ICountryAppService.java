package com.fastcode.testdocbuild11.application.core.country;

import com.fastcode.testdocbuild11.application.core.country.dto.*;
import com.fastcode.testdocbuild11.commons.search.SearchCriteria;
import java.util.*;
import org.springframework.data.domain.Pageable;

public interface ICountryAppService {
    //CRUD Operations

    CreateCountryOutput create(CreateCountryInput country);

    void delete(Integer id);

    UpdateCountryOutput update(Integer id, UpdateCountryInput input);

    FindCountryByIdOutput findById(Integer id);

    List<FindCountryByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;

    //Join Column Parsers

    Map<String, String> parseCitysJoinColumn(String keysString);
}
