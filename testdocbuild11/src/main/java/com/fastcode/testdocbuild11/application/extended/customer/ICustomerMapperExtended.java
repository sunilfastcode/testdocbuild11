package com.fastcode.testdocbuild11.application.extended.customer;

import com.fastcode.testdocbuild11.application.core.customer.ICustomerMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICustomerMapperExtended extends ICustomerMapper {}
