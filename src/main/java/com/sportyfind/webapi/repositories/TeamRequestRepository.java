package com.sportyfind.webapi.repositories;

import com.sportyfind.webapi.entities.TeamRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRequestRepository extends JpaRepository<TeamRequestEntity, Integer> {

    List<TeamRequestEntity> findByTeamId(int teamId);
    TeamRequestEntity findByUserIdAndTeamId(Long user_id, int team_id);
}
