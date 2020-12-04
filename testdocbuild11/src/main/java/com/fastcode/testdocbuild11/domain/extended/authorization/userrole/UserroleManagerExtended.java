package com.fastcode.testdocbuild11.domain.extended.authorization.userrole;

import com.fastcode.testdocbuild11.domain.core.authorization.userrole.UserroleManager;
import com.fastcode.testdocbuild11.domain.extended.authorization.role.IRoleRepositoryExtended;
import com.fastcode.testdocbuild11.domain.extended.authorization.user.IUserRepositoryExtended;
import org.springframework.stereotype.Component;

@Component("userroleManagerExtended")
public class UserroleManagerExtended extends UserroleManager implements IUserroleManagerExtended {

    public UserroleManagerExtended(
        IUserroleRepositoryExtended userroleRepositoryExtended,
        IRoleRepositoryExtended roleRepositoryExtended,
        IUserRepositoryExtended userRepositoryExtended
    ) {
        super(userroleRepositoryExtended, roleRepositoryExtended, userRepositoryExtended);
    }
    //Add your custom code here
}
