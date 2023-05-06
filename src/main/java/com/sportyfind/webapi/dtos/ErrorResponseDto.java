package com.sportyfind.webapi.dtos;

public class ErrorResponseDto extends BaseResponseDto{
    public Object errors;
    public ErrorResponseDto() {
        this.status = "ERROR";
    }

    public ErrorResponseDto(String message) {
        this.status = "ERROR";
        this.message = message;
    }
}
