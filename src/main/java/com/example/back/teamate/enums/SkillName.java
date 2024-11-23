package com.example.back.teamate.enums;

public enum SkillName {
    JAVA(1, "Java"),
    SPRING(2, "Spring"),
    MYSQL(3, "MySQL"),
    MONGODB(4, "MongoDB"),
    REACT(5, "React"),
    VUE(6, "Vue"),
    SQLITE(7, "SQLite"),
    NODEJS(8, "Nodejs"),
    C(9, "C"),
    AWS(10, "AWS"),
    FIGMA(11, "Figma"),
    GIT(12, "Git"),
    TYPESCRIPT(13, "TypeScript"),
    KOTLIN(14, "Kotlin"),
    EXPRESS(15, "Express"),
    PYTHON(16, "Python"),
    FLUTTER(17, "Flutter"),
    SWIFT(18, "Swift"),
    REACTNATIVE(19, "ReactNative"),
    UNITY(20, "Unity"),
    DOCKER(21, "Docker"),
    FIREBASE(22, "Firebase"),
    DJANGO(23, "Django"),
    NEXTJS(24, "Nextjs"),
    GO(25, "Go"),
    GRAPHQL(26, "GhaphQL"),
    KUBERNETES(27, "Kubernetes"),
    SVELTE(28, "Svelte"),
    JAVASCRIPT(29, "JavaScript");

    private final int skillId;
    private String skillDisplayName;

    SkillName(int skillId, String skillDisplayName) {
        this.skillId = skillId;
        this.skillDisplayName = skillDisplayName;
    }

    public int getSkillId() {
        return skillId;
    }

    public String getSkillDisplayName() {
        return skillDisplayName;
    }

    public static SkillName fromDatabaseValue(String value) {
        for (SkillName skillName : SkillName.values()) {
            if (skillName.name().equalsIgnoreCase(value)) {
                return skillName;
            }
        }
        throw new IllegalArgumentException("Invalid value for FieldName: " + value);
    }
}
