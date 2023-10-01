package com.sportyfind.webapi.controllers;

import com.sportyfind.webapi.dtos.*;
import com.sportyfind.webapi.entities.TeamEntity;
import com.sportyfind.webapi.entities.TeamRequestEntity;
import com.sportyfind.webapi.entities.UserEntity;
import com.sportyfind.webapi.repositories.TeamRepository;
import com.sportyfind.webapi.repositories.UserTeamRepository;
import com.sportyfind.webapi.repositories.UserRepository;
import com.sportyfind.webapi.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private UserTeamRepository teamUserRepository;

    @Autowired
    private TeamService teamService;

    @GetMapping("/getTeamList")
    public ResponseEntity<Object> getTeamList(boolean isClearCache) {
        HttpStatus status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();
            List<TeamEntity> teamList = teamService.getAllTeams();
            response.result = TeamCreateResDto.fromEntities(teamList);
            return new ResponseEntity<>(response, status);
        } catch (Exception err) {
            status = HttpStatus.BAD_REQUEST;
            ErrorResponseDto response = new ErrorResponseDto();
            response.errors = err;
            return new ResponseEntity<>(null, status);
        }
    }



    @GetMapping("/getTeamListByUserId")
    public ResponseEntity<Object> getTeamListByCaptainId(@RequestParam Long userId) {
        HttpStatus status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();
            List<TeamEntity> teamList = teamService.getAllTeamsByUserId(userId);
            response.result = TeamCreateResDto.fromEntities(teamList);
            return new ResponseEntity<>(response, status);
        } catch (Exception err) {
            status = HttpStatus.BAD_REQUEST;
            ErrorResponseDto response = new ErrorResponseDto();
            response.errors = err;
            return new ResponseEntity<>(null, status);
        }
    }

    @GetMapping("/getTeamInformatioById")
    public ResponseEntity<Object> getTeamInformatioById(@RequestParam int teamId) {
        var status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();
            TeamEntity teamEntity = teamRepository.findById(teamId).orElse(null);
            response.result = TeamCreateResDto.fromEntity(teamEntity);
            return new ResponseEntity<>(response, status);
        } catch (Exception err) {
            status = HttpStatus.BAD_REQUEST;
            ErrorResponseDto response = new ErrorResponseDto();
            response.errors = err;
            return null;
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
            teamEntity.setName(reqDto.name);
            teamEntity.setLogo(reqDto.logo);
            teamEntity.setDescription(reqDto.description);
            teamEntity.setRankingpoint(reqDto.rankingpoint);
            teamEntity.setSkilllevel(reqDto.skilllevel);
            teamEntity.setSize(reqDto.size);
            teamEntity.setRankingorder(reqDto.rankingorder);
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
            response.result = TeamRequestCreateResDto.fromEntities(teamList);
            return new ResponseEntity<>(response, status);
        } catch (Exception err) {
            status = HttpStatus.BAD_REQUEST;
            ErrorResponseDto response = new ErrorResponseDto();
            response.errors = err;
            return new ResponseEntity<>(null, status);
        }
    }

    @PostMapping("/updateTeamRequest")
    public ResponseEntity<Object> updateTeamRequest(@RequestBody TeamRequestCreateReqDto reqDto) throws Exception {
        var status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();
            response.result = teamService.updateTeamRequest(reqDto);
            return new ResponseEntity<>(response, status);
        } catch (Exception err) {
            status = HttpStatus.BAD_REQUEST;
            var response = new ErrorResponseDto();
            status = HttpStatus.BAD_REQUEST;
            response.errors = err;
            return new ResponseEntity<>(response, status);
        }
    }

    @GetMapping("/getTeamRequestInfo")
    public ResponseEntity<Object> getTeamRequestInfo(@RequestParam long userId, @RequestParam int teamId) {
        HttpStatus status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();
            response.result = teamService.getTeamRequestByUserIdAndTeamId(userId, teamId);
            return new ResponseEntity<>(response, status);
        } catch (Exception err) {
            status = HttpStatus.BAD_REQUEST;
            ErrorResponseDto response = new ErrorResponseDto();
            response.errors = err;
            return new ResponseEntity<>(null, status);
        }
    }
}
