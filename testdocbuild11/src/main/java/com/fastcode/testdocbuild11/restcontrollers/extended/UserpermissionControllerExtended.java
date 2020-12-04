package com.fastcode.testdocbuild11.restcontrollers.extended;

import com.fastcode.testdocbuild11.application.extended.authorization.permission.IPermissionAppServiceExtended;
import com.fastcode.testdocbuild11.application.extended.authorization.user.IUserAppServiceExtended;
import com.fastcode.testdocbuild11.application.extended.authorization.userpermission.IUserpermissionAppServiceExtended;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.restcontrollers.core.UserpermissionController;
import com.fastcode.testdocbuild11.security.JWTAppService;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userpermission/extended")
public class UserpermissionControllerExtended extends UserpermissionController {

    public UserpermissionControllerExtended(
        IUserpermissionAppServiceExtended userpermissionAppServiceExtended,
        IPermissionAppServiceExtended permissionAppServiceExtended,
        IUserAppServiceExtended userAppServiceExtended,
        JWTAppService jwtAppService,
        LoggingHelper helper,
        Environment env
    ) {
        super(
            userpermissionAppServiceExtended,
            permissionAppServiceExtended,
            userAppServiceExtended,
            jwtAppService,
            helper,
            env
        );
    }
    //Add your custom code here

}
