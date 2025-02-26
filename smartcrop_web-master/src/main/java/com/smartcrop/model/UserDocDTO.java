package com.smartcrop.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class UserDocDTO {
    private String documentType;
    private String description;

    private String docExtension;
    private byte[] document;
}
