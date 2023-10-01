package com.sportyfind.webapi.repositories;

import com.sportyfind.webapi.entities.TeamEntity;
import com.sportyfind.webapi.entities.UserTeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserTeamRepository extends JpaRepository<UserTeamEntity, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_team WHERE user_id = ?1 AND team_id = ?2", nativeQuery = true)
    int deleteByUserIdAndTeamId(Long userId, int teamId);

    List<UserTeamEntity> findAllByUserId(long userId);
}
