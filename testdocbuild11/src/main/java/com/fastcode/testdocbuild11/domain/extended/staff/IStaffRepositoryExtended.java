package com.fastcode.testdocbuild11.domain.extended.staff;

import com.fastcode.testdocbuild11.domain.core.staff.IStaffRepository;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("staffRepositoryExtended")
public interface IStaffRepositoryExtended extends IStaffRepository {
    //Add your custom code here
}
