package com.smartcrop.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "EmailToken")
public class EmailToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    private Date createdOn;

    private boolean verified;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "smartCropUserFk")
    private SmartCropUser smartCropUser;
}
