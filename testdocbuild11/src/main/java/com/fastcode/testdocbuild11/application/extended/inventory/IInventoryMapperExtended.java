package com.fastcode.testdocbuild11.application.extended.inventory;

import com.fastcode.testdocbuild11.application.core.inventory.IInventoryMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IInventoryMapperExtended extends IInventoryMapper {}
