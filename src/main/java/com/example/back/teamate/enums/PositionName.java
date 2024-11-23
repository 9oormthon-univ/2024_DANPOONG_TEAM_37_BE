package com.example.back.teamate.enums;

import java.util.Arrays;

public enum PositionName {
    FRONTEND(1, "프론트엔드"),
    BACKEND(2, "백엔드"),
    DESIGNER(3, "디자이너"),
    AI(4, "인공지능"),
    IOS(5, "iOS"),
    ANDROID(6, "안드로이드"),
    PM(7, "PM"),
    PLANNER(8, "기획자"),
    MARKETER(9, "마케터");

    private final int positionId;
    private String positionDisplayName;

    PositionName(int PositionId, String positionDisplayName) {
        this.positionId = PositionId;
        this.positionDisplayName = positionDisplayName;
    }

    public int getPositionId() {
        return positionId;
    }

    public String getPositionDisplayName() {
        return positionDisplayName;
    }

    public static PositionName fromDatabaseValue(String value) {
        for (PositionName positionName : PositionName.values()) {
            if (positionName.getPositionDisplayName().equalsIgnoreCase(value)) {
                return positionName;
            }
        }
        throw new IllegalArgumentException("Invalid value for FieldName: " + value);
    }

    public static PositionName fromDatabaseValueForInt(int value) {
        return Arrays.stream(PositionName.values())
            .filter(field -> field.getPositionId() == value)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Invalid fieldId: " + value));
    }

    // 프론트엔드 값으로 Enum 찾기
    public static PositionName fromDisplayName(String displayName) {
        for (PositionName positionName : PositionName.values()) {
            if (positionName.positionDisplayName.equalsIgnoreCase(displayName)) {
                return positionName;
            }
        }
        throw new IllegalArgumentException("Invalid display name for PositionName: " + displayName);
    }
}
