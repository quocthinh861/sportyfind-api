package com.sportyfind.webapi.dtos;

import com.sportyfind.webapi.entities.TeamRequestEntity;

public class TeamRequestCreateResDto {
    public String userName;
    public int status;

    public void loadFromEntity(TeamRequestEntity entity) {
        this.userName = entity.getUser().getUsername();
        this.status = entity.getStatus();
    }

    public static TeamRequestCreateResDto fromEntity(TeamRequestEntity entity) {
        var result = new TeamRequestCreateResDto();
        result.loadFromEntity(entity);
        return result;
    }
}
