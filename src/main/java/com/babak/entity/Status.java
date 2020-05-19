package com.babak.entity;

public enum Status {

    ACCEPTED("ACCEPTED"),
    CONFIRMED("CONFIRMED"),
    FORM("FORM"),
    SENT("SENT"),
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED");

    private String description;

    private Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
