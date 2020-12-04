package com.fastcode.testdocbuild11.application.extended.authorization.rolepermission;

import com.fastcode.testdocbuild11.application.core.authorization.rolepermission.RolepermissionAppService;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.domain.extended.authorization.permission.IPermissionManagerExtended;
import com.fastcode.testdocbuild11.domain.extended.authorization.role.IRoleManagerExtended;
import com.fastcode.testdocbuild11.domain.extended.authorization.rolepermission.IRolepermissionManagerExtended;
import com.fastcode.testdocbuild11.domain.extended.authorization.userrole.IUserroleManagerExtended;
import com.fastcode.testdocbuild11.security.JWTAppService;
import org.springframework.stereotype.Service;

@Service("rolepermissionAppServiceExtended")
public class RolepermissionAppServiceExtended
    extends RolepermissionAppService
    implements IRolepermissionAppServiceExtended {

    public RolepermissionAppServiceExtended(
        JWTAppService jwtAppService,
        IUserroleManagerExtended userroleManagerExtended,
        IRolepermissionManagerExtended rolepermissionManagerExtended,
        IPermissionManagerExtended permissionManagerExtended,
        IRoleManagerExtended roleManagerExtended,
        IRolepermissionMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(
            jwtAppService,
            userroleManagerExtended,
            rolepermissionManagerExtended,
            permissionManagerExtended,
            roleManagerExtended,
            mapper,
            logHelper
        );
    }
    //Add your custom code here

}
