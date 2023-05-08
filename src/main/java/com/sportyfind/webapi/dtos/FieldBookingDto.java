package com.sportyfind.webapi.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sportyfind.webapi.Utils.CustomTimeUtil;
import com.sportyfind.webapi.entities.FieldBookingEntity;
import com.sportyfind.webapi.entities.FieldEntity;
import com.sportyfind.webapi.entities.UserEntity;

import java.util.List;


public class FieldBookingDto {
    @JsonProperty("bookingId")
    public Integer bookingId;

    @JsonProperty("startTime")
    public String startTime;

    @JsonProperty("endTime")
    public String endTime;

    @JsonProperty("bookingStatus")
    public String bookingStatus;

    @JsonProperty("fieldId")
    public Integer fieldId;

    @JsonProperty("customerId")
    public Long customerId;

    @JsonProperty("bookingDate")
    @JsonFormat(pattern = "MM/dd/yyyy")
    public String bookingDate;

    @JsonProperty("field")
    public FieldEntity field;

    @JsonProperty("customer")
    public UserEntity customer;

    @JsonProperty("fieldName")
    public String fieldName;

    @JsonProperty("price")
    public double price;

    public void loadFromEntity(FieldBookingEntity entity) {
        this.bookingId = entity.getFieldBookingId();
        this.startTime = entity.getStartTime().toString();
        this.endTime = entity.getEndTime().toString();
        this.bookingStatus = entity.getBookingStatus();
        this.bookingDate = CustomTimeUtil.formatDateToString(entity.getBookingDate());
        this.fieldName = entity.getField().getFieldName();
        this.price = entity.calculatePrice();
    }

    public static FieldBookingDto fromEntity(FieldBookingEntity entity) {
        var result = new FieldBookingDto();
        result.loadFromEntity(entity);
        return result;
    }

    public static List<FieldBookingDto> fromEntities(List<FieldBookingEntity> entities) {
        return entities.stream().map(FieldBookingDto::fromEntity).toList();
    }
}
