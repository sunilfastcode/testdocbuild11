package com.fastcode.testdocbuild11.application.extended.authorization.userrole;

import com.fastcode.testdocbuild11.application.core.authorization.userrole.UserroleAppService;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.domain.extended.authorization.role.IRoleManagerExtended;
import com.fastcode.testdocbuild11.domain.extended.authorization.user.IUserManagerExtended;
import com.fastcode.testdocbuild11.domain.extended.authorization.userrole.IUserroleManagerExtended;
import org.springframework.stereotype.Service;

@Service("userroleAppServiceExtended")
public class UserroleAppServiceExtended extends UserroleAppService implements IUserroleAppServiceExtended {

    public UserroleAppServiceExtended(
        IUserroleManagerExtended userroleManagerExtended,
        IRoleManagerExtended roleManagerExtended,
        IUserManagerExtended userManagerExtended,
        IUserroleMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(userroleManagerExtended, roleManagerExtended, userManagerExtended, mapper, logHelper);
    }
    //Add your custom code here

}
