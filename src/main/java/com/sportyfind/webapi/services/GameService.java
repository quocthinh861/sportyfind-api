package com.sportyfind.webapi.services;

import com.sportyfind.webapi.dtos.*;
import com.sportyfind.webapi.entities.*;
import com.sportyfind.webapi.enums.REQ_ACTION;
import com.sportyfind.webapi.models.GameMatchInfo;
import com.sportyfind.webapi.repositories.FieldBookingRepository;
import com.sportyfind.webapi.repositories.GameMatchRepository;
import com.sportyfind.webapi.repositories.TeamRepository;
import com.sportyfind.webapi.repositories.UserTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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

    @Transactional
    public TeamRequestCreateResDto updateTeamRequest(GameRequestCreateReqDto reqDto) {
        return null;
//        var teamRequest = new TeamRequestEntity();
//        if(reqDto.action == REQ_ACTION.CREATE) {
//            UserEntity user = userRepository.findById(reqDto.userId).orElse(null);
//            TeamEntity team = teamRepository.findById(reqDto.teamId).orElse(null);
//            teamRequest.setUser(user);
//            teamRequest.setTeam(team);
//            teamRequest.setStatus(1);
//            teamRequest.setCreateddate(new java.util.Date());
//        } else if(Objects.equals(reqDto.action, "ACCEPT")) {
//            teamRequest = teamRequestRepository.findByUserIdAndTeamId(reqDto.userId, reqDto.teamId);
//            teamRequest.setStatus(2);
//            UserEntity user = userRepository.findById(reqDto.userId).orElse(null);
//            TeamEntity team = teamRepository.findById(reqDto.teamId).orElse(null);
//            var userTeam = new UserTeamEntity();
//            userTeam.setUser(user);
//            userTeam.setTeam(team);
//            userTeamRepository.save(userTeam);
//        } else if(Objects.equals(reqDto.action, "CANCEL")) {
//            teamRequestRepository.deleteByUserIdAndTeamId(reqDto.userId, reqDto.teamId);
//            return null;
//        } else if(Objects.equals(reqDto.action, "REMOVE")) {
//            teamRequestRepository.deleteByUserIdAndTeamId(reqDto.userId, reqDto.teamId);
//            userTeamRepository.deleteByUserIdAndTeamId(reqDto.userId, reqDto.teamId);
//            return null;
//        }
//        return TeamRequestCreateResDto.fromEntity(teamRequestRepository.save(teamRequest));
    }
}
