package com.fastcode.testdocbuild11.domain.extended.authorization.permission;

import com.fastcode.testdocbuild11.domain.core.authorization.permission.IPermissionRepository;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("permissionRepositoryExtended")
public interface IPermissionRepositoryExtended extends IPermissionRepository {
    //Add your custom code here
}
