package com.sportyfind.webapi.services;

import com.sportyfind.webapi.dtos.*;
import com.sportyfind.webapi.entities.TeamEntity;
import com.sportyfind.webapi.entities.TeamRequestEntity;
import com.sportyfind.webapi.entities.UserEntity;
import com.sportyfind.webapi.entities.UserTeamEntity;
import com.sportyfind.webapi.repositories.TeamRepository;
import com.sportyfind.webapi.repositories.TeamRequestRepository;
import com.sportyfind.webapi.repositories.UserRepository;
import com.sportyfind.webapi.repositories.UserTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRequestRepository teamRequestRepository;

    @Autowired
    private UserTeamRepository userTeamRepository;

    public List<TeamEntity> getAllTeams() throws InterruptedException {
        return teamRepository.findAll();
    }

    public TeamEntity getTeamById(Integer id) {
        return teamRepository.findById(id).orElse(null);
    }

    public TeamEntity createTeam(TeamEntity team) {
        return teamRepository.save(team);
    }

    public List<TeamRequestEntity> getAllTeamRequestsByTeamId(int teamId) {
        return teamRequestRepository.findAllCreatedRequestByTeamId(teamId);
    }

    @Transactional
    public TeamRequestCreateResDto updateTeamRequest(TeamRequestCreateReqDto reqDto) {
        var teamRequest = new TeamRequestEntity();
        if(Objects.equals(reqDto.action, "CREATE")) {
            UserEntity user = userRepository.findById(reqDto.userId).orElse(null);
            TeamEntity team = teamRepository.findById(reqDto.teamId).orElse(null);
            teamRequest.setUser(user);
            teamRequest.setTeam(team);
            teamRequest.setStatus(1);
            teamRequest.setCreateddate(new java.util.Date());
        } else if(Objects.equals(reqDto.action, "ACCEPT")) {
            teamRequest = teamRequestRepository.findByUserIdAndTeamId(reqDto.userId, reqDto.teamId);
            teamRequest.setStatus(2);
            UserEntity user = userRepository.findById(reqDto.userId).orElse(null);
            TeamEntity team = teamRepository.findById(reqDto.teamId).orElse(null);
            var userTeam = new UserTeamEntity();
            userTeam.setUser(user);
            userTeam.setTeam(team);
            userTeamRepository.save(userTeam);
        } else if(Objects.equals(reqDto.action, "CANCEL")) {
            teamRequestRepository.deleteByUserIdAndTeamId(reqDto.userId, reqDto.teamId);
            return null;
        } else if(Objects.equals(reqDto.action, "REMOVE")) {
            teamRequestRepository.deleteByUserIdAndTeamId(reqDto.userId, reqDto.teamId);
            userTeamRepository.deleteByUserIdAndTeamId(reqDto.userId, reqDto.teamId);
            return null;
        }
        return TeamRequestCreateResDto.fromEntity(teamRequestRepository.save(teamRequest));
    }

    public TeamRequestCreateResDto getTeamRequestByUserIdAndTeamId(long userId, int teamId) throws Exception {
        try {
            // check user in team
            UserTeamEntity userTeam = userTeamRepository.findByUserIdAndTeamId(userId, teamId);
            if(userTeam != null) {
                TeamRequestCreateResDto res =  new TeamRequestCreateResDto();
                res.userId = userId;
                res.teamId = teamId;
                res.status = 2;
                return res;
            }
            TeamRequestEntity teamRequest =  teamRequestRepository.findByUserIdAndTeamId(userId, teamId);
            return teamRequest != null ? TeamRequestCreateResDto.fromEntity(teamRequest) : null;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public List<TeamEntity> getAllTeamsByUserId(long userId) throws Exception {
        try {
            List<UserTeamEntity> userTeams =  userTeamRepository.findAllByUserId(userId);
            List<TeamEntity> teams = userTeams.stream()
                    .map(UserTeamEntity::getTeam) // Map to TeamEntity objects
                    .collect(Collectors.toList());
            return teams;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public Object updateTeamThumbnail(TeamCreateReqDto reqDto) throws Exception {
        TeamEntity teamEntity = teamRepository.findById(reqDto.id).orElse(null);
        if(teamEntity == null) {
            throw new Exception("Cannot find team with id: " + reqDto.id + " to update thumbnail");
        }
        teamEntity.setThumbnail(reqDto.thumbnail);
        TeamEntity team = teamRepository.save(teamEntity);
        return TeamCreateResDto.fromEntity(team);
    }
}

