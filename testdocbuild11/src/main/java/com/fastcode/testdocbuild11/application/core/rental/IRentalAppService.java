package com.fastcode.testdocbuild11.application.core.rental;

import com.fastcode.testdocbuild11.application.core.rental.dto.*;
import com.fastcode.testdocbuild11.commons.search.SearchCriteria;
import java.util.*;
import org.springframework.data.domain.Pageable;

public interface IRentalAppService {
    //CRUD Operations

    CreateRentalOutput create(CreateRentalInput rental);

    void delete(Integer id);

    UpdateRentalOutput update(Integer id, UpdateRentalInput input);

    FindRentalByIdOutput findById(Integer id);

    List<FindRentalByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;
    //Relationship Operations
    //Relationship Operations
    //Relationship Operations

    GetCustomerOutput getCustomer(Integer rentalid);

    GetInventoryOutput getInventory(Integer rentalid);

    GetStaffOutput getStaff(Integer rentalid);

    //Join Column Parsers

    Map<String, String> parsePaymentsJoinColumn(String keysString);
}
