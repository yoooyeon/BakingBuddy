package com.coco.bakingbuddy.recipe.domain;

public enum Unit {
    CUP("컵"),
    TABLESPOON("큰 숟가락"),
    TEASPOON("작은 숟가락"),
    GRAM("g"),
    KILOGRAM("kg"),
    LITER("L"),
    MILLILITER("mL");

    private final String displayName;

    // 생성자
    Unit(String displayName) {
        this.displayName = displayName;
    }

    // 단위의 표시 이름을 반환하는 메서드
    public String getDisplayName() {
        return displayName;
    }
}
