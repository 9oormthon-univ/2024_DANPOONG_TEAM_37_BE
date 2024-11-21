package com.example.back.teamate.enums;

public enum MatchStatus {
    PENDING("가입대기"),
    ACCEPTED("가입완료"),
    REJECTED("거절됨");

    private final String message;

    MatchStatus(String message) {
        this.message = message;
    }
}
