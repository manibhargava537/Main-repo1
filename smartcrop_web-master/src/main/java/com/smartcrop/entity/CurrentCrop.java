package com.smartcrop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name="currentCrop")
@AllArgsConstructor
@NoArgsConstructor
public class CurrentCrop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String surveyNos;

    private String cropType;
    private double totalLandHolding;
    private double netIncome;

    private String waterSource;

    private String regularDischarge;

    private String summerDischarge;

    private String location;


    @Version
    Long version;
}
