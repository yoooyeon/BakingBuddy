package com.coco.bakingbuddy.ingredient.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum Unit {
    CUP("컵"),
    TABLESPOON("큰 숟가락"),
    TEASPOON("작은 숟가락"),
    GRAM("g"),
    KILOGRAM("kg"),
    LITER("L"),
    MILLILITER("mL"),
    PIECE("개")
    ;

    private final String displayName;
    private static final Map<String, Unit> lookup = new HashMap<>();

    static {
        for (Unit unit : Unit.values()) {
            lookup.put(unit.name().toLowerCase(), unit);
            lookup.put(unit.displayName.toLowerCase(), unit);
            lookup.put(unit.displayName.toUpperCase(), unit);
        }
        // 추가적인 매핑을 등록할 수 있습니다.
        lookup.put("g", GRAM);
        lookup.put("그램", GRAM);
        lookup.put("그람", GRAM);
        lookup.put("kg", KILOGRAM);
        lookup.put("킬로그램", KILOGRAM);
        lookup.put("킬로", KILOGRAM);
        lookup.put("l", LITER);
        lookup.put("리터", LITER);
        lookup.put("ml", MILLILITER);
        lookup.put("밀리리터", MILLILITER);
        lookup.put("밀리", MILLILITER);
    }

    // 생성자
    Unit(String displayName) {
        this.displayName = displayName;
    }

    // 단위의 표시 이름을 반환하는 메서드
    public String getDisplayName() {
        return displayName;
    }

    // 문자열 값을 Unit enum 값으로 변환하는 메서드
    public static Unit from(String unitStr) {
        Unit unit = lookup.get(unitStr.toLowerCase());
        if (unit == null) {
            throw new IllegalArgumentException("Invalid unit: " + unitStr);
        }
        return unit;
    }

    // 모든 Unit enum의 displayName을 반환하는 메서드
    public static List<String> getAllDisplayNames() {
        return Arrays.stream(Unit.values())
                .map(Unit::getDisplayName)
                .collect(Collectors.toList());
    }
}
