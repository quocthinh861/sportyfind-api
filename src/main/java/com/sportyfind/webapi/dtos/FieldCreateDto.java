package com.sportyfind.webapi.dtos;

import com.sportyfind.webapi.entities.FieldEntity;

public class FieldCreateDto {
    public String fieldName;
    public String thumbnail;
    public Integer fieldId;
    public Double hourlyRate;
    public VenueCreateDto venue;

    public static FieldCreateDto fromEntity(FieldEntity entity) {
        var result = new FieldCreateDto();
        result.fieldId = entity.getFieldId();
        result.fieldName = entity.getFieldName();
        result.thumbnail = entity.getThumbnail();
        result.hourlyRate = entity.getHourlyRate();
        result.venue = VenueCreateDto.fromEntity(entity.getVenue());
        return result;
    }
}
