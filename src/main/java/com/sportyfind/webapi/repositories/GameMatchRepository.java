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

    // get all game match by team id, team id can be team A or team B
    @Query("SELECT COUNT(g) FROM GameMatchEntity g WHERE g.teamA.id = :teamId OR g.teamB.id = :teamId")
    public Integer countGameMatchEntitiesByTeamID(int teamId);

    // if teamId is team A, get all game match team A by team id has score greater than team B
    // if teamId is team B, get all game match team B by team id has score greater than team A
    @Query("SELECT COUNT(g) FROM GameMatchEntity g WHERE (g.teamA.id = :teamId AND g.teamAScore > g.teamBScore) OR (g.teamB.id = :teamId AND g.teamBScore > g.teamAScore)")
    public Integer countGameMatchEntitiesByTeamIDAndWinner(int teamId);

    // if teamId is team A, get all game match team A by team id has score less than team B
    // if teamId is team B, get all game match team B by team id has score less than team A
    @Query("SELECT COUNT(g) FROM GameMatchEntity g WHERE (g.teamA.id = :teamId AND g.teamAScore < g.teamBScore) OR (g.teamB.id = :teamId AND g.teamBScore < g.teamAScore)")
    public Integer countGameMatchEntitiesByTeamIDAndLoser(int teamId);
}
