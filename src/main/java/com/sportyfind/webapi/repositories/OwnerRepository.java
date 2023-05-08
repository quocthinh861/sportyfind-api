package com.sportyfind.webapi.repositories;

import com.sportyfind.webapi.entities.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  OwnerRepository extends JpaRepository<OwnerEntity, Integer> {
}
