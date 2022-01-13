package com.budgetfriendly.bms.auth.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_role_mapping")
public class UserRoleMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id_fk", referencedColumnName = "id")
    private Users users;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "master_role_id_fk", referencedColumnName = "id")
    private MasterRole masterRole;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "created_at")
    private Date createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public MasterRole getMasterRole() {
        return masterRole;
    }

    public void setMasterRole(MasterRole masterRole) {
        this.masterRole = masterRole;
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
}
