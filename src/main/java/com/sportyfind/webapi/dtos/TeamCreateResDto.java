package com.sportyfind.webapi.dtos;

import com.sportyfind.webapi.entities.FieldBookingEntity;
import com.sportyfind.webapi.entities.TeamEntity;

import java.util.List;

public class TeamCreateResDto {
    public String name;
    public String description;
    public int rankingpoint;
    public int skilllevel;

    public void loadFromEntity(TeamEntity entity) {
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.rankingpoint = entity.getRankingpoint();
        this.skilllevel = entity.getSkilllevel();
    }



    public static TeamCreateResDto fromEntity(TeamEntity entity) {
        var result = new TeamCreateResDto();
        result.loadFromEntity(entity);
        return result;
    }

    public static List<TeamCreateResDto> fromEntities(List<TeamEntity> entities) {
        return entities.stream().map(TeamCreateResDto::fromEntity).toList();
    }
}
