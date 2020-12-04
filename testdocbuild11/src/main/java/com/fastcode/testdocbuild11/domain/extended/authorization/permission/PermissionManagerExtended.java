package com.fastcode.testdocbuild11.domain.extended.authorization.permission;

import com.fastcode.testdocbuild11.domain.core.authorization.permission.PermissionManager;
import com.fastcode.testdocbuild11.domain.extended.authorization.rolepermission.IRolepermissionRepositoryExtended;
import com.fastcode.testdocbuild11.domain.extended.authorization.userpermission.IUserpermissionRepositoryExtended;
import org.springframework.stereotype.Component;

@Component("permissionManagerExtended")
public class PermissionManagerExtended extends PermissionManager implements IPermissionManagerExtended {

    public PermissionManagerExtended(
        IPermissionRepositoryExtended permissionRepositoryExtended,
        IRolepermissionRepositoryExtended rolepermissionRepositoryExtended,
        IUserpermissionRepositoryExtended userpermissionRepositoryExtended
    ) {
        super(permissionRepositoryExtended, rolepermissionRepositoryExtended, userpermissionRepositoryExtended);
    }
    //Add your custom code here
}
