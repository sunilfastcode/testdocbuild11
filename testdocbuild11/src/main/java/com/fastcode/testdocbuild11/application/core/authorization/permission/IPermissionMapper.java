package com.fastcode.testdocbuild11.application.core.authorization.permission;

import com.fastcode.testdocbuild11.application.core.authorization.permission.dto.*;
import com.fastcode.testdocbuild11.domain.core.authorization.permission.PermissionEntity;
import java.time.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IPermissionMapper {
    PermissionEntity createPermissionInputToPermissionEntity(CreatePermissionInput permissionDto);

    CreatePermissionOutput permissionEntityToCreatePermissionOutput(PermissionEntity entity);

    PermissionEntity updatePermissionInputToPermissionEntity(UpdatePermissionInput permissionDto);

    UpdatePermissionOutput permissionEntityToUpdatePermissionOutput(PermissionEntity entity);

    FindPermissionByIdOutput permissionEntityToFindPermissionByIdOutput(PermissionEntity entity);

    FindPermissionByNameOutput permissionEntityToFindPermissionByNameOutput(PermissionEntity entity);
}
