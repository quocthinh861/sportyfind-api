package com.sportyfind.webapi.services;

import com.sportyfind.webapi.dtos.TeamRequestCreateReqDto;
import com.sportyfind.webapi.dtos.TeamRequestCreateResDto;
import com.sportyfind.webapi.entities.TeamEntity;
import com.sportyfind.webapi.entities.TeamRequestEntity;
import com.sportyfind.webapi.entities.UserEntity;
import com.sportyfind.webapi.repositories.TeamRepository;
import com.sportyfind.webapi.repositories.TeamRequestRepository;
import com.sportyfind.webapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRequestRepository teamRequestRepository;

    public List<TeamEntity> getAllTeams() {
        return teamRepository.findAll();
    }

    public TeamEntity getTeamById(Integer id) {
        return teamRepository.findById(id).orElse(null);
    }

    public TeamEntity createTeam(TeamEntity team) {
        return teamRepository.save(team);
    }

    public List<TeamRequestEntity> getAllTeamRequestsByTeamId(int teamId) {
        return teamRequestRepository.findByTeamId(teamId);
    }

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
        } else if(Objects.equals(reqDto.action, "CANCEL")) {
            teamRequest = teamRequestRepository.findByUserIdAndTeamId(reqDto.userId, reqDto.teamId);
            teamRequestRepository.delete(teamRequest);
            return TeamRequestCreateResDto.fromEntity(teamRequest);
        }
        return TeamRequestCreateResDto.fromEntity(teamRequestRepository.save(teamRequest));
    }

    public TeamRequestCreateResDto getTeamRequestByUserIdAndTeamId(long userId, int teamId) {
        return TeamRequestCreateResDto.fromEntity(teamRequestRepository.findByUserIdAndTeamId(userId, teamId));
    }
}

