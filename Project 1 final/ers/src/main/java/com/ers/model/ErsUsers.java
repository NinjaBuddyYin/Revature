package com.ers.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ers_users")
@Getter
@Setter
public class ErsUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "ers_username")
    private String ersUserName;

    @Column(name = "ers_password")
    private String ersPassword;

    @Column(name = "user_first_name")
    private String userFirstName;

    @Column(name = "user_last_name")
    private String userLastName;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_role")
    private String userRole;

    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<UserReimbursement> reimbAuthor = new ArrayList<>();

    @OneToMany(
            mappedBy = "resolver",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<UserReimbursement> reimbResolver = new ArrayList<>();
}
