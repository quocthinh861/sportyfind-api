package com.sportyfind.webapi.dtos;

import com.sportyfind.webapi.entities.FieldBookingEntity;
import com.sportyfind.webapi.entities.TeamEntity;

import java.util.List;

public class TeamCreateResDto {
    public String name;
    public String description;
    public int rankingpoint;
    public int skilllevel;
    public int id;
    public int size;
    public List<UserCreateResDto> users;

    public void loadFromEntity(TeamEntity entity) {
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.rankingpoint = entity.getRankingpoint();
        this.skilllevel = entity.getSkilllevel();
        this.id = entity.getId();
        this.size = entity.getSize();
        this.users = UserCreateResDto.fromEntities(entity.getUsers());
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
