package com.sportyfind.webapi.repositories;

import com.sportyfind.webapi.entities.FieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldRepository extends JpaRepository<FieldEntity, Integer> {
}
