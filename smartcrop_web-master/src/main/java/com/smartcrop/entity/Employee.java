package com.smartcrop.entity;

import com.smartcrop.model.Designation;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "employee")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String designation = Designation.AREA_MANAGER.name();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addressFk")
    private Address address;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "smartCropUserFk")
    private SmartCropUser smartCropUser;

    @Version
    private Long version;

    @OneToMany(mappedBy = "employee")
    private List<SmartCropUserDoc> smartCropUserDocList;
}