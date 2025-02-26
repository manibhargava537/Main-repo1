package com.smartcrop.model;

public enum UserType {
    EMPLOYEE("Employee"),
    FARMER("Farmer"),
    NURSERY("Nursery"),

    OTHER("Other");
    private String value;

    UserType(String value){
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
