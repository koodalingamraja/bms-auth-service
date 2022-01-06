package com.budgetfriendly.bms.auth.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "email_id")
    private String email;

    @Column(name = "mobile_number")
    private String mobile;

    @Column(name = "date_of_berth")
    private Date dob;

    @Column(name = "gender")
    private String gender;

    @Column(name = "active_status")
    private Boolean status;

    @Column(name = "created_at")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "state_id_fk")
    private MasterState masterState;

    @ManyToOne
    @JoinColumn(name = "city_id_fk")
    private MasterCity masterCity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public MasterState getMasterState() {
        return masterState;
    }

    public void setMasterState(MasterState masterState) {
        this.masterState = masterState;
    }

    public MasterCity getMasterCity() {
        return masterCity;
    }

    public void setMasterCity(MasterCity masterCity) {
        this.masterCity = masterCity;
    }
}
