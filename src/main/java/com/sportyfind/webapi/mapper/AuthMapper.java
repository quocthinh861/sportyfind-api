package com.sportyfind.webapi.mapper;

import com.sportyfind.webapi.dtos.AuthCreateReqDto;
import com.sportyfind.webapi.entities.UserEntity;
import com.sportyfind.webapi.util.TimeUtil;

public class AuthMapper {
    public static UserEntity fromDto(AuthCreateReqDto dto) throws Exception {
        var result = new UserEntity();
        result.setUsername(dto.username);
        result.setPassword(dto.password);
        result.setFullName(dto.fullName);
        result.setAddress(dto.address);
        result.setBirthday(TimeUtil.formatStringToDate(dto.birthday));
        result.setGender(dto.gender);
        result.setPhoneNumber(dto.phoneNumber);
        return result;
    }
}
