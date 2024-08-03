package com.coco.bakingbuddy.user.service;

import java.util.HashMap;
import java.util.Map;

public enum RoleType {
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN"),
    ROLE_EDITOR("EDITOR"),
    ROLE_SELLER("SELLER");

    private final String displayName;
    private static final Map<String, RoleType> lookup = new HashMap<>();

    RoleType(String displayName) {
        this.displayName = displayName;
    }

    public static RoleType from(String roleStr) {
        RoleType role = lookup.get(roleStr.toLowerCase());
        if (role == null) {
            throw new IllegalArgumentException("Invalid role type: " + roleStr);
        }
        return role;
    }

    static {
        for (RoleType unit : RoleType.values()) {
            lookup.put(unit.name().toLowerCase(), unit);
            lookup.put(unit.displayName.toLowerCase(), unit);
            lookup.put(unit.displayName.toUpperCase(), unit);
        }
    }
}
