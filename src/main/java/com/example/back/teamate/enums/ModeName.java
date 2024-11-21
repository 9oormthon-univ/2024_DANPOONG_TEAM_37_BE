package com.example.back.teamate.enums;

public enum ModeName {
    ONLINE("온라인"),
    OFFLINE("오프라인");
    private final String message;

    ModeName(String message) {
        this.message = message;
    }
}
