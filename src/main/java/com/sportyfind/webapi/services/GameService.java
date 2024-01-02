package com.sportyfind.webapi.services;

import com.sportyfind.webapi.dtos.*;
import com.sportyfind.webapi.entities.*;
import com.sportyfind.webapi.enums.GAME_MATCH_STATUS;
import com.sportyfind.webapi.enums.REQ_ACTION;
import com.sportyfind.webapi.models.CustomGameInfo;
import com.sportyfind.webapi.models.GameMatchInfo;
import com.sportyfind.webapi.models.GameMatchUserResDto;
import com.sportyfind.webapi.models.SearchGameMatchQuery;
import com.sportyfind.webapi.repositories.*;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
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
    private UserRepository userRepository;

    @Autowired
    private FieldBookingRepository fieldBookingRepository;

    @Autowired
    private GameRequestRepository gameRequestRepository;

    @Autowired
    private GameMatchUserRepository gameMatchUserRepository;

    public List<GameMatchCreateResDto> searchGameMatch(SearchGameMatchQuery query) {
        List<GameMatchEntity> gameMatchEntities = null;
        if (query.teamId > 0) {
            gameMatchEntities = gameMatchRepository.findAllByTeamId(query.teamId);
        } else {
            gameMatchEntities = gameMatchRepository.findAll();
        }
        if (query.status >= 0) {
            gameMatchEntities = gameMatchEntities.stream()
                    .filter(gameMatch -> gameMatch.getStatus() == query.status)
                    .collect(Collectors.toList());
        }
        return gameMatchEntities.stream().map(gameMatch -> {
                    GameMatchCreateResDto result = new GameMatchCreateResDto();
                    result.booking = FieldBookingDto.fromEntity(gameMatch.getFieldBooking());
                    result.description = gameMatch.getDescription();
                    result.id = gameMatch.getId();
                    result.status = gameMatch.getStatus();
                    result.gameType = gameMatch.getGameType();
                    return result;
                })
                // some game match has the same id, so we need to distinct
                .distinct()
                .collect(Collectors.toList());
    }

    public List<GameMatchCreateResDto> getAllGameMatch() {
        List<GameMatchEntity> gameMatchEntities = gameMatchRepository.findAll();
        return gameMatchEntities.stream()
                .map(gameMatch -> {
                    GameMatchCreateResDto result = new GameMatchCreateResDto();
                    TeamEntity teamA = gameMatch.getTeamA();
                    result.teamA = TeamCreateResDto.fromEntity(teamA);
                    TeamEntity teamB = gameMatch.getTeamB();
                    if (teamB != null) {
                        result.teamB = TeamCreateResDto.fromEntity(teamB);
                    }
                    result.description = gameMatch.getDescription();
                    result.booking = FieldBookingDto.fromEntity(gameMatch.getFieldBooking());
                    result.id = gameMatch.getId();
                    result.status = gameMatch.getStatus();
                    result.gameType = gameMatch.getGameType();
                    // get captain team A
                    UserTeamEntity userTeamEntity = userTeamRepository.findByTeamIdAndRole(teamA.getId(), "CAPTAIN");
                    result.host = UserCreateResDto.fromEntity(userTeamEntity.getUser());
                    return result;
                })
                .sorted(Comparator.comparingLong(GameMatchCreateResDto::getStatus))
                .collect(Collectors.toList());
    }

    public List<GameMatchCreateResDto> getAllGameMatch(int gameType) {
        List<GameMatchEntity> gameMatchEntities = gameMatchRepository.findAllByGameType(gameType);
        return gameMatchEntities.stream()
                .map(gameMatch -> {
                    GameMatchCreateResDto result = new GameMatchCreateResDto();
                    TeamEntity teamA = gameMatch.getTeamA();
                    result.teamA = TeamCreateResDto.fromEntity(teamA);
                    TeamEntity teamB = gameMatch.getTeamB();
                    if (teamB != null) {
                        result.teamB = TeamCreateResDto.fromEntity(teamB);
                    }
                    result.description = gameMatch.getDescription();
                    result.booking = FieldBookingDto.fromEntity(gameMatch.getFieldBooking());
                    result.id = gameMatch.getId();
                    result.status = gameMatch.getStatus();
                    result.teamAScore = gameMatch.getTeamAScore();
                    result.teamBScore = gameMatch.getTeamBScore();
                    // get captain team A
                    UserTeamEntity userTeamEntity = userTeamRepository.findByTeamIdAndRole(teamA.getId(), "CAPTAIN");
                    if(userTeamEntity != null)
                        result.host = UserCreateResDto.fromEntity(userTeamEntity.getUser());
                    return result;
                })
                .sorted(Comparator.comparingLong(GameMatchCreateResDto::getStatus)
                        .thenComparing(Comparator.comparingLong(GameMatchCreateResDto::getId)
                                .reversed()))
                .collect(Collectors.toList());
    }

    public GameMatchCreateResDto createGameMatch(GameMatchCreateReqDto gameMatchDto) throws Exception {
        GameMatchEntity gameMatch = new GameMatchEntity();
        TeamEntity teamA = teamRepository.findById(gameMatchDto.teamAId).isPresent() ?
                teamRepository.findById(gameMatchDto.teamAId).get() : null;
        if (teamA == null) {
            throw new Exception("Team A not found");
        }

        FieldBookingEntity fieldBooking = fieldBookingRepository.findById(gameMatchDto.bookingId).isPresent() ?
                fieldBookingRepository.findById(gameMatchDto.bookingId).get() : null;
        if (fieldBooking == null) {
            throw new Exception("Field booking not found");
        }
        gameMatch.setTeamA(teamA);
        gameMatch.setGameType(gameMatchDto.gameType);
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

    public List<TeamCreateResDto> getGameRequestByTeamId(int gameId) {
        List<GameRequestEntity> gameRequestEntities = gameRequestRepository.findByGameId(gameId);
        return gameRequestEntities.stream().map(gameRequest -> {
            TeamCreateResDto result = new TeamCreateResDto();
            result.loadFromEntity(gameRequest.getTeam());
            return result;
        }).collect(Collectors.toList());
    }

    @Transactional
    public GameRequestCreateResDto updateGameRequest(GameRequestCreateReqDto reqDto) throws Exception {
        GameRequestEntity gameRequest = new GameRequestEntity();
        if (reqDto.action == REQ_ACTION.CREATE) {
            TeamEntity team = teamRepository.findById(reqDto.teamId).orElse(null);
            GameMatchEntity gameMatch = gameMatchRepository.findById(reqDto.gameId).orElse(null);
            gameRequest.setGame(gameMatch);
            gameRequest.setTeam(team);
            gameRequest.setStatus(1);
            gameRequest.setCreateddate(new java.util.Date());
        } else if(reqDto.action == REQ_ACTION.CANCEL) {
            gameRequestRepository.deleteByTeamIdAndGameId(reqDto.teamId, reqDto.gameId);
            gameMatchRepository.deleteById(reqDto.gameId);
            return null;
        } else if (reqDto.action == REQ_ACTION.REMOVE) {
            gameRequestRepository.deleteByTeamIdAndGameId(reqDto.teamId, reqDto.gameId);
            return null;
        } else if (reqDto.action == REQ_ACTION.ACCEPT) {
            TeamEntity team = teamRepository.findById(reqDto.teamId).orElse(null);
            if (team == null) throw new Exception("Cannot find team");
            GameMatchEntity game = gameMatchRepository.findById(reqDto.gameId).orElse(null);
            if (game == null) throw new Exception("Cannot find game");
            game.setTeamB(team);
            game.setStatus(1);
            gameMatchRepository.save(game);
            gameRequestRepository.deleteByGameId(reqDto.gameId);
            return null;
        }

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
        return GameRequestCreateResDto.fromEntity(gameRequestRepository.save(gameRequest));
    }

    public GameMatchCreateResDto updateGameResult(GameMatchCreateReqDto gameDTO) {
        // check if game exist
        GameMatchEntity gameMatch = gameMatchRepository.findById(gameDTO.id).orElse(null);
        if (gameMatch == null) {
            throw new RuntimeException("Game not found with id: " + gameDTO.id);
        }

        //check if the game is finished by compare the timne of the game with the current time of booking
//        if (gameMatch.getFieldBooking().getBookingDate().after(new Date())) {
//            throw new RuntimeException("Game is not finished yet");
//        }

        // update team point
        TeamEntity teamA = gameMatch.getTeamA();
        TeamEntity teamB = gameMatch.getTeamB();

        if (gameMatch.getStatus() == GAME_MATCH_STATUS.FINISHED.getStatus()) {
            // if the game is already finished, we need to reset the point of the team
            if (gameMatch.getTeamAScore() > gameMatch.getTeamBScore()) {
                teamA.setSkilllevel(teamA.getSkilllevel() - 3);
                teamA.setRankingpoint(teamA.getRankingpoint() - 3);
            } else if (gameMatch.getTeamAScore() < gameMatch.getTeamBScore()) {
                teamB.setSkilllevel(teamB.getSkilllevel() - 3);
                teamB.setRankingpoint(teamB.getRankingpoint() - 3);
            } else {
                teamA.setSkilllevel(teamA.getSkilllevel() +  1);
                teamB.setSkilllevel(teamB.getSkilllevel() + 1);
            }
        }

        gameMatch.setStatus(GAME_MATCH_STATUS.FINISHED.getStatus());
        gameMatch.setTeamAScore(gameDTO.teamAScore);
        gameMatch.setTeamBScore(gameDTO.teamBScore);


        if (gameDTO.teamAScore > gameDTO.teamBScore) {
            teamA.setSkilllevel(teamA.getSkilllevel() + 3);
            gameMatch.setWinner(teamA.getId());
            gameMatch.setLoser(teamB.getId());
        } else if (gameDTO.teamAScore < gameDTO.teamBScore) {
            teamB.setSkilllevel(teamB.getSkilllevel() + 3);
            gameMatch.setWinner(teamB.getId());
            gameMatch.setLoser(teamA.getId());
        } else {
            teamA.setSkilllevel(teamA.getSkilllevel() + 1);
            teamB.setSkilllevel(teamB.getSkilllevel() + 1);
        }

        teamRepository.save(teamA);
        teamRepository.save(teamB);
        gameMatchRepository.save(gameMatch);

        return GameMatchCreateResDto.fromEntity(gameMatch);
    }

    public CustomGameInfo getCustomGameMatchInfo(int gameId, int userId) {
        GameMatchEntity gameMatch = gameMatchRepository.findById(gameId).orElse(null);
        if (gameMatch == null) {
            throw new RuntimeException("Game not found with id: " + gameId);
        }

        List<GameMatchUserEntity> gameMatchUsers = gameMatchUserRepository.findByGameMatchId(gameId);
        if(gameMatchUsers == null) {
            throw new RuntimeException("Game not found with id: " + gameId);
        }

        GameMatchUserEntity gameUser = gameMatchUsers.stream().filter(gameMatchUserEntity -> gameMatchUserEntity.getUser().getId() == userId).findFirst().orElse(null);


        Integer noJoin = gameMatchUsers.stream().filter(gameMatchUser -> gameMatchUser.getStatus() == 0)
                .collect(Collectors.toList()).size();

        Integer noReject = gameMatchUsers.stream().filter(gameMatchUser -> gameMatchUser.getStatus() == 1)
                .collect(Collectors.toList()).size();

        Integer noWait = gameMatchUsers.stream().filter(gameMatchUser -> gameMatchUser.getStatus() == 2)
                .collect(Collectors.toList()).size();


        CustomGameInfo customGameInfo = new CustomGameInfo();
        customGameInfo.noJoin = noJoin;
        customGameInfo.noReject = noReject;
        customGameInfo.noWait = noWait;
        customGameInfo.status = gameUser != null ?gameUser.getStatus() : null;

        return customGameInfo;
    }

    public GameMatchUserResDto updateCustomGameMatchInfo(GameMatchUserResDto gameMatchUserResDto) {
        GameMatchEntity gameMatch = gameMatchRepository.findById(gameMatchUserResDto.gameMatchId).orElse(null);
        if (gameMatch == null) {
            throw new RuntimeException("Game not found with id: " + gameMatchUserResDto.gameMatchId);
        }

        UserEntity user = userRepository.findById(gameMatchUserResDto.userId).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found with id: " + gameMatchUserResDto.userId);
        }

        // check existing game match user
        GameMatchUserEntity gameMatchUserEntity = gameMatchUserRepository.findByGameMatchIdAndUserId(gameMatchUserResDto.gameMatchId, gameMatchUserResDto.userId);
        if(gameMatchUserEntity != null) {
            gameMatchUserEntity.setStatus(gameMatchUserResDto.status);
            GameMatchUserEntity result = gameMatchUserRepository.save(gameMatchUserEntity);
            return GameMatchUserResDto.fromEntity(result);
        }

        GameMatchUserEntity gameMatchUser = new GameMatchUserEntity();
        gameMatchUser.setGameMatch(gameMatch);
        gameMatchUser.setUser(user);
        gameMatchUser.setStatus(gameMatchUserResDto.status);

        GameMatchUserEntity result = gameMatchUserRepository.save(gameMatchUser);
        return GameMatchUserResDto.fromEntity(result);
    }
}
