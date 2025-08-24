package com.company.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Entity
@ToString
@Data
@Table(name = "developer")
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)// only in responses, not in request body
    private int id;

    @NotBlank(message = "First name is required")
    @Column(name = "first_name", nullable = false)

    private String fName;

    @NotBlank(message = "Last name is required")
    @Column(name = "last_name", nullable = false)

    private String lName;

    private int age;

    private String gender;

    private String city;

    private long salary;

    private String developerId;

    @Column(name = "yob")
    private int yOB;

    // ✅ new field for DOB
    private LocalDateTime dob;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public String getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(String developerId) {
        this.developerId = developerId;
    }

    public int getyOB() {
        return yOB;
    }

    public void setyOB(int yOB) {
        this.yOB = yOB;
    }

    public LocalDateTime getDob() {
        return dob;
    }

    public void setDob(LocalDateTime dob) {
        this.dob = dob;
        if (dob != null) {
            this.age = Period.between(dob.toLocalDate(), LocalDate.now()).getYears();
        }
    }
}
