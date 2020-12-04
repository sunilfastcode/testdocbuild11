package com.fastcode.testdocbuild11.application.core.staff;

import com.fastcode.testdocbuild11.application.core.staff.dto.*;
import com.fastcode.testdocbuild11.commons.search.SearchCriteria;
import java.util.*;
import org.springframework.data.domain.Pageable;

public interface IStaffAppService {
    //CRUD Operations

    CreateStaffOutput create(CreateStaffInput staff);

    void delete(Integer id);

    UpdateStaffOutput update(Integer id, UpdateStaffInput input);

    FindStaffByIdOutput findById(Integer id);

    List<FindStaffByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;
    //Relationship Operations
    //Relationship Operations

    GetAddressOutput getAddress(Integer staffid);

    GetStoreOutput getStore(Integer staffid);

    //Join Column Parsers

    Map<String, String> parsePaymentsJoinColumn(String keysString);

    Map<String, String> parseRentalsJoinColumn(String keysString);
}
