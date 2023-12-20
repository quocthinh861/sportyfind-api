package com.sportyfind.webapi.models;

import com.sportyfind.webapi.entities.GameMatchUserEntity;

public class GameMatchUserResDto {

    public int gameMatchId;
    public Long userId;
    public Integer status;

    public static GameMatchUserResDto fromEntity(GameMatchUserEntity result) {
        GameMatchUserResDto dto = new GameMatchUserResDto();
        dto.gameMatchId = result.getGameMatch().getId();
        dto.userId = result.getUser().getId();
        dto.status = result.getStatus();
        return dto;
    }
}
