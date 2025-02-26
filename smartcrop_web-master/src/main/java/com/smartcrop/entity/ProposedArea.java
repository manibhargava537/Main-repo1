package com.smartcrop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name="proposedArea")
@AllArgsConstructor
@NoArgsConstructor
public class ProposedArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String surveyNos;
    private double proposedArea;
    private String plantationType;
    private int plantsPerAcre;
    private int totalPlants;
 /*   private String country;
    private String state;
    private String mandal;
    private String village;*/
    private Boolean soilTested;
    private String soilType;
    @Version
    Long version;
}
