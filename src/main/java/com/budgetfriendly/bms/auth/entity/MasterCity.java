package com.budgetfriendly.bms.auth.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "master_city")
public class MasterCity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "city_name")
    private String cityName;
    @Column(name = "city_code")
    private String cityCode;
    @Column(name = "status")
    private Boolean status;
    @Column(name = "created_at")
    private Date createdAt;
    @ManyToOne
    @JoinColumn(name = "master_state_id_fk")
    private MasterState masterState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
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
}
