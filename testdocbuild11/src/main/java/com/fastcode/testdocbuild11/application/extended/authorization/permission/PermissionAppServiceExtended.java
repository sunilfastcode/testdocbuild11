package com.fastcode.testdocbuild11.application.extended.authorization.permission;

import com.fastcode.testdocbuild11.application.core.authorization.permission.PermissionAppService;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.domain.extended.authorization.permission.IPermissionManagerExtended;
import org.springframework.stereotype.Service;

@Service("permissionAppServiceExtended")
public class PermissionAppServiceExtended extends PermissionAppService implements IPermissionAppServiceExtended {

    public PermissionAppServiceExtended(
        IPermissionManagerExtended permissionManagerExtended,
        IPermissionMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(permissionManagerExtended, mapper, logHelper);
    }
    //Add your custom code here

}
