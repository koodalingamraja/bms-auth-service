package com.budgetfriendly.bms.auth.repository;

import com.budgetfriendly.bms.auth.entity.MasterRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterRoleRepository extends JpaRepository<MasterRole, Long> {
}
