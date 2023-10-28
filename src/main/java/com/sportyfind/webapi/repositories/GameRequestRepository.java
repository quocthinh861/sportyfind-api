package com.sportyfind.webapi.repositories;

import com.sportyfind.webapi.entities.GameMatchEntity;
import com.sportyfind.webapi.entities.GameRequestEntity;
import com.sportyfind.webapi.entities.TeamRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface GameRequestRepository extends JpaRepository<GameRequestEntity, Integer> {

    @Query(value = "SELECT * FROM game_request WHERE team_id = ?1 AND game_id = ?2", nativeQuery = true)
    GameRequestEntity findByTeamIdAndGameId(int teamId, int gameId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM game_request WHERE team_id = ?1 AND game_id = ?2", nativeQuery = true)
    void deleteByTeamIdAndGameId(int teamId, int gameId);

    List<GameRequestEntity> findByGameId(int gameId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM game_request WHERE game_id = ?1", nativeQuery = true)
    void deleteByGameId(int gameId);
}
