package com.fastcode.testdocbuild11.application.core.authorization.userpermission;

import com.fastcode.testdocbuild11.application.core.authorization.userpermission.dto.*;
import com.fastcode.testdocbuild11.commons.search.SearchCriteria;
import com.fastcode.testdocbuild11.domain.core.authorization.userpermission.UserpermissionId;
import java.util.*;
import org.springframework.data.domain.Pageable;

public interface IUserpermissionAppService {
    //CRUD Operations

    CreateUserpermissionOutput create(CreateUserpermissionInput userpermission);

    void delete(UserpermissionId userpermissionId);

    UpdateUserpermissionOutput update(UserpermissionId userpermissionId, UpdateUserpermissionInput input);

    FindUserpermissionByIdOutput findById(UserpermissionId userpermissionId);

    List<FindUserpermissionByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;
    //Relationship Operations
    //Relationship Operations

    GetPermissionOutput getPermission(UserpermissionId userpermissionId);

    GetUserOutput getUser(UserpermissionId userpermissionId);

    //Join Column Parsers

    UserpermissionId parseUserpermissionKey(String keysString);
}
