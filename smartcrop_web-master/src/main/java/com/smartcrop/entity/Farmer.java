package com.smartcrop.entity;

import com.smartcrop.entity.Address;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Data
@Builder
@Entity
@Table(name="Farmer")
@AllArgsConstructor
@NoArgsConstructor
public class Farmer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String careTaker;
    private Long aadharNumber;
    private String caste;
    private  String ppbNo;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addressFk")
    private Address address;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "smartCropUserFk")
    private SmartCropUser smartCropUser;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currentCropFk")
    private CurrentCrop currentCrop;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proposedAreaFk")
    private ProposedArea proposedArea;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bankDetailsFk")
    private BankDetails bankDetails;
    @OneToMany(mappedBy = "farmer")
    private List<SmartCropUserDoc> smartCropUserDocList;
    @Version
    Long version;
}