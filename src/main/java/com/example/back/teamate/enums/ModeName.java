package com.example.back.teamate.enums;

public enum ModeName {
    ONLINE("온라인"),
    OFFLINE("오프라인");

    private String modeDisplayName;
    ModeName(String modeDisplayName) {
        this.modeDisplayName = modeDisplayName;
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
}
