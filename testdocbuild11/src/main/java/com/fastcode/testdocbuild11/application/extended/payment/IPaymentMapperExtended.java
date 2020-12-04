package com.fastcode.testdocbuild11.application.extended.payment;

import com.fastcode.testdocbuild11.application.core.payment.IPaymentMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IPaymentMapperExtended extends IPaymentMapper {}
