package com.sportyfind.webapi.repositories;

import com.sportyfind.webapi.entities.GameMatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameMatchRepository extends JpaRepository<GameMatchEntity, Integer> {
}
