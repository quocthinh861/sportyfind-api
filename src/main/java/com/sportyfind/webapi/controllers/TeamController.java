package com.sportyfind.webapi.controllers;

import com.sportyfind.webapi.dtos.ErrorResponseDto;
import com.sportyfind.webapi.dtos.SuccessResponseDto;
import com.sportyfind.webapi.dtos.TeamCreateReqDto;
import com.sportyfind.webapi.dtos.TeamCreateResDto;
import com.sportyfind.webapi.entities.TeamEntity;
import com.sportyfind.webapi.entities.TeamRequestEntity;
import com.sportyfind.webapi.entities.UserEntity;
import com.sportyfind.webapi.repositories.FieldRepository;
import com.sportyfind.webapi.repositories.TeamRepository;
import com.sportyfind.webapi.repositories.UserRepository;
import com.sportyfind.webapi.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
@PreAuthorize("hasRole('USER')")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamService teamService;

    @GetMapping("/getTeamListByCaptainId")
    public ResponseEntity<Object> getTeamListByCaptainId(@RequestParam Long captainId) {
        HttpStatus status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();
            List<TeamEntity> teamList = teamRepository.findByCaptainId(captainId);
            response.result = TeamCreateResDto.fromEntities(teamList);
            return new ResponseEntity<>(response, status);
        } catch (Exception err) {
            status = HttpStatus.BAD_REQUEST;
            ErrorResponseDto response = new ErrorResponseDto();
            response.errors = err;
            return new ResponseEntity<>(null, status);
        }
    }




    @PostMapping("/create")
    public ResponseEntity<Object> createTeam(@RequestBody TeamCreateReqDto reqDto) throws Exception {
        var status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();
            UserEntity customer = userRepository.findById(reqDto.captainid)
                    .orElseThrow(() -> new Exception("Customer not found"));
            TeamEntity teamEntity = new TeamEntity();
            teamEntity.setCaptain(customer);
            teamEntity.setName(reqDto.name);
            teamEntity.setLogo(reqDto.logo);
            teamEntity.setDescription(reqDto.description);
            teamEntity.setRankingpoint(reqDto.rankingpoint);
            teamEntity.setSkilllevel(reqDto.skilllevel);
            teamEntity = teamRepository.save(teamEntity);
            response.result = TeamCreateResDto.fromEntity(teamEntity);
            return new ResponseEntity<>(response, status);
        } catch (Exception err) {
            status = HttpStatus.BAD_REQUEST;
            var response = new ErrorResponseDto();
            response.errors = err;
            return new ResponseEntity<>(response, status);
        }
    }

    @GetMapping("/getAllTeamRequestsByTeamId")
    public ResponseEntity<Object> getTeamRequestsByTeamId(@RequestParam int teamId) {
        HttpStatus status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();
            List<TeamRequestEntity> teamList = teamService.getAllTeamRequestsByTeamId(teamId);
            response.result = teamList;
            return new ResponseEntity<>(response, status);
        } catch (Exception err) {
            status = HttpStatus.BAD_REQUEST;
            ErrorResponseDto response = new ErrorResponseDto();
            response.errors = err;
            return new ResponseEntity<>(null, status);
        }
    }
}
