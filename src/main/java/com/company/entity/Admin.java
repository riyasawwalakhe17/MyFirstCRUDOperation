package com.company.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adm_seq")
    @SequenceGenerator(name = "adm_seq", sequenceName = "admin_sequence", initialValue = 100001, allocationSize = 1)
    private Long id;

    private String Fname;

    private String Lname;

    private String designation;

    private Long mob_no;

}
