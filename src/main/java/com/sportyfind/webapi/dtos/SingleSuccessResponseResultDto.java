package com.sportyfind.webapi.dtos;

public class SingleSuccessResponseResultDto extends ResponseResultDto {
    public Object data;

    public SingleSuccessResponseResultDto(Object data) {
        this.data = data;
    }
}
