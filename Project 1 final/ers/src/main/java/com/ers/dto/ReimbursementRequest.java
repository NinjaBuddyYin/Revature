package com.ers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.File;
import java.math.BigDecimal;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReimbursementRequest {
    private Long userId;
    private BigDecimal reimbAmount;
    private String reimbType;
    private String reimbDescription;
    private File reimbReceipt;
}
