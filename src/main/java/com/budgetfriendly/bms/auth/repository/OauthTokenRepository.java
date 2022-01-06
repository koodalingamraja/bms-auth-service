package com.budgetfriendly.bms.auth.repository;

import com.budgetfriendly.bms.auth.entity.OauthTokenDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OauthTokenRepository extends JpaRepository<OauthTokenDetail , Long> {

    public List<OauthTokenDetail> findFirstByUsersIdOrderByCreatedAtDesc(@Param(value = "userIdFk") long userIdFk);

}
