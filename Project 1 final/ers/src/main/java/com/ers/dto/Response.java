package com.ers.dto;

import com.ers.model.ErsUsers;
import com.ers.model.UserReimbursement;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private Long userId;
    private String message;
    private ErsUsers user;
    private String userName;
    private  String email;
    private String role;
    private String token;
    private List<UserReimbursement> reimbursement;
}
