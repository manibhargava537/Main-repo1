package com.smartcrop.model;

public enum DocumentType {
    PROFILE("profile"), IDENTITY("identity"), PPB("ppb"), AADHAR("aadhar"), BANK_DETAILS("bank_details"), PAN_CARD("pan_card"), OTHER("other");

    private String value;
    DocumentType(String documentType) {
        this.value = documentType;
    }
    public String getValue() {
        return this.value;
    }
}
