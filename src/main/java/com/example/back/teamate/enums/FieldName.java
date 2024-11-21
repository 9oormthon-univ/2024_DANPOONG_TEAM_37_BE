package com.example.back.teamate.enums;

public enum FieldName {
        PROJECT("프로젝트"),
        STUDY("스터디");

        private String fieldDisplayName;
        FieldName(String fieldDisplayName) {
                this.fieldDisplayName = fieldDisplayName;
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
}
