package com.example.back.teamate.enums;

public enum MatchStatus {
    PENDING("가입대기"),
    ACCEPTED("가입완료"),
    REJECTED("거절됨");

    private String matchDisplayName;
    MatchStatus(String matchDisplayName) {
        this.matchDisplayName = matchDisplayName;
    }

    public String getMatchDisplayName() {
        return matchDisplayName;
    }

    public static MatchStatus fromDatabaseValue(String value) {
        for (MatchStatus matchStatus : MatchStatus.values()) {
            if (matchStatus.name().equalsIgnoreCase(value)) {
                return matchStatus;
            }
        }
        throw new IllegalArgumentException("Invalid value for FieldName: " + value);
    }
}
