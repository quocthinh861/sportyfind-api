package com.sportyfind.webapi.dtos;

import com.sportyfind.webapi.entities.FieldEntity;

public class FieldCreateDto {
    public String fieldName;
    public VenueCreateDto venue;

    public static FieldCreateDto fromEntity(FieldEntity entity) {
        var result = new FieldCreateDto();
        result.fieldName = entity.getFieldName();
        result.venue = VenueCreateDto.fromEntity(entity.getVenue());
        return result;
    }
}
