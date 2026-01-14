package com.backendlld.hospitalManagement.security;

import com.backendlld.hospitalManagement.model.enums.PermissionType;
import com.backendlld.hospitalManagement.model.enums.RoleType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class RolePermissionMapping {

    private static final Map<RoleType, Set<PermissionType>> map = Map.of(
            RoleType.PATIENT,Set.of(PermissionType.PATIENT_READ, PermissionType.PATIENT_WRITE, PermissionType.APPOINTMENT_READ, PermissionType.APPOINTMENT_WRITE),
            RoleType.DOCTOR,Set.of(PermissionType.PATIENT_READ, PermissionType.APPOINTMENT_READ, PermissionType.APPOINTMENT_WRITE),
            RoleType.ADMIN,Set.of(PermissionType.PATIENT_READ, PermissionType.PATIENT_WRITE, PermissionType.APPOINTMENT_READ, PermissionType.APPOINTMENT_WRITE, PermissionType.APPOINTMENT_DELETE, PermissionType.USER_MANAGE, PermissionType.REPORTS_VIEW)
    );

    public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(RoleType role) {
        return map.get(role).stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
