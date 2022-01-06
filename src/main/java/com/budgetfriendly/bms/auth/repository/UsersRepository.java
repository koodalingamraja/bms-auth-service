package com.budgetfriendly.bms.auth.repository;

import com.budgetfriendly.bms.auth.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query("select user from Users as user where user.userName = :userName")
    Users getUserByUserName(@Param("userName") String userName);

    Users findByUserName(String userName);

    @Query("update Users as users set users.status=0 where users.id=:userId")
    int updateBlockUser(@Param(value = "userId") long userId);
}
