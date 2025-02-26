package com.smartcrop.model;

public enum UserStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    PENDING("Pending"),
    REJECTED("Reject"),
    //REFER_BACK("Refer Back"),
    APPROVED("Approve"),

    REFER_BACK("Refer Back");
    private String status;

    UserStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
