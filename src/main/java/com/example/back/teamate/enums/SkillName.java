package com.example.back.teamate.enums;

public enum SkillName {
    JAVA("Java"),
    SPRING("Spring"),
    MYSQL("MySQL"),
    MONGODB("MongoDB"),
    REACT("React"),
    VUE("Vue"),
    SQLITE("SQLite"),
    NODEJS("Nodejs"),
    C("C"),
    AWS("AWS"),
    FIGMA("Figma"),
    GIT("Git"),
    TYPESCRIPT("TypeScript"),
    KOTLIN("Kotlin"),
    EXPRESS("Express"),
    PYTHON("Python"),
    FLUTTER("Flutter"),
    SWIFT("Swift"),
    REACTNATIVE("ReactNative"),
    UNITY("Unity"),
    DOCKER("Docker"),
    FIREBASE("Firebase"),
    DJANGO("Django"),
    NEXTJS("Nextjs"),
    GO("Go"),
    GRAPHQL("GhaphQL"),
    KUBERNETES("Kubernetes"),
    SVELTE("Svelte"),
    JAVASCRIPT("JavaScript");

    private String skillDisplayName;
    SkillName(String skillDisplayName) {
        this.skillDisplayName = skillDisplayName;
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
