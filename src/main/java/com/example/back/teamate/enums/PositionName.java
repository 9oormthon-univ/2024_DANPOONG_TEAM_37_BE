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

    private String message;
    PositionName(String message) {
        this.message = message;
    }

}
