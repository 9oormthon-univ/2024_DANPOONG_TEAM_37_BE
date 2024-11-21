package com.example.back.teamate.enums;

public enum PositionName {
    FRONTEND("프론트엔드"),
    BACKEND("백엔드"),
    DESIGNER("디자이너"),
    AI("인공지능"),
    IOS("iOS"),
    ANDROID("안드로이드"),
    PM("PM"),
    PLANNER("기획자"),
    MARKETER("마케터");

    private String positionDisplayName;
    PositionName(String positionDisplayName) {
        this.positionDisplayName = positionDisplayName;
    }

    public String getPositionDisplayName() {
        return positionDisplayName;
    }

    public static PositionName fromDatabaseValue(String value) {
        for (PositionName positionName : PositionName.values()) {
            if (positionName.name().equalsIgnoreCase(value)) {
                return positionName;
            }
        }
        throw new IllegalArgumentException("Invalid value for FieldName: " + value);
    }
}
