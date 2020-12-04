package com.fastcode.testdocbuild11.application.core.payment;

import com.fastcode.testdocbuild11.application.core.payment.dto.*;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.fastcode.testdocbuild11.domain.core.customer.CustomerEntity;
import com.fastcode.testdocbuild11.domain.core.customer.ICustomerManager;
import com.fastcode.testdocbuild11.domain.core.payment.IPaymentManager;
import com.fastcode.testdocbuild11.domain.core.payment.PaymentEntity;
import com.fastcode.testdocbuild11.domain.core.payment.QPaymentEntity;
import com.fastcode.testdocbuild11.domain.core.rental.IRentalManager;
import com.fastcode.testdocbuild11.domain.core.rental.RentalEntity;
import com.fastcode.testdocbuild11.domain.core.staff.IStaffManager;
import com.fastcode.testdocbuild11.domain.core.staff.StaffEntity;
import com.querydsl.core.BooleanBuilder;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("paymentAppService")
@RequiredArgsConstructor
public class PaymentAppService implements IPaymentAppService {

    @Qualifier("paymentManager")
    @NonNull
    protected final IPaymentManager _paymentManager;

    @Qualifier("customerManager")
    @NonNull
    protected final ICustomerManager _customerManager;

    @Qualifier("rentalManager")
    @NonNull
    protected final IRentalManager _rentalManager;

    @Qualifier("staffManager")
    @NonNull
    protected final IStaffManager _staffManager;

    @Qualifier("IPaymentMapperImpl")
    @NonNull
    protected final IPaymentMapper mapper;

