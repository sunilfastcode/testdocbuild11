package com.fastcode.testdocbuild11.application.extended.category;

import com.fastcode.testdocbuild11.application.core.category.CategoryAppService;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.domain.extended.category.ICategoryManagerExtended;
import org.springframework.stereotype.Service;

@Service("categoryAppServiceExtended")
public class CategoryAppServiceExtended extends CategoryAppService implements ICategoryAppServiceExtended {

    public CategoryAppServiceExtended(
        ICategoryManagerExtended categoryManagerExtended,
        ICategoryMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(categoryManagerExtended, mapper, logHelper);
    }
    //Add your custom code here

}
