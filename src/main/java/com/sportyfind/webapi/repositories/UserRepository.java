package com.sportyfind.webapi.repositories;

import com.sportyfind.webapi.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    @Query("SELECT u FROM UserEntity u WHERE u.username = ?1 AND u.password = ?2")
    UserEntity findByUsernameAndPassword(String username, String password);
}
