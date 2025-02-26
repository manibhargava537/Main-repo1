package com.smartcrop.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "smartCropUserDoc")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SmartCropUserDoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // 1 employee -- n documents
    // employeeId is the foreignKey in SmartCropUserDoc

    private String documentType;

    private String docExtension;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] document;

    private String documentDescription;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "farmer_id")
    private Farmer farmer;

}
