package com.sportyfind.webapi.services;

import com.sportyfind.webapi.dtos.AuthCreateReqDto;
import com.sportyfind.webapi.dtos.UserCreateResDto;
import com.sportyfind.webapi.entities.RoleEntity;
import com.sportyfind.webapi.entities.UserEntity;
import com.sportyfind.webapi.repositories.RoleRepository;
import com.sportyfind.webapi.repositories.UserRepository;
//import com.sportyfind.webapi.util.CustomTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public UserCreateResDto getUserById(Long id) {
        UserEntity user =  userRepository.findById(id).orElse(null);
        if(user == null) {
            return null;
        }
        return UserCreateResDto.fromEntity(user);
    }

    public UserCreateResDto updateUser(UserCreateResDto userCreateResDto) {
        UserEntity userEntity = userRepository.findById(userCreateResDto.id).orElse(null);
        if(userEntity == null) {
            return null;
        }
        userEntity.setFullName(userCreateResDto.fullName);
        userEntity.setEmail(userCreateResDto.email);
        userEntity.setGender(userCreateResDto.gender);
        userEntity.setHeight(userCreateResDto.height);
        userEntity.setWeight(userCreateResDto.weight);
        userEntity.setPhoneNumber(userCreateResDto.phoneNumber);
        UserEntity user = userRepository.save(userEntity);
        if(user == null) {
            return null;
        }
        return userCreateResDto;
    }

    public UserEntity createUser(AuthCreateReqDto dto) throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(dto.username);
        userEntity.setPassword(dto.password);
        userEntity.setFullName(dto.fullName);
//        userEntity.setAddress(dto.address);
//        userEntity.setBirthday(CustomTimeUtil.formatStringToDate(dto.birthday));
        userEntity.setGender(dto.gender);
        userEntity.setPhoneNumber(dto.phoneNumber);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        List<RoleEntity> roles = roleRepository.findAllById(dto.role);
        userEntity.setRoles(roles);
        return userRepository.save(userEntity);

    }

    public List<UserEntity> getAllOwners() {
        return userRepository.findAll();
    }

    public UserEntity checkLogin(String username, String password) {
        return null;
    }
}
