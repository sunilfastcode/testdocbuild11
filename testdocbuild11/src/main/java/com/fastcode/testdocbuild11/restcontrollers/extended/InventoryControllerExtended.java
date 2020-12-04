package com.fastcode.testdocbuild11.restcontrollers.extended;

import com.fastcode.testdocbuild11.application.extended.film.IFilmAppServiceExtended;
import com.fastcode.testdocbuild11.application.extended.inventory.IInventoryAppServiceExtended;
import com.fastcode.testdocbuild11.application.extended.rental.IRentalAppServiceExtended;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.restcontrollers.core.InventoryController;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory/extended")
public class InventoryControllerExtended extends InventoryController {

    public InventoryControllerExtended(
        IInventoryAppServiceExtended inventoryAppServiceExtended,
        IFilmAppServiceExtended filmAppServiceExtended,
        IRentalAppServiceExtended rentalAppServiceExtended,
        LoggingHelper helper,
        Environment env
    ) {
        super(inventoryAppServiceExtended, filmAppServiceExtended, rentalAppServiceExtended, helper, env);
    }
    //Add your custom code here

}
