package com.fastcode.testdocbuild11.application.extended.authorization.userpermission;

import com.fastcode.testdocbuild11.application.core.authorization.userpermission.UserpermissionAppService;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.domain.extended.authorization.permission.IPermissionManagerExtended;
import com.fastcode.testdocbuild11.domain.extended.authorization.user.IUserManagerExtended;
import com.fastcode.testdocbuild11.domain.extended.authorization.userpermission.IUserpermissionManagerExtended;
import org.springframework.stereotype.Service;

@Service("userpermissionAppServiceExtended")
public class UserpermissionAppServiceExtended
    extends UserpermissionAppService
    implements IUserpermissionAppServiceExtended {

    public UserpermissionAppServiceExtended(
        IUserpermissionManagerExtended userpermissionManagerExtended,
        IPermissionManagerExtended permissionManagerExtended,
        IUserManagerExtended userManagerExtended,
        IUserpermissionMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(userpermissionManagerExtended, permissionManagerExtended, userManagerExtended, mapper, logHelper);
    }
    //Add your custom code here

}
