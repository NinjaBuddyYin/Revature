package com.ers.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ers_reimbursement")
@Getter
@Setter
public class UserReimbursement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reimb_id")
    private Long id;

    @Column(name = "reimb_amount")
    private BigDecimal reimbAmount;

    @Column(name = "reimb_submitted")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reimbSubmitted;

    @Column(name = "reimb_resolved")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reimbResolved;

    @Column(name = "reimb_status")
    private String reimbStatus;

    @Column(name = "reimb_type")
    private String reimbType;

    @Column(name = "reimb_description")
    private String reimbDescription;


    @Column(name = "reimb_receipt")
    private String reimbReceipt;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "reimb_author")
    private ErsUsers author;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "reimb_resolver")
    private ErsUsers resolver;
}
