package com.smartcrop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailTokenModel {

    private String token;

    private Date createdOn;

    private boolean verified;

    private String password;

    @Email
    @NotEmpty
    @Pattern(regexp = "[a-zA-Z0-9_.@$&!]+", message = "Invalid Email Address")
    private String email;
}
