package com.sportyfind.webapi.enums;

public enum GAME_MATCH_STATUS {
    FINISHED(3);
    // Add other enum constants if needed

    private final int status;

    GAME_MATCH_STATUS(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}