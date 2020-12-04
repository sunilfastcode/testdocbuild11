package com.fastcode.testdocbuild11.application.core.authorization.user.dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindUserByNameOutput {

    private Long id;
    private Boolean isActive;
    private Boolean shouldChangePasswordOnNextLogin;
    private String lastName;
    private int accessFailedCount;
    private String emailAddress;
    private Boolean isEmailConfirmed;
    private Boolean isLockoutEnabled;
    private Boolean isPhoneNumberConfirmed;
    private Date lastLoginTime;
    private Date lockoutEndDateUtc;
    private String firstName;
    private String phoneNumber;
    private Long profilePictureId;
    private String authenticationSource;
    private String userName;
}
