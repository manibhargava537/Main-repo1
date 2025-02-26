package com.smartcrop.model;

public enum Designation {
    AREA_MANAGER("Area Manager"),
    DATA_QUALITY_CHECK_OFFICER("Data Quality Check Officer"),
    CUSTER_OFFICER("Cluster Officer");

    private String value;

    private Designation(String value) {
        this.value = value;
    }
}
