package com.fastcode.testdocbuild11.security;

import com.fastcode.testdocbuild11.domain.core.authorization.rolepermission.IRolepermissionManager;
import com.fastcode.testdocbuild11.domain.core.authorization.rolepermission.RolepermissionEntity;
import com.fastcode.testdocbuild11.domain.core.authorization.user.UserEntity;
import com.fastcode.testdocbuild11.domain.core.authorization.userpermission.IUserpermissionManager;
import com.fastcode.testdocbuild11.domain.core.authorization.userpermission.UserpermissionEntity;
import com.fastcode.testdocbuild11.domain.core.authorization.userrole.IUserroleManager;
import com.fastcode.testdocbuild11.domain.core.authorization.userrole.UserroleEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.Cookie;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    @Qualifier("rolepermissionManager")
    @NonNull
    private final IRolepermissionManager rolepermissionManager;

    @Qualifier("userroleManager")
    @NonNull
    private final IUserroleManager userroleManager;

    @Qualifier("userpermissionManager")
    @NonNull
    private final IUserpermissionManager userpermissionManager;

    public List<String> getAllPermissionsFromUserAndRole(UserEntity user) {
        List<String> permissions = new ArrayList<>();
        List<UserroleEntity> ure = userroleManager.getUserrolesByUserId(user.getId());

        for (UserroleEntity ur : ure) {
            List<RolepermissionEntity> srp = rolepermissionManager.getRolepermissionsByRoleId(ur.getRoleId());
            for (RolepermissionEntity item : srp) {
                permissions.add(item.getPermission().getName());
            }
        }

        List<UserpermissionEntity> spe = userpermissionManager.getUserpermissionsByUserId(user.getId());

        for (UserpermissionEntity up : spe) {
            if (permissions.contains(up.getPermission().getName()) && (up.getRevoked() != null && up.getRevoked())) {
                permissions.remove(up.getPermission().getName());
            }
            if (!permissions.contains(up.getPermission().getName()) && (up.getRevoked() == null || !up.getRevoked())) {
                permissions.add(up.getPermission().getName());
            }
        }

        return permissions.stream().distinct().collect(Collectors.toList());
    }

    public String getTokenFromCookies(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(SecurityConstants.HEADER_STRING_AUTHENTICATION)) {
                    return c.getValue();
                }
            }
        }
        return null;
    }

    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional
            .ofNullable(securityContext.getAuthentication())
            .map(
                authentication -> {
                    if (authentication.getPrincipal() instanceof UserDetails) {
                        UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                        return springSecurityUser.getUsername();
                    } else if (authentication.getPrincipal() instanceof String) {
                        return (String) authentication.getPrincipal();
                    }
                    return null;
                }
            );
    }
}
