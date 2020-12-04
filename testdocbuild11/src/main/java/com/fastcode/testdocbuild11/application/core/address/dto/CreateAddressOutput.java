package com.fastcode.testdocbuild11.application.core.address.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAddressOutput {

    private String address;
    private String address2;
    private Integer addressId;
    private String district;
    private LocalDateTime lastUpdate;
    private String phone;
    private String postalCode;
    private Short cityId;
    private Integer cityDescriptiveField;
}
