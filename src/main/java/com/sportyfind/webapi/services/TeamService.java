package com.sportyfind.webapi.services;

import com.sportyfind.webapi.entities.TeamEntity;
import com.sportyfind.webapi.entities.TeamRequestEntity;
import com.sportyfind.webapi.repositories.TeamRepository;
import com.sportyfind.webapi.repositories.TeamRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

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
}

