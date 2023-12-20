package com.sportyfind.webapi.dtos;

import com.sportyfind.webapi.entities.GameMatchEntity;

import java.util.List;

public class GameMatchCreateResDto {
    public TeamCreateResDto teamA;
    public TeamCreateResDto teamB;
    public UserCreateResDto host;
    public FieldBookingDto booking;
    public String description;
    public int id;
    public int status;
    public int gameType;
    public Integer teamAScore;
    public Integer teamBScore;

    public static GameMatchCreateResDto fromEntity(GameMatchEntity entity) {
        GameMatchCreateResDto result = new GameMatchCreateResDto();
        result.teamA = TeamCreateResDto.fromEntity(entity.getTeamA());
        result.teamB = TeamCreateResDto.fromEntity(entity.getTeamB());
        result.booking = FieldBookingDto.fromEntity(entity.getFieldBooking());
        result.description = entity.getDescription();
        result.id = entity.getId();
        result.status = entity.getStatus();
        result.gameType = entity.getGameType();
        result.teamAScore = entity.getTeamAScore();
        result.teamBScore = entity.getTeamBScore();
        return result;
    }

    public static List<GameMatchCreateResDto> fromEntities(List<GameMatchEntity> entities) {
        return entities.stream().map(GameMatchCreateResDto::fromEntity).collect(java.util.stream.Collectors.toList());
    }

    public int getId() {
        return id;
    }
    public int getStatus() {
        return status;
    }
}
