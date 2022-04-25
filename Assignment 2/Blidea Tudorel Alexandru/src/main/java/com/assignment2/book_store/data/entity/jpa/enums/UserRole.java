package com.assignment2.book_store.data.entity.jpa.enums;

public enum UserRole {
    ADMIN("ADMIN"),
    CUSTOMER("CUSTOMER");
    private final String name;

    private UserRole(String name) {
        this.name = name;
    }

    public boolean equalsName(String otherName) {
        return this.name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }

}
