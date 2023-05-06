package com.sportyfind.webapi.services;

import com.sportyfind.webapi.models.UserEntity;
import com.sportyfind.webapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> getAllOwners() {
        return userRepository.findAll();
    }

    public UserEntity checkLogin(String username, String password) {
        return null;
    }
}
