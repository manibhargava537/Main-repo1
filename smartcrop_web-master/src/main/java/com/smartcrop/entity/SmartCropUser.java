package com.smartcrop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name = "smartcrop_user",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"userId"})})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmartCropUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotEmpty
    private String userId;
    private String title;
    @NotNull
    @NotEmpty
    private String firstName;
    @NotNull
    @NotEmpty
    private String lastName;
    private String middleName;
    @NotNull
    @NotEmpty
    private String gender;
    @NotNull
    @NotEmpty
    private String defaultPassword;
    private Date dateOfBirth;
    private String password;
    @Email
    private String email;
    @Column(name = "username")
    private String userName;
    private String countryCode;
    @NotNull
    private long phoneNumber;
    private Date createOn;
    private Date modifiedOn;
    private String role;
    private String status;
    private String remark;
    @Version
    Long version;
}
