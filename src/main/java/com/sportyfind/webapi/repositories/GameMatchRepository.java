package com.sportyfind.webapi.repositories;

import com.sportyfind.webapi.entities.GameMatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameMatchRepository extends JpaRepository<GameMatchEntity, Integer> {

    public List<GameMatchEntity> findAllByGameType(int gameType);

    // get all game match by team id, team id can be team A or team B
    @Query("SELECT g FROM GameMatchEntity g WHERE g.teamA.id = :teamId OR g.teamB.id = :teamId")
    public List<GameMatchEntity> findAllByTeamId(int teamId);

}
