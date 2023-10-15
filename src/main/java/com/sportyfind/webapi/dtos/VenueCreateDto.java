package com.sportyfind.webapi.dtos;

import com.sportyfind.webapi.entities.VenueEntity;

public class VenueCreateDto {
    public String address;
    public String venueName;

    public static VenueCreateDto fromEntity(VenueEntity entity) {
        var result = new VenueCreateDto();
        result.address = entity.getAddress();
        result.venueName = entity.getVenueName();
        return result;
    }
}
