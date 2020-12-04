package com.fastcode.testdocbuild11.application.extended.authorization.role;

import com.fastcode.testdocbuild11.application.core.authorization.role.RoleAppService;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.domain.extended.authorization.role.IRoleManagerExtended;
import org.springframework.stereotype.Service;

@Service("roleAppServiceExtended")
public class RoleAppServiceExtended extends RoleAppService implements IRoleAppServiceExtended {

    public RoleAppServiceExtended(
        IRoleManagerExtended roleManagerExtended,
        IRoleMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(roleManagerExtended, mapper, logHelper);
    }
    //Add your custom code here

}
