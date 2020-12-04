package com.fastcode.testdocbuild11.domain.extended.authorization.role;

import com.fastcode.testdocbuild11.domain.core.authorization.role.IRoleRepository;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("roleRepositoryExtended")
public interface IRoleRepositoryExtended extends IRoleRepository {
    //Add your custom code here
}
