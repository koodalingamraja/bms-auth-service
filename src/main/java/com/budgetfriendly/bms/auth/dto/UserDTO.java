package com.budgetfriendly.bms.auth.dto;

import java.util.Date;

public class UserDTO {

    private Long id;
    private String userName;
    private String password;
    private String email;
    private String mobile;
    private Date dob;
    private String gender;
    private Boolean status;
    private Date createdAt;
    private MasterCityDTO masterCityDTO;
    private MasterRoleDTO masterRoleDTO;

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

    public MasterCityDTO getMasterCityDTO() {
        return masterCityDTO;
    }

    public void setMasterCityDTO(MasterCityDTO masterCityDTO) {
        this.masterCityDTO = masterCityDTO;
    }

    public MasterRoleDTO getMasterRoleDTO() {
        return masterRoleDTO;
    }

    public void setMasterRoleDTO(MasterRoleDTO masterRoleDTO) {
        this.masterRoleDTO = masterRoleDTO;
    }
}
