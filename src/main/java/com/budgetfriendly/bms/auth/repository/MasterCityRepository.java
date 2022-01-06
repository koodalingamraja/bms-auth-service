package com.budgetfriendly.bms.auth.repository;

import com.budgetfriendly.bms.auth.entity.MasterCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MasterCityRepository extends JpaRepository<MasterCity, Long> {

    Optional<MasterCity> findByIdAndStatusTrue(Long id);
}
