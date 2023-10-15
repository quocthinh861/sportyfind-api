package com.sportyfind.webapi.models;

import com.sportyfind.webapi.dtos.FieldBookingDto;
import com.sportyfind.webapi.dtos.TeamCreateResDto;
import com.sportyfind.webapi.dtos.UserCreateResDto;
import com.sportyfind.webapi.entities.GameMatchEntity;
import com.sportyfind.webapi.entities.TeamEntity;

import java.util.List;

public class GameMatchInfo {
    public TeamCreateResDto teamA;
    public FieldBookingDto booking;
    public UserCreateResDto host;

    public void loadFromEntity(GameMatchEntity entity) {
        this.teamA = TeamCreateResDto.fromEntity(entity.getTeamA());
        this.booking = FieldBookingDto.fromEntity(entity.getFieldBooking());
    }

    public static GameMatchInfo fromEntity(GameMatchEntity entity) {
        if(entity == null) return null;
        var result = new GameMatchInfo();
        result.loadFromEntity(entity);
        return result;
    }

    public static List<GameMatchInfo> fromEntities(List<GameMatchEntity> entities) {
        return entities.stream().map(GameMatchInfo::fromEntity).toList();
    }
}
