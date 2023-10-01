package com.sportyfind.webapi.services;

import com.sportyfind.webapi.dtos.FieldBookingDto;
import com.sportyfind.webapi.dtos.GameMatchCreateReqDto;
import com.sportyfind.webapi.dtos.GameMatchCreateResDto;
import com.sportyfind.webapi.dtos.TeamCreateResDto;
import com.sportyfind.webapi.entities.FieldBookingEntity;
import com.sportyfind.webapi.entities.GameMatchEntity;
import com.sportyfind.webapi.entities.TeamEntity;
import com.sportyfind.webapi.repositories.FieldBookingRepository;
import com.sportyfind.webapi.repositories.GameMatchRepository;
import com.sportyfind.webapi.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class GameService {
    @Autowired
    private GameMatchRepository gameMatchRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private FieldBookingRepository fieldBookingRepository;

    public GameMatchCreateResDto createGameMatch(GameMatchCreateReqDto gameMatchDto) throws Exception {
        GameMatchEntity gameMatch = new GameMatchEntity();
        TeamEntity teamA = teamRepository.findById(gameMatchDto.teamAId).isPresent() ?
                teamRepository.findById(gameMatchDto.teamAId).get() : null;
        if(teamA == null) {
            throw new Exception("Team A not found");
        }
        FieldBookingEntity fieldBooking = fieldBookingRepository.findById(gameMatchDto.bookingId).isPresent() ?
                fieldBookingRepository.findById(gameMatchDto.bookingId).get() : null;
        if(fieldBooking == null) {
            throw new Exception("Field booking not found");
        }
        gameMatch.setTeamA(teamA);
        gameMatch.setFieldBooking(fieldBooking);
        gameMatch.setDescription(gameMatchDto.description);
        gameMatch.setCreatedAt(new Date());

        GameMatchEntity gameMatchEntity = gameMatchRepository.save(gameMatch);

        GameMatchCreateResDto result = new GameMatchCreateResDto();
        result.teamA = TeamCreateResDto.fromEntity(gameMatchEntity.getTeamA());
        result.description = gameMatchEntity.getDescription();
        result.booking = FieldBookingDto.fromEntity(gameMatchEntity.getFieldBooking());

        return result;
    }
}
