package com.sportyfind.webapi.repositories;

import com.sportyfind.webapi.entities.FieldBookingEntity;
import com.sportyfind.webapi.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    List<RoleEntity> findAllByIdIn(List<Long> ids);
}
