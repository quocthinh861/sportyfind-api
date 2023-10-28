package com.sportyfind.webapi.dtos;

import com.sportyfind.webapi.entities.GameRequestEntity;
import com.sportyfind.webapi.enums.REQ_ACTION;

import java.util.List;

public class GameRequestCreateResDto {
    public TeamCreateResDto team;
    public GameMatchCreateResDto game;
    public int status;

    public void loadFromEntity(GameRequestEntity entity) {
        this.team = TeamCreateResDto.fromEntity(entity.getTeam());
        this.game = GameMatchCreateResDto.fromEntity(entity.getGame());
        this.status = entity.getStatus();
    }

    public static GameRequestCreateResDto fromEntity(GameRequestEntity entity) {
        if(entity == null) return null;
        var result = new GameRequestCreateResDto();
        result.loadFromEntity(entity);
        return result;
    }

    public static List<GameRequestCreateResDto> fromEntities(List<GameRequestEntity> entities) {
        return entities.stream().map(GameRequestCreateResDto::fromEntity).toList();
    }
}
