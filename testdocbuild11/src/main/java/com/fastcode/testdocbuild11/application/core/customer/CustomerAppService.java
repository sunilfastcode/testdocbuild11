package com.fastcode.testdocbuild11.application.core.customer;

import com.fastcode.testdocbuild11.application.core.customer.dto.*;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.fastcode.testdocbuild11.domain.core.address.AddressEntity;
import com.fastcode.testdocbuild11.domain.core.address.IAddressManager;
import com.fastcode.testdocbuild11.domain.core.customer.CustomerEntity;
import com.fastcode.testdocbuild11.domain.core.customer.ICustomerManager;
import com.fastcode.testdocbuild11.domain.core.customer.QCustomerEntity;
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

@Service("customerAppService")
@RequiredArgsConstructor
public class CustomerAppService implements ICustomerAppService {

    @Qualifier("customerManager")
    @NonNull
    protected final ICustomerManager _customerManager;

    @Qualifier("addressManager")
    @NonNull
    protected final IAddressManager _addressManager;

    @Qualifier("ICustomerMapperImpl")
    @NonNull
    protected final ICustomerMapper mapper;

    @NonNull
    protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreateCustomerOutput create(CreateCustomerInput input) {
        CustomerEntity customer = mapper.createCustomerInputToCustomerEntity(input);
        AddressEntity foundAddress = null;
        if (input.getAddressId() != null) {
            foundAddress = _addressManager.findById(Integer.parseInt(input.getAddressId().toString()));

            if (foundAddress != null) {
                customer.setAddress(foundAddress);
            } else {
                return null;
            }
        } else {
            return null;
        }

        CustomerEntity createdCustomer = _customerManager.create(customer);
        return mapper.customerEntityToCreateCustomerOutput(createdCustomer);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UpdateCustomerOutput update(Integer customerId, UpdateCustomerInput input) {
        CustomerEntity customer = mapper.updateCustomerInputToCustomerEntity(input);
        AddressEntity foundAddress = null;

        if (input.getAddressId() != null) {
            foundAddress = _addressManager.findById(Integer.parseInt(input.getAddressId().toString()));

            if (foundAddress != null) {
                customer.setAddress(foundAddress);
            } else {
                return null;
            }
        } else {
            return null;
        }

        CustomerEntity updatedCustomer = _customerManager.update(customer);
        return mapper.customerEntityToUpdateCustomerOutput(updatedCustomer);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Integer customerId) {
        CustomerEntity existing = _customerManager.findById(customerId);
        _customerManager.delete(existing);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindCustomerByIdOutput findById(Integer customerId) {
        CustomerEntity foundCustomer = _customerManager.findById(customerId);
        if (foundCustomer == null) return null;

        return mapper.customerEntityToFindCustomerByIdOutput(foundCustomer);
    }

    //Address
    // ReST API Call - GET /customer/1/address
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetAddressOutput getAddress(Integer customerId) {
        CustomerEntity foundCustomer = _customerManager.findById(customerId);
        if (foundCustomer == null) {
            logHelper.getLogger().error("There does not exist a customer wth a id=%s", customerId);
            return null;
        }
        AddressEntity re = _customerManager.getAddress(customerId);
        return mapper.addressEntityToGetAddressOutput(re, foundCustomer);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindCustomerByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception {
        Page<CustomerEntity> foundCustomer = _customerManager.findAll(search(search), pageable);
        List<CustomerEntity> customerList = foundCustomer.getContent();
        Iterator<CustomerEntity> customerIterator = customerList.iterator();
        List<FindCustomerByIdOutput> output = new ArrayList<>();

        while (customerIterator.hasNext()) {
            CustomerEntity customer = customerIterator.next();
            output.add(mapper.customerEntityToFindCustomerByIdOutput(customer));
        }
        return output;
    }

    protected BooleanBuilder search(SearchCriteria search) throws Exception {
        QCustomerEntity customer = QCustomerEntity.customerEntity;
        if (search != null) {
            Map<String, SearchFields> map = new HashMap<>();
            for (SearchFields fieldDetails : search.getFields()) {
                map.put(fieldDetails.getFieldName(), fieldDetails);
            }
            List<String> keysList = new ArrayList<String>(map.keySet());
            checkProperties(keysList);
            return searchKeyValuePair(customer, map, search.getJoinColumns());
        }
        return null;
    }

    protected void checkProperties(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    list.get(i).replace("%20", "").trim().equals("addressId") ||
                    list.get(i).replace("%20", "").trim().equals("active") ||
                    list.get(i).replace("%20", "").trim().equals("activebool") ||
                    list.get(i).replace("%20", "").trim().equals("createDate") ||
                    list.get(i).replace("%20", "").trim().equals("customerId") ||
                    list.get(i).replace("%20", "").trim().equals("email") ||
                    list.get(i).replace("%20", "").trim().equals("firstName") ||
                    list.get(i).replace("%20", "").trim().equals("lastName") ||
                    list.get(i).replace("%20", "").trim().equals("lastUpdate") ||
                    list.get(i).replace("%20", "").trim().equals("storeId")
                )
            ) {
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    protected BooleanBuilder searchKeyValuePair(
        QCustomerEntity customer,
        Map<String, SearchFields> map,
        Map<String, String> joinColumns
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if (details.getKey().replace("%20", "").trim().equals("active")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(customer.active.eq(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(customer.active.ne(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        customer.active.between(
                            Integer.valueOf(details.getValue().getStartingValue()),
                            Integer.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        customer.active.goe(Integer.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        customer.active.loe(Integer.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("activebool")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    (
                        details.getValue().getSearchValue().equalsIgnoreCase("true") ||
                        details.getValue().getSearchValue().equalsIgnoreCase("false")
                    )
                ) builder.and(
                    customer.activebool.eq(Boolean.parseBoolean(details.getValue().getSearchValue()))
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    (
                        details.getValue().getSearchValue().equalsIgnoreCase("true") ||
                        details.getValue().getSearchValue().equalsIgnoreCase("false")
                    )
                ) builder.and(customer.activebool.ne(Boolean.parseBoolean(details.getValue().getSearchValue())));
            }
            if (details.getKey().replace("%20", "").trim().equals("createDate")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    SearchUtils.stringToLocalDate(details.getValue().getSearchValue()) != null
                ) builder.and(
                    customer.createDate.eq(SearchUtils.stringToLocalDate(details.getValue().getSearchValue()))
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    SearchUtils.stringToLocalDate(details.getValue().getSearchValue()) != null
                ) builder.and(
                    customer.createDate.ne(SearchUtils.stringToLocalDate(details.getValue().getSearchValue()))
                ); else if (details.getValue().getOperator().equals("range")) {
                    LocalDate startLocalDate = SearchUtils.stringToLocalDate(details.getValue().getStartingValue());
                    LocalDate endLocalDate = SearchUtils.stringToLocalDate(details.getValue().getEndingValue());
                    if (startLocalDate != null && endLocalDate != null) builder.and(
                        customer.createDate.between(startLocalDate, endLocalDate)
                    ); else if (endLocalDate != null) builder.and(customer.createDate.loe(endLocalDate)); else if (
                        startLocalDate != null
                    ) builder.and(customer.createDate.goe(startLocalDate));
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("customerId")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(customer.customerId.eq(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(customer.customerId.ne(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        customer.customerId.between(
                            Integer.valueOf(details.getValue().getStartingValue()),
                            Integer.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        customer.customerId.goe(Integer.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        customer.customerId.loe(Integer.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("email")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    customer.email.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    customer.email.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    customer.email.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("firstName")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    customer.firstName.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    customer.firstName.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    customer.firstName.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("lastName")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    customer.lastName.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    customer.lastName.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    customer.lastName.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("lastUpdate")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    customer.lastUpdate.eq(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    customer.lastUpdate.ne(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (details.getValue().getOperator().equals("range")) {
                    LocalDateTime startLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getStartingValue()
                    );
                    LocalDateTime endLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getEndingValue()
                    );
                    if (startLocalDateTime != null && endLocalDateTime != null) builder.and(
                        customer.lastUpdate.between(startLocalDateTime, endLocalDateTime)
                    ); else if (endLocalDateTime != null) builder.and(
                        customer.lastUpdate.loe(endLocalDateTime)
                    ); else if (startLocalDateTime != null) builder.and(customer.lastUpdate.goe(startLocalDateTime));
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("storeId")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(customer.storeId.eq(Short.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(customer.storeId.ne(Short.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        customer.storeId.between(
                            Short.valueOf(details.getValue().getStartingValue()),
                            Short.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        customer.storeId.goe(Short.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        customer.storeId.loe(Short.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
        }

        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("addressId")) {
                builder.and(customer.address.addressId.eq(Integer.parseInt(joinCol.getValue())));
            }
        }
        return builder;
    }

    public Map<String, String> parsePaymentsJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("customerId", keysString);

        return joinColumnMap;
    }

    public Map<String, String> parseRentalsJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("customerId", keysString);

        return joinColumnMap;
    }
}
