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
@Table(name = "EmailTokenVerification")
public class EmailVerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    private Date createdOn;

    private String email;
}
