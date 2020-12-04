package com.fastcode.testdocbuild11.domain.extended.authorization.rolepermission;

import com.fastcode.testdocbuild11.domain.core.authorization.rolepermission.RolepermissionManager;
import com.fastcode.testdocbuild11.domain.extended.authorization.permission.IPermissionRepositoryExtended;
import com.fastcode.testdocbuild11.domain.extended.authorization.role.IRoleRepositoryExtended;
import org.springframework.stereotype.Component;

@Component("rolepermissionManagerExtended")
public class RolepermissionManagerExtended extends RolepermissionManager implements IRolepermissionManagerExtended {

    public RolepermissionManagerExtended(
        IRolepermissionRepositoryExtended rolepermissionRepositoryExtended,
        IPermissionRepositoryExtended permissionRepositoryExtended,
        IRoleRepositoryExtended roleRepositoryExtended
    ) {
        super(rolepermissionRepositoryExtended, permissionRepositoryExtended, roleRepositoryExtended);
    }
    //Add your custom code here
}
