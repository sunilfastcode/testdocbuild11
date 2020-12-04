package com.fastcode.testdocbuild11.domain.extended.authorization.role;

import com.fastcode.testdocbuild11.domain.core.authorization.role.RoleManager;
import com.fastcode.testdocbuild11.domain.extended.authorization.rolepermission.IRolepermissionRepositoryExtended;
import com.fastcode.testdocbuild11.domain.extended.authorization.userrole.IUserroleRepositoryExtended;
import org.springframework.stereotype.Component;

@Component("roleManagerExtended")
public class RoleManagerExtended extends RoleManager implements IRoleManagerExtended {

    public RoleManagerExtended(
        IRoleRepositoryExtended roleRepositoryExtended,
        IRolepermissionRepositoryExtended rolepermissionRepositoryExtended,
        IUserroleRepositoryExtended userroleRepositoryExtended
    ) {
        super(roleRepositoryExtended, rolepermissionRepositoryExtended, userroleRepositoryExtended);
    }
    //Add your custom code here
}
