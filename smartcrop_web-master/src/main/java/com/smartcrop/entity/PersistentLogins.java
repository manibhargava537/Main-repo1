package com.smartcrop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="persistent_logins")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersistentLogins {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="username")
    private String userName;
    private String  series;
    private String token;

    @Column(name="last_used")
    private Date lastUsed;


}
