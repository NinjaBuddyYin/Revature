package com.ers.dto;

import com.ers.model.ErsUsers;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ManagerReimbResponse {
    private Long id;
    private BigDecimal reimbAmount;
    private Date reimbSubmitted;
    private Date reimbResolved;
    private String reimbStatus;
    private String reimbType;
    private String reimbReceipt;
    private String ersUserName;
}
