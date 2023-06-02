package com.sportyfind.webapi.repositories;

import com.sportyfind.webapi.entities.TeamEntity;
import com.sportyfind.webapi.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Integer> {
    List<TeamEntity> findByCaptainId(Long captainId);
}