package com.ers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAuthRequest {
    @NotEmpty
    @Size(min = 2, message = "Username should have at least 2 characters")
    private String ersUserName;
    @NotEmpty
    @Size(min = 6,message = "Password cannot be less than 6 characters")
    private String ersPassword;
    private String userFirstName;
    private String userLastName;
    @NotEmpty
    @Email
    private String userEmail;
    private String userRole;
}
