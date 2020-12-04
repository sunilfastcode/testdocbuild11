package com.fastcode.testdocbuild11.domain.extended.authorization.user;

import com.fastcode.testdocbuild11.domain.core.authorization.user.UserManager;
import com.fastcode.testdocbuild11.domain.extended.authorization.userpermission.IUserpermissionRepositoryExtended;
import com.fastcode.testdocbuild11.domain.extended.authorization.userrole.IUserroleRepositoryExtended;
import org.springframework.stereotype.Component;

@Component("userManagerExtended")
public class UserManagerExtended extends UserManager implements IUserManagerExtended {

    public UserManagerExtended(
        IUserRepositoryExtended userRepositoryExtended,
        IUserpermissionRepositoryExtended userpermissionRepositoryExtended,
        IUserroleRepositoryExtended userroleRepositoryExtended
    ) {
        super(userRepositoryExtended, userpermissionRepositoryExtended, userroleRepositoryExtended);
    }
    //Add your custom code here
}
