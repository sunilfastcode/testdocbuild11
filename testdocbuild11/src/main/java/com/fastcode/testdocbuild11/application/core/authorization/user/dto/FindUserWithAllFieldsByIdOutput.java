package com.fastcode.testdocbuild11.application.core.authorization.user.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindUserWithAllFieldsByIdOutput {

    private String emailAddress;
    private String firstName;
    private Long id;
    private Boolean isActive;
    private Boolean isEmailConfirmed;
    private String lastName;
    private String password;
    private String phoneNumber;
    private String userName;
    private Long userId;
    private Long versiono;
}
