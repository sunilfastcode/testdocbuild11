package com.fastcode.testdocbuild11.application.core.authorization.userpermission.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserpermissionInput {

    @NotNull(message = "permissionId Should not be null")
    private Long permissionId;

    private Boolean revoked;

    @NotNull(message = "userId Should not be null")
    private Long userId;
}
