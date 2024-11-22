package com.example.back.teamate.enums;

public enum ModeName {
    ONLINE(1, "온라인"),
    OFFLINE(2, "오프라인");

    private int modeId;
    private String modeDisplayName;
    ModeName(int modeId, String modeDisplayName) {
        this.modeId = modeId;
        this.modeDisplayName = modeDisplayName;
    }

    public int getModeId() {
        return modeId;
    }

    public String getModeDisplayName() {
        return modeDisplayName;
    }

    public static ModeName fromDatabaseValue(String value) {
        for (ModeName modeName : ModeName.values()) {
            if (modeName.name().equalsIgnoreCase(value)) {
                return modeName;
            }
        }
        throw new IllegalArgumentException("Invalid value for FieldName: " + value);
    }

    // 프론트엔드 값으로 Enum 찾기
    public static ModeName fromDisplayName(String displayName) {
        for (ModeName modeName : ModeName.values()) {
            if (modeName.modeDisplayName.equalsIgnoreCase(displayName)) {
                return modeName;
            }
        }
        throw new IllegalArgumentException("Invalid display name for FieldName: " + displayName);
    }
}
