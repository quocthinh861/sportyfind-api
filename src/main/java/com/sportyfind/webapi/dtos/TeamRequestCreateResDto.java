package com.sportyfind.webapi.dtos;

import com.sportyfind.webapi.entities.TeamEntity;
import com.sportyfind.webapi.entities.TeamRequestEntity;

import java.util.List;

public class TeamRequestCreateResDto {
    public String userName;
    public int teamId;
    public long userId;
    public int status;

    public void loadFromEntity(TeamRequestEntity entity) {
        this.userName = entity.getUser().getUsername();
        this.status = entity.getStatus();
        this.teamId = entity.getTeam().getId();
        this.userId = entity.getUser().getId();

    }

    public static TeamRequestCreateResDto fromEntity(TeamRequestEntity entity) {
        var result = new TeamRequestCreateResDto();
        result.loadFromEntity(entity);
        return result;
    }

    public static List<TeamRequestCreateResDto> fromEntities(List<TeamRequestEntity> entities) {
        return entities.stream().map(TeamRequestCreateResDto::fromEntity).toList();
    }
}
