package com.smartcrop.model;

import com.poiji.annotation.ExcelCellName;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FarmerModel {

    @NotEmpty
    @NotNull
    @ExcelCellName("title")
    private String title;
    @NotEmpty
    @NotEmpty
    @ExcelCellName("firstName")
    private String firstName;

    @ExcelCellName("middleName")
    private String middleName;
    @NotEmpty
    @NotEmpty
    @ExcelCellName("lastName")
    private String lastName;

    @ExcelCellName("gender")
    private String gender;

    @NotNull
    @NotEmpty(message = "Birth date cannot be empty")
    @ExcelCellName("dateOfBirth")
    private String dateOfBirth;
    @NotNull
    @NotEmpty
    @ExcelCellName("careTaker")
    private String careTaker;
    @ExcelCellName("aadharNumber")
    private long aadharNumber;
    @NotNull
    @NotEmpty
    @ExcelCellName("caste")
    private String caste;
    @NotNull
    @ExcelCellName("phoneNumber")
    private long phoneNumber;
    @ExcelCellName("email")
    private String email;
    @NotNull
    @NotEmpty
    @ExcelCellName("proposedAreaCountry")
    private String proposedAreaCountry;
    @NotNull
    @NotEmpty
    @ExcelCellName("proposedAreaState")
    private String proposedAreaState;
    @NotNull
    @NotEmpty
    @ExcelCellName("proposedAreaDistrict")
    private String proposedAreaDistrict;
    @NotNull
    @NotEmpty
    @ExcelCellName("proposedAreaMandal")
    private String proposedAreaMandal;
    @NotNull
    @NotEmpty
    @ExcelCellName("proposedAreaVillage")
    private String proposedAreaVillage;

    @NotNull
    @NotEmpty
    @ExcelCellName("zipCode")
    private String zipCode;
    @ExcelCellName("totalLandHoldingInAcres")
    private double totalLandHoldingInAcres;
    @NotNull
    @NotEmpty
    @ExcelCellName("soilType")
    private String soilType;
    @NotNull
    @NotEmpty
    @ExcelCellName("ppbNo")
    private String ppbNo;
    @ExcelCellName("soilTested")
    private boolean soilTested;
    @NotNull
    @NotEmpty
    @ExcelCellName("proposedAreaSurveyNos")
    private String proposedAreaSurveyNos;

    @ExcelCellName("proposedAreaInAcres")
    private double proposedAreaInAcres;
    @NotNull
    @NotEmpty
    @ExcelCellName("currentCropSurveyNos")
    private String currentCropSurveyNos;
    @NotNull
    @NotEmpty
    @ExcelCellName("plantationMethod")
    private String plantationMethod;
    @ExcelCellName("plantsPerAcre")
    private int plantsPerAcre;
    @ExcelCellName("totalPlants")
    private int totalPlants;
    @NotNull
    @NotEmpty
    @ExcelCellName("currentCrop")
    private String currentCrop;
    @ExcelCellName("netIncome")
    private double netIncome;
    @NotNull
    @NotEmpty
    @ExcelCellName("waterSource")
    private String waterSource;

    @ExcelCellName("normalDischarge")
    private String normalDischarge;
    @ExcelCellName("summerDischarge")
    private String summerDischarge;
    @ExcelCellName("location")
    private String location;
    @ExcelCellName("bankName")
    private String bankName;
    @ExcelCellName("accountNumber")
    private long accountNumber;
    @ExcelCellName("branchName")
    private String branchName;
    @ExcelCellName("ifscCode")
    private String ifscCode;
}
