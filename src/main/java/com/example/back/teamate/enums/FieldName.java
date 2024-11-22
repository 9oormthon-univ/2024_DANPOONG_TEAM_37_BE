package com.example.back.teamate.enums;

public enum FieldName {
        PROJECT(1, "프로젝트"),
        STUDY(2, "스터디");

        private int fieldId;
        private String fieldDisplayName;
        FieldName(int fieldId, String fieldDisplayName) {
                this.fieldId = fieldId;
                this.fieldDisplayName = fieldDisplayName;
        }

        public int getFieldId() {
                return fieldId;
        }

        public String getFieldDisplayName() {
                return fieldDisplayName;
        }
        public static FieldName fromDatabaseValue(String value) {
                for (FieldName fieldName : FieldName.values()) {
                        if (fieldName.name().equalsIgnoreCase(value)) {
                                return fieldName;
                        }
                }
                throw new IllegalArgumentException("Invalid value for FieldName: " + value);
        }

        // 프론트엔드 값으로 Enum 찾기
        public static FieldName fromDisplayName(String displayName) {
                for (FieldName fieldName : FieldName.values()) {
                        if (fieldName.fieldDisplayName.equalsIgnoreCase(displayName)) {
                                return fieldName;
                        }
                }
                throw new IllegalArgumentException("Invalid display name for FieldName: " + displayName);
        }
}
