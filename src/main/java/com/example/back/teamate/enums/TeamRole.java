package com.example.back.teamate.enums;

public enum TeamRole {
	TEAM_LEADER("팀장"),
	TEAM_MEMBER("팀원");

	private final String name;

	TeamRole(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}