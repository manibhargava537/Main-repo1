package com.smartcrop.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.hibernate.usertype.UserType;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Data
@ToString
@Builder
public class RegistrationModel {
    private String title;
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Special characters and numbers  are not allowed in firstName")
    private String firstName;
    private String middleName;
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Special characters and numbers are not allowed in lastName")
    private String lastName;
    @NotNull
    @NotEmpty(message = "Gender field cannot be empty")
    private String gender;
    private String designation;
    private String defaultPassword;
    @Email
    @NotEmpty
    private String email;
    private String countryCode;
    @NotNull
    private long phoneNumber;
    @NotNull
    @NotEmpty(message = "Country field cannot be empty")
    private String country;
    @NotNull
    @NotEmpty(message = "State cannot be empty" )
    private String state;
    @NotNull
    @NotEmpty(message = "District/City cannot be empty")
    private String districtOrCity;
    private String mandal;
    private String village;
    @NotNull
    @NotEmpty(message = "ZipCode cannot be empty")
    private String zipCode;
    @NotNull
    @NotEmpty(message = "Birth date cannot be empty")
    private String dateOfBirth;
    /**
     * Employee List fields
     */
    private String employeeId;
    private String fullName;
    private Date onBoardDate;
    private String status;

    @NotNull
    private String role;

    private String userId;

    private Long entityId;

    private String surveyNos;

    private String currentCrop;

    private double totalLandHolding;

    private double netIncome;

    private String proposedAreaSurveyNos;

    private double proposedArea;

    private String plantationType;

    private int numberOfPlantsPerAcre;

    private int totalPlants;

    private String waterSource;

    private String normalDischarge;

    private String summerDischarge;

    private String location;

    private String bankName;

    private long accountNumber;

    private String branchName;

    private String ifscCode;

    private String idType;
}