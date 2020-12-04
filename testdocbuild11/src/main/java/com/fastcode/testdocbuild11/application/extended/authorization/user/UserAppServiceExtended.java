package com.fastcode.testdocbuild11.application.extended.authorization.user;

import com.fastcode.testdocbuild11.addons.reporting.domain.dashboardversion.*;
import com.fastcode.testdocbuild11.addons.reporting.domain.dashboardversionreport.*;
import com.fastcode.testdocbuild11.addons.reporting.domain.reportversion.*;
import com.fastcode.testdocbuild11.application.core.authorization.user.UserAppService;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.domain.core.authorization.userpreference.IUserpreferenceManager;
import com.fastcode.testdocbuild11.domain.extended.authorization.user.IUserManagerExtended;
import org.springframework.stereotype.Service;

@Service("userAppServiceExtended")
public class UserAppServiceExtended extends UserAppService implements IUserAppServiceExtended {

    public UserAppServiceExtended(
        IDashboardversionManager dashboardversionManager,
        IReportversionManager reportversionManager,
        IDashboardversionreportManager reportDashboardManager,
        IUserManagerExtended userManagerExtended,
        IUserpreferenceManager userpreferenceManager,
        IUserMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(
            dashboardversionManager,
            reportversionManager,
            reportDashboardManager,
            userManagerExtended,
            userpreferenceManager,
            mapper,
            logHelper
        );
    }
    //Add your custom code here

}
