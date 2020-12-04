package com.fastcode.testdocbuild11.domain.extended.inventory;

import com.fastcode.testdocbuild11.domain.core.inventory.InventoryManager;
import com.fastcode.testdocbuild11.domain.extended.film.IFilmRepositoryExtended;
import com.fastcode.testdocbuild11.domain.extended.rental.IRentalRepositoryExtended;
import org.springframework.stereotype.Component;

@Component("inventoryManagerExtended")
public class InventoryManagerExtended extends InventoryManager implements IInventoryManagerExtended {

    public InventoryManagerExtended(
        IInventoryRepositoryExtended inventoryRepositoryExtended,
        IFilmRepositoryExtended filmRepositoryExtended,
        IRentalRepositoryExtended rentalRepositoryExtended
    ) {
        super(inventoryRepositoryExtended, filmRepositoryExtended, rentalRepositoryExtended);
    }
    //Add your custom code here
}
