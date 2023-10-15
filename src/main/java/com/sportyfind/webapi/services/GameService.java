package com.sportyfind.webapi.services;

import com.sportyfind.webapi.dtos.*;
import com.sportyfind.webapi.entities.FieldBookingEntity;
import com.sportyfind.webapi.entities.GameMatchEntity;
import com.sportyfind.webapi.entities.TeamEntity;
import com.sportyfind.webapi.entities.UserTeamEntity;
import com.sportyfind.webapi.models.GameMatchInfo;
import com.sportyfind.webapi.repositories.FieldBookingRepository;
import com.sportyfind.webapi.repositories.GameMatchRepository;
import com.sportyfind.webapi.repositories.TeamRepository;
import com.sportyfind.webapi.repositories.UserTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {
    @Autowired
    private GameMatchRepository gameMatchRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserTeamRepository userTeamRepository;

    @Autowired
    private FieldBookingRepository fieldBookingRepository;

    public List<GameMatchCreateResDto> getAllGameMatch(int gameType) {
        List<GameMatchEntity> gameMatchEntities = gameMatchRepository.findAllByGameType(gameType);
        return gameMatchEntities.stream().map(gameMatch -> {
            GameMatchCreateResDto result = new GameMatchCreateResDto();
            TeamEntity teamA = gameMatch.getTeamA();
            result.teamA = TeamCreateResDto.fromEntity(teamA);
            result.description = gameMatch.getDescription();
            result.booking = FieldBookingDto.fromEntity(gameMatch.getFieldBooking());
            // get captain team A
            UserTeamEntity userTeamEntity = userTeamRepository.findByTeamIdAndRole(teamA.getId(), "CAPTAIN");
            result.host = UserCreateResDto.fromEntity(userTeamEntity.getUser());
            return result;
        }).collect(Collectors.toList());
    }

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

    public List<GameMatchInfo> getListGameMatch() {
        List<GameMatchEntity> gameMatchEntities = gameMatchRepository.findAll();
        return gameMatchEntities.stream().map(gameMatch -> {
            GameMatchInfo gameMatchInfo = new GameMatchInfo();
            // get captain team A
            UserTeamEntity userTeamEntity = userTeamRepository.findByTeamIdAndRole(gameMatch.getTeamA().getId(), "CAPTAIN");
            gameMatchInfo.host = UserCreateResDto.fromEntity(userTeamEntity.getUser());
            gameMatchInfo.loadFromEntity(gameMatch);

            return gameMatchInfo;
        }).toList();
    }
}
