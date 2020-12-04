package com.fastcode.testdocbuild11.restcontrollers.extended;

import com.fastcode.testdocbuild11.application.extended.authorization.permission.IPermissionAppServiceExtended;
import com.fastcode.testdocbuild11.application.extended.authorization.role.IRoleAppServiceExtended;
import com.fastcode.testdocbuild11.application.extended.authorization.rolepermission.IRolepermissionAppServiceExtended;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.restcontrollers.core.RolepermissionController;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rolepermission/extended")
public class RolepermissionControllerExtended extends RolepermissionController {

    public RolepermissionControllerExtended(
        IRolepermissionAppServiceExtended rolepermissionAppServiceExtended,
        IPermissionAppServiceExtended permissionAppServiceExtended,
        IRoleAppServiceExtended roleAppServiceExtended,
        LoggingHelper helper,
        Environment env
    ) {
        super(rolepermissionAppServiceExtended, permissionAppServiceExtended, roleAppServiceExtended, helper, env);
    }
    //Add your custom code here

}
