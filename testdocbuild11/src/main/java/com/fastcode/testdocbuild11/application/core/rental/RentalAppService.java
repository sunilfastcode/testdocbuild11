package com.fastcode.testdocbuild11.application.core.rental;

import com.fastcode.testdocbuild11.application.core.rental.dto.*;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.fastcode.testdocbuild11.domain.core.customer.CustomerEntity;
import com.fastcode.testdocbuild11.domain.core.customer.ICustomerManager;
import com.fastcode.testdocbuild11.domain.core.inventory.IInventoryManager;
import com.fastcode.testdocbuild11.domain.core.inventory.InventoryEntity;
import com.fastcode.testdocbuild11.domain.core.rental.IRentalManager;
import com.fastcode.testdocbuild11.domain.core.rental.QRentalEntity;
import com.fastcode.testdocbuild11.domain.core.rental.RentalEntity;
import com.fastcode.testdocbuild11.domain.core.staff.IStaffManager;
import com.fastcode.testdocbuild11.domain.core.staff.StaffEntity;
import com.querydsl.core.BooleanBuilder;
import java.time.*;
import java.util.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("rentalAppService")
@RequiredArgsConstructor
public class RentalAppService implements IRentalAppService {

    @Qualifier("rentalManager")
    @NonNull
    protected final IRentalManager _rentalManager;

    @Qualifier("customerManager")
    @NonNull
    protected final ICustomerManager _customerManager;

    @Qualifier("inventoryManager")
    @NonNull
    protected final IInventoryManager _inventoryManager;

    @Qualifier("staffManager")
    @NonNull
    protected final IStaffManager _staffManager;

    @Qualifier("IRentalMapperImpl")
    @NonNull
    protected final IRentalMapper mapper;

