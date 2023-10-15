package com.sportyfind.webapi.dtos;

import com.sportyfind.webapi.entities.FieldBookingEntity;
import com.sportyfind.webapi.entities.TeamEntity;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

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
        this.users = UserCreateResDto.fromEntities(entity.getUsers());
        this.size = Optional.ofNullable(entity.getUsers()).map(List::size).orElse(0);
    }



    public static TeamCreateResDto fromEntity(TeamEntity entity) {
        if(entity == null) return null;
        var result = new TeamCreateResDto();
        result.loadFromEntity(entity);
        return result;
    }

    public static List<TeamCreateResDto> fromEntities(List<TeamEntity> entities) {
        return entities.stream().map(TeamCreateResDto::fromEntity).toList();
    }
}
