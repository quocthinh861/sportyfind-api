package com.sportyfind.webapi.services;

import com.sportyfind.webapi.models.OwnerEntity;
import com.sportyfind.webapi.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {
    @Autowired
    private OwnerRepository ownerRepository;

    public List<OwnerEntity> getAllOwners() {
        return ownerRepository.findAll();
    }
}
