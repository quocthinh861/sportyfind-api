package com.sportyfind.webapi.repositories;

import com.sportyfind.webapi.entities.GameMatchUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameMatchUserRepository extends JpaRepository<GameMatchUserEntity, Integer> {
    List<GameMatchUserEntity> findByGameMatchId(int gameId);

    GameMatchUserEntity findByGameMatchIdAndUserId(int gameMatchId, Long userId);
}
