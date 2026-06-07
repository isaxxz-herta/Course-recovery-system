package com.crs.model;

public enum Role {
    STUDENT("Student"),
    ADMINISTRATOR("Administrator"),
    ACADEMIC_OFFICER("Academic Officer"),
    INSTRUCTOR("Instructor");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Role fromString(String role) {
        for (Role r : Role.values()) {
            if (r.displayName.equalsIgnoreCase(role) || r.name().equalsIgnoreCase(role)) {
                return r;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + role);
    }
}
