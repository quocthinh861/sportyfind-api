package com.sportyfind.webapi.repositories;

import com.sportyfind.webapi.entities.TeamRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TeamRequestRepository extends JpaRepository<TeamRequestEntity, Integer> {

    @Query(value = "SELECT * FROM team_request WHERE team_id = ?1 AND status = 1", nativeQuery = true)
    List<TeamRequestEntity> findAllCreatedRequestByTeamId(int teamId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM team_request WHERE user_id = ?1 AND team_id = ?2", nativeQuery = true)
    void deleteByUserIdAndTeamId(Long userId, int teamId);

    List<TeamRequestEntity> findByTeamId(int teamId);
    TeamRequestEntity findByUserIdAndTeamId(Long user_id, int team_id);



}
