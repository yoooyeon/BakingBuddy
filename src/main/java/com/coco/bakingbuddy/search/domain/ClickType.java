package com.coco.bakingbuddy.search.domain;


import com.coco.bakingbuddy.ingredient.domain.Unit;

import java.util.HashMap;
import java.util.Map;

public enum ClickType {
    RECIPE("RECIPE"),
    PRODUCT("PRODUCT"),
    EDITOR("EDITOR");

    private final String displayName;
    private static final Map<String, ClickType> lookup = new HashMap<>();

    static {
        for (ClickType tyoe : ClickType.values()) {
            lookup.put(tyoe.name().toLowerCase(), tyoe);
            lookup.put(tyoe.displayName.toLowerCase(), tyoe);
            lookup.put(tyoe.displayName.toUpperCase(), tyoe);
        }
    }

    ClickType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    // 문자열 값을 Unit enum 값으로 변환하는 메서드
    public static ClickType from(String unitStr) {
        ClickType unit = lookup.get(unitStr.toLowerCase());
        if (unit == null) {
            throw new IllegalArgumentException("Invalid click type: " + unitStr);
        }
        return unit;
    }

}