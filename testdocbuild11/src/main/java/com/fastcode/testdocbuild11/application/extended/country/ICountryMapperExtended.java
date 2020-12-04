package com.fastcode.testdocbuild11.application.extended.country;

import com.fastcode.testdocbuild11.application.core.country.ICountryMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICountryMapperExtended extends ICountryMapper {}
