package com.sportyfind.webapi.services;

import com.sportyfind.webapi.dtos.FieldCreateDto;
import com.sportyfind.webapi.dtos.VenueCreateDto;
import com.sportyfind.webapi.entities.FieldEntity;
import com.sportyfind.webapi.entities.VenueEntity;
import com.sportyfind.webapi.repositories.FieldRepository;
import com.sportyfind.webapi.repositories.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FieldService {
    @Autowired
    private FieldRepository fieldRepository;

    @Autowired VenueRepository venueRepository;


    public FieldCreateDto getFieldIdByURL(String url) {
        VenueEntity venueEntity = venueRepository.findByUrl(url);
        if(venueEntity == null) {
            return null;
        }
        List<FieldEntity> fieldEntities = venueEntity.getFields();
        if(fieldEntities == null || fieldEntities.size() == 0) {
            return null;
        }

        return getFieldDetailById(fieldEntities.get(0).getFieldId());
    }

    public FieldCreateDto getFieldDetailById(Integer fieldId) {
        FieldEntity field = fieldRepository.findById(fieldId).orElse(null);
        if (field == null) {
            return null;
        }
        VenueEntity venue = field.getVenue();
        if (venue == null) {
            return null;
        }

        FieldCreateDto result = new FieldCreateDto();
        result.fieldName = field.getFieldName();
        result.venue = VenueCreateDto.fromEntity(venue);

        return result;
    }
}