    @NonNull
    protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreatePaymentOutput create(CreatePaymentInput input) {
        PaymentEntity payment = mapper.createPaymentInputToPaymentEntity(input);
        CustomerEntity foundCustomer = null;
        RentalEntity foundRental = null;
        StaffEntity foundStaff = null;
        if (input.getCustomerId() != null) {
            foundCustomer = _customerManager.findById(Integer.parseInt(input.getCustomerId().toString()));

            if (foundCustomer != null) {
                payment.setCustomer(foundCustomer);
            } else {
                return null;
            }
        } else {
            return null;
        }
        if (input.getRentalId() != null) {
            foundRental = _rentalManager.findById(input.getRentalId());

            if (foundRental != null) {
                payment.setRental(foundRental);
            } else {
                return null;
            }
        } else {
            return null;
        }
        if (input.getStaffId() != null) {
            foundStaff = _staffManager.findById(Integer.parseInt(input.getStaffId().toString()));

            if (foundStaff != null) {
                payment.setStaff(foundStaff);
            } else {
                return null;
            }
        } else {
            return null;
        }

        PaymentEntity createdPayment = _paymentManager.create(payment);
        return mapper.paymentEntityToCreatePaymentOutput(createdPayment);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UpdatePaymentOutput update(Integer paymentId, UpdatePaymentInput input) {
        PaymentEntity payment = mapper.updatePaymentInputToPaymentEntity(input);
        CustomerEntity foundCustomer = null;
        RentalEntity foundRental = null;
        StaffEntity foundStaff = null;

        if (input.getCustomerId() != null) {
            foundCustomer = _customerManager.findById(Integer.parseInt(input.getCustomerId().toString()));

            if (foundCustomer != null) {
                payment.setCustomer(foundCustomer);
            } else {
                return null;
            }
        } else {
            return null;
        }

        if (input.getRentalId() != null) {
            foundRental = _rentalManager.findById(input.getRentalId());

            if (foundRental != null) {
                payment.setRental(foundRental);
            } else {
                return null;
            }
        } else {
            return null;
        }

        if (input.getStaffId() != null) {
            foundStaff = _staffManager.findById(Integer.parseInt(input.getStaffId().toString()));

            if (foundStaff != null) {
                payment.setStaff(foundStaff);
            } else {
                return null;
            }
        } else {
            return null;
        }

        PaymentEntity updatedPayment = _paymentManager.update(payment);
        return mapper.paymentEntityToUpdatePaymentOutput(updatedPayment);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Integer paymentId) {
        PaymentEntity existing = _paymentManager.findById(paymentId);
        _paymentManager.delete(existing);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindPaymentByIdOutput findById(Integer paymentId) {
        PaymentEntity foundPayment = _paymentManager.findById(paymentId);
        if (foundPayment == null) return null;

        return mapper.paymentEntityToFindPaymentByIdOutput(foundPayment);
    }

    //Customer
    // ReST API Call - GET /payment/1/customer
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetCustomerOutput getCustomer(Integer paymentId) {
        PaymentEntity foundPayment = _paymentManager.findById(paymentId);
        if (foundPayment == null) {
            logHelper.getLogger().error("There does not exist a payment wth a id=%s", paymentId);
            return null;
        }
        CustomerEntity re = _paymentManager.getCustomer(paymentId);
        return mapper.customerEntityToGetCustomerOutput(re, foundPayment);
    }

    //Rental
    // ReST API Call - GET /payment/1/rental
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetRentalOutput getRental(Integer paymentId) {
        PaymentEntity foundPayment = _paymentManager.findById(paymentId);
        if (foundPayment == null) {
            logHelper.getLogger().error("There does not exist a payment wth a id=%s", paymentId);
            return null;
        }
        RentalEntity re = _paymentManager.getRental(paymentId);
        return mapper.rentalEntityToGetRentalOutput(re, foundPayment);
    }

    //Staff
    // ReST API Call - GET /payment/1/staff
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetStaffOutput getStaff(Integer paymentId) {
        PaymentEntity foundPayment = _paymentManager.findById(paymentId);
        if (foundPayment == null) {
            logHelper.getLogger().error("There does not exist a payment wth a id=%s", paymentId);
            return null;
        }
        StaffEntity re = _paymentManager.getStaff(paymentId);
        return mapper.staffEntityToGetStaffOutput(re, foundPayment);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindPaymentByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception {
        Page<PaymentEntity> foundPayment = _paymentManager.findAll(search(search), pageable);
        List<PaymentEntity> paymentList = foundPayment.getContent();
        Iterator<PaymentEntity> paymentIterator = paymentList.iterator();
        List<FindPaymentByIdOutput> output = new ArrayList<>();

        while (paymentIterator.hasNext()) {
            PaymentEntity payment = paymentIterator.next();
            output.add(mapper.paymentEntityToFindPaymentByIdOutput(payment));
        }
        return output;
    }

    protected BooleanBuilder search(SearchCriteria search) throws Exception {
        QPaymentEntity payment = QPaymentEntity.paymentEntity;
        if (search != null) {
            Map<String, SearchFields> map = new HashMap<>();
            for (SearchFields fieldDetails : search.getFields()) {
                map.put(fieldDetails.getFieldName(), fieldDetails);
            }
            List<String> keysList = new ArrayList<String>(map.keySet());
            checkProperties(keysList);
            return searchKeyValuePair(payment, map, search.getJoinColumns());
        }
        return null;
    }

    protected void checkProperties(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    list.get(i).replace("%20", "").trim().equals("customerId") ||
                    list.get(i).replace("%20", "").trim().equals("rentalId") ||
                    list.get(i).replace("%20", "").trim().equals("staffId") ||
                    list.get(i).replace("%20", "").trim().equals("amount") ||
                    list.get(i).replace("%20", "").trim().equals("paymentDate") ||
                    list.get(i).replace("%20", "").trim().equals("paymentId")
                )
            ) {
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    protected BooleanBuilder searchKeyValuePair(
        QPaymentEntity payment,
        Map<String, SearchFields> map,
        Map<String, String> joinColumns
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if (details.getKey().replace("%20", "").trim().equals("amount")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    NumberUtils.isCreatable(details.getValue().getSearchValue())
                ) builder.and(payment.amount.eq(new BigDecimal(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    NumberUtils.isCreatable(details.getValue().getSearchValue())
                ) builder.and(payment.amount.ne(new BigDecimal(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        NumberUtils.isCreatable(details.getValue().getStartingValue()) &&
                        NumberUtils.isCreatable(details.getValue().getEndingValue())
                    ) builder.and(
                        payment.amount.between(
                            new BigDecimal(details.getValue().getStartingValue()),
                            new BigDecimal(details.getValue().getEndingValue())
                        )
                    ); else if (NumberUtils.isCreatable(details.getValue().getStartingValue())) builder.and(
                        payment.amount.goe(new BigDecimal(details.getValue().getStartingValue()))
                    ); else if (NumberUtils.isCreatable(details.getValue().getEndingValue())) builder.and(
                        payment.amount.loe(new BigDecimal(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("paymentDate")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    payment.paymentDate.eq(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    payment.paymentDate.ne(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (details.getValue().getOperator().equals("range")) {
                    LocalDateTime startLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getStartingValue()
                    );
                    LocalDateTime endLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getEndingValue()
                    );
                    if (startLocalDateTime != null && endLocalDateTime != null) builder.and(
                        payment.paymentDate.between(startLocalDateTime, endLocalDateTime)
                    ); else if (endLocalDateTime != null) builder.and(
                        payment.paymentDate.loe(endLocalDateTime)
                    ); else if (startLocalDateTime != null) builder.and(payment.paymentDate.goe(startLocalDateTime));
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("paymentId")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(payment.paymentId.eq(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(payment.paymentId.ne(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        payment.paymentId.between(
                            Integer.valueOf(details.getValue().getStartingValue()),
                            Integer.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        payment.paymentId.goe(Integer.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        payment.paymentId.loe(Integer.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
        }

        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("customerId")) {
                builder.and(payment.customer.customerId.eq(Integer.parseInt(joinCol.getValue())));
            }
        }
        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("rentalId")) {
                builder.and(payment.rental.rentalId.eq(Integer.parseInt(joinCol.getValue())));
            }
        }
        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("staffId")) {
                builder.and(payment.staff.staffId.eq(Integer.parseInt(joinCol.getValue())));
            }
        }
        return builder;
    }
}
