package com.fastcode.testdocbuild11.restcontrollers.extended;

import com.fastcode.testdocbuild11.application.extended.authorization.role.IRoleAppServiceExtended;
import com.fastcode.testdocbuild11.application.extended.authorization.rolepermission.IRolepermissionAppServiceExtended;
import com.fastcode.testdocbuild11.application.extended.authorization.userrole.IUserroleAppServiceExtended;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.restcontrollers.core.RoleController;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role/extended")
public class RoleControllerExtended extends RoleController {

    public RoleControllerExtended(
        IRoleAppServiceExtended roleAppServiceExtended,
        IRolepermissionAppServiceExtended rolepermissionAppServiceExtended,
        IUserroleAppServiceExtended userroleAppServiceExtended,
        LoggingHelper helper,
        Environment env
    ) {
        super(roleAppServiceExtended, rolepermissionAppServiceExtended, userroleAppServiceExtended, helper, env);
    }
    //Add your custom code here

}
