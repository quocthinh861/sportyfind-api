package com.sportyfind.webapi.dtos;

import com.sportyfind.webapi.enums.REQ_ACTION;

public class GameRequestCreateReqDto {
    public int teamId;
    public int gameId;
    public REQ_ACTION action;
}
