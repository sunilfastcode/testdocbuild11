package com.fastcode.testdocbuild11.domain.extended.authorization.userpermission;

import com.fastcode.testdocbuild11.domain.core.authorization.userpermission.UserpermissionManager;
import com.fastcode.testdocbuild11.domain.extended.authorization.permission.IPermissionRepositoryExtended;
import com.fastcode.testdocbuild11.domain.extended.authorization.user.IUserRepositoryExtended;
import org.springframework.stereotype.Component;

@Component("userpermissionManagerExtended")
public class UserpermissionManagerExtended extends UserpermissionManager implements IUserpermissionManagerExtended {

    public UserpermissionManagerExtended(
        IUserpermissionRepositoryExtended userpermissionRepositoryExtended,
        IPermissionRepositoryExtended permissionRepositoryExtended,
        IUserRepositoryExtended userRepositoryExtended
    ) {
        super(userpermissionRepositoryExtended, permissionRepositoryExtended, userRepositoryExtended);
    }
    //Add your custom code here
}
