package com.fastcode.testdocbuild11.application.extended.inventory;

import com.fastcode.testdocbuild11.application.core.inventory.InventoryAppService;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.domain.extended.film.IFilmManagerExtended;
import com.fastcode.testdocbuild11.domain.extended.inventory.IInventoryManagerExtended;
import org.springframework.stereotype.Service;

@Service("inventoryAppServiceExtended")
public class InventoryAppServiceExtended extends InventoryAppService implements IInventoryAppServiceExtended {

    public InventoryAppServiceExtended(
        IInventoryManagerExtended inventoryManagerExtended,
        IFilmManagerExtended filmManagerExtended,
        IInventoryMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(inventoryManagerExtended, filmManagerExtended, mapper, logHelper);
    }
    //Add your custom code here

}
