package com.example.back.teamate.enums;

public enum EventType {
	PROPOSITION("제안"),
	MATCH_STATUS("매칭 상태");

	private final String name;

	EventType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