    @NonNull
    protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreateRentalOutput create(CreateRentalInput input) {
        RentalEntity rental = mapper.createRentalInputToRentalEntity(input);
        CustomerEntity foundCustomer = null;
        InventoryEntity foundInventory = null;
        StaffEntity foundStaff = null;
        if (input.getCustomerId() != null) {
            foundCustomer = _customerManager.findById(Integer.parseInt(input.getCustomerId().toString()));

            if (foundCustomer != null) {
                rental.setCustomer(foundCustomer);
            } else {
                return null;
            }
        } else {
            return null;
        }
        if (input.getInventoryId() != null) {
            foundInventory = _inventoryManager.findById(input.getInventoryId());

            if (foundInventory != null) {
                rental.setInventory(foundInventory);
            } else {
                return null;
            }
        } else {
            return null;
        }
        if (input.getStaffId() != null) {
            foundStaff = _staffManager.findById(Integer.parseInt(input.getStaffId().toString()));

            if (foundStaff != null) {
                rental.setStaff(foundStaff);
            } else {
                return null;
            }
        } else {
            return null;
        }

        RentalEntity createdRental = _rentalManager.create(rental);
        return mapper.rentalEntityToCreateRentalOutput(createdRental);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UpdateRentalOutput update(Integer rentalId, UpdateRentalInput input) {
        RentalEntity rental = mapper.updateRentalInputToRentalEntity(input);
        CustomerEntity foundCustomer = null;
        InventoryEntity foundInventory = null;
        StaffEntity foundStaff = null;

        if (input.getCustomerId() != null) {
            foundCustomer = _customerManager.findById(Integer.parseInt(input.getCustomerId().toString()));

            if (foundCustomer != null) {
                rental.setCustomer(foundCustomer);
            } else {
                return null;
            }
        } else {
            return null;
        }

        if (input.getInventoryId() != null) {
            foundInventory = _inventoryManager.findById(input.getInventoryId());

            if (foundInventory != null) {
                rental.setInventory(foundInventory);
            } else {
                return null;
            }
        } else {
            return null;
        }

        if (input.getStaffId() != null) {
            foundStaff = _staffManager.findById(Integer.parseInt(input.getStaffId().toString()));

            if (foundStaff != null) {
                rental.setStaff(foundStaff);
            } else {
                return null;
            }
        } else {
            return null;
        }

        RentalEntity updatedRental = _rentalManager.update(rental);
        return mapper.rentalEntityToUpdateRentalOutput(updatedRental);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Integer rentalId) {
        RentalEntity existing = _rentalManager.findById(rentalId);
        _rentalManager.delete(existing);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindRentalByIdOutput findById(Integer rentalId) {
        RentalEntity foundRental = _rentalManager.findById(rentalId);
        if (foundRental == null) return null;

        return mapper.rentalEntityToFindRentalByIdOutput(foundRental);
    }

    //Customer
    // ReST API Call - GET /rental/1/customer
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetCustomerOutput getCustomer(Integer rentalId) {
        RentalEntity foundRental = _rentalManager.findById(rentalId);
        if (foundRental == null) {
            logHelper.getLogger().error("There does not exist a rental wth a id=%s", rentalId);
            return null;
        }
        CustomerEntity re = _rentalManager.getCustomer(rentalId);
        return mapper.customerEntityToGetCustomerOutput(re, foundRental);
    }

    //Inventory
    // ReST API Call - GET /rental/1/inventory
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetInventoryOutput getInventory(Integer rentalId) {
        RentalEntity foundRental = _rentalManager.findById(rentalId);
        if (foundRental == null) {
            logHelper.getLogger().error("There does not exist a rental wth a id=%s", rentalId);
            return null;
        }
        InventoryEntity re = _rentalManager.getInventory(rentalId);
        return mapper.inventoryEntityToGetInventoryOutput(re, foundRental);
    }

    //Staff
    // ReST API Call - GET /rental/1/staff
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetStaffOutput getStaff(Integer rentalId) {
        RentalEntity foundRental = _rentalManager.findById(rentalId);
        if (foundRental == null) {
            logHelper.getLogger().error("There does not exist a rental wth a id=%s", rentalId);
            return null;
        }
        StaffEntity re = _rentalManager.getStaff(rentalId);
        return mapper.staffEntityToGetStaffOutput(re, foundRental);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindRentalByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception {
        Page<RentalEntity> foundRental = _rentalManager.findAll(search(search), pageable);
        List<RentalEntity> rentalList = foundRental.getContent();
        Iterator<RentalEntity> rentalIterator = rentalList.iterator();
        List<FindRentalByIdOutput> output = new ArrayList<>();

        while (rentalIterator.hasNext()) {
            RentalEntity rental = rentalIterator.next();
            output.add(mapper.rentalEntityToFindRentalByIdOutput(rental));
        }
        return output;
    }

    protected BooleanBuilder search(SearchCriteria search) throws Exception {
        QRentalEntity rental = QRentalEntity.rentalEntity;
        if (search != null) {
            Map<String, SearchFields> map = new HashMap<>();
            for (SearchFields fieldDetails : search.getFields()) {
                map.put(fieldDetails.getFieldName(), fieldDetails);
            }
            List<String> keysList = new ArrayList<String>(map.keySet());
            checkProperties(keysList);
            return searchKeyValuePair(rental, map, search.getJoinColumns());
        }
        return null;
    }

    protected void checkProperties(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    list.get(i).replace("%20", "").trim().equals("customerId") ||
                    list.get(i).replace("%20", "").trim().equals("inventoryId") ||
                    list.get(i).replace("%20", "").trim().equals("staffId") ||
                    list.get(i).replace("%20", "").trim().equals("lastUpdate") ||
                    list.get(i).replace("%20", "").trim().equals("rentalDate") ||
                    list.get(i).replace("%20", "").trim().equals("rentalId") ||
                    list.get(i).replace("%20", "").trim().equals("returnDate")
                )
            ) {
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    protected BooleanBuilder searchKeyValuePair(
        QRentalEntity rental,
        Map<String, SearchFields> map,
        Map<String, String> joinColumns
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if (details.getKey().replace("%20", "").trim().equals("lastUpdate")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    rental.lastUpdate.eq(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    rental.lastUpdate.ne(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (details.getValue().getOperator().equals("range")) {
                    LocalDateTime startLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getStartingValue()
                    );
                    LocalDateTime endLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getEndingValue()
                    );
                    if (startLocalDateTime != null && endLocalDateTime != null) builder.and(
                        rental.lastUpdate.between(startLocalDateTime, endLocalDateTime)
                    ); else if (endLocalDateTime != null) builder.and(
                        rental.lastUpdate.loe(endLocalDateTime)
                    ); else if (startLocalDateTime != null) builder.and(rental.lastUpdate.goe(startLocalDateTime));
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("rentalDate")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    rental.rentalDate.eq(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    rental.rentalDate.ne(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (details.getValue().getOperator().equals("range")) {
                    LocalDateTime startLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getStartingValue()
                    );
                    LocalDateTime endLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getEndingValue()
                    );
                    if (startLocalDateTime != null && endLocalDateTime != null) builder.and(
                        rental.rentalDate.between(startLocalDateTime, endLocalDateTime)
                    ); else if (endLocalDateTime != null) builder.and(
                        rental.rentalDate.loe(endLocalDateTime)
                    ); else if (startLocalDateTime != null) builder.and(rental.rentalDate.goe(startLocalDateTime));
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("rentalId")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(rental.rentalId.eq(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(rental.rentalId.ne(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        rental.rentalId.between(
                            Integer.valueOf(details.getValue().getStartingValue()),
                            Integer.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        rental.rentalId.goe(Integer.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        rental.rentalId.loe(Integer.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("returnDate")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    rental.returnDate.eq(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    rental.returnDate.ne(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (details.getValue().getOperator().equals("range")) {
                    LocalDateTime startLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getStartingValue()
                    );
                    LocalDateTime endLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getEndingValue()
                    );
                    if (startLocalDateTime != null && endLocalDateTime != null) builder.and(
                        rental.returnDate.between(startLocalDateTime, endLocalDateTime)
                    ); else if (endLocalDateTime != null) builder.and(
                        rental.returnDate.loe(endLocalDateTime)
                    ); else if (startLocalDateTime != null) builder.and(rental.returnDate.goe(startLocalDateTime));
                }
            }
        }

        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("customerId")) {
                builder.and(rental.customer.customerId.eq(Integer.parseInt(joinCol.getValue())));
            }
        }
        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("inventoryId")) {
                builder.and(rental.inventory.inventoryId.eq(Integer.parseInt(joinCol.getValue())));
            }
        }
        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("staffId")) {
                builder.and(rental.staff.staffId.eq(Integer.parseInt(joinCol.getValue())));
            }
        }
        return builder;
    }

    public Map<String, String> parsePaymentsJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("rentalId", keysString);

        return joinColumnMap;
    }
}
