package com.sportyfind.webapi.controllers;

import com.sportyfind.webapi.dtos.*;
import com.sportyfind.webapi.models.CustomGameInfo;
import com.sportyfind.webapi.models.GameMatchUserResDto;
import com.sportyfind.webapi.repositories.GameRequestRepository;
import com.sportyfind.webapi.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;
    @Autowired
    private GameRequestRepository gameRequestRepository;

    @PostMapping("/updateGameResult")
    public ResponseEntity<Object> updateGameResult(@RequestBody GameMatchCreateReqDto gameDTO) {
        var status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();
            response.result = gameService.updateGameResult(gameDTO);
            return new ResponseEntity<>(response, status);
        } catch (Exception err) {
            status = HttpStatus.BAD_REQUEST;
            var response = new ErrorResponseDto();
            response.errors = err;
            response.message = err.getMessage();
            return new ResponseEntity<>(response, status);
        }
    }

    @PostMapping("/createMatch")
    public ResponseEntity<Object> createGame(@RequestBody GameMatchCreateReqDto gameDTO) {
        var status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();
            response.result = gameService.createGameMatch(gameDTO);
            return new ResponseEntity<>(response, status);
        } catch (Exception err) {
            status = HttpStatus.BAD_REQUEST;
            var response = new ErrorResponseDto();
            response.errors = err;
            return new ResponseEntity<>(response, status);
        }
    }

    @GetMapping("/getListGameMatch")
    public ResponseEntity<Object> getListGameMatch(int gameType) {
        var status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();
            response.result = gameService.getAllGameMatch(gameType);
            return new ResponseEntity<>(response, status);
        } catch (Exception err) {
            status = HttpStatus.BAD_REQUEST;
            var response = new ErrorResponseDto();
            response.errors = err;
            return new ResponseEntity<>(response, status);
        }
    }

    @GetMapping("/getGameRequestInfo")
    public ResponseEntity<Object> getGameRequestInfo(@RequestParam int teamId, @RequestParam int gameId) {
        HttpStatus status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();
            response.result = GameRequestCreateResDto.fromEntity(gameRequestRepository.findByTeamIdAndGameId(teamId, gameId));
            return new ResponseEntity<>(response, status);
        } catch (Exception err) {
            status = HttpStatus.BAD_REQUEST;
            ErrorResponseDto response = new ErrorResponseDto();
            response.errors = err;
            return new ResponseEntity<>(null, status);
        }
    }

    @GetMapping("/getGameRequestList")
    public ResponseEntity<Object> getGameRequestList(@RequestParam int gameId) {
        HttpStatus status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();
            response.result = gameService.getGameRequestByTeamId(gameId);
            return new ResponseEntity<>(response, status);
        } catch (Exception err) {
            status = HttpStatus.BAD_REQUEST;
            ErrorResponseDto response = new ErrorResponseDto();
            response.errors = err;
            return new ResponseEntity<>(null, status);
        }
    }

    @PostMapping("/updateGameRequest")
    public ResponseEntity<Object> updateGameRequest(@RequestBody GameRequestCreateReqDto reqDto) throws Exception {
        var status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();
            response.result = gameService.updateGameRequest(reqDto);
            return new ResponseEntity<>(response, status);
        } catch (Exception err) {
            status = HttpStatus.BAD_REQUEST;
            var response = new ErrorResponseDto();
            status = HttpStatus.BAD_REQUEST;
            response.errors = err;
            return new ResponseEntity<>(response, status);
        }
    }

    @GetMapping("/getCustomGameMatchInfo")
    public ResponseEntity<Object> getCustomGameMatchInfo(@RequestParam int gameId, @RequestParam int userId) {
        HttpStatus status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();
            response.result = gameService.getCustomGameMatchInfo(gameId, userId);
            return new ResponseEntity<>(response, status);
        } catch (Exception err) {
            status = HttpStatus.BAD_REQUEST;
            ErrorResponseDto response = new ErrorResponseDto();
            response.errors = err;
            return new ResponseEntity<>(null, status);
        }
    }

    @PostMapping("/updateCustomGameMatchInfo")
    public ResponseEntity<Object> updateCustomGameMatchInfo(@RequestBody GameMatchUserResDto gameMatchUserResDto) {
        HttpStatus status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();
            response.result = gameService.updateCustomGameMatchInfo(gameMatchUserResDto);
            return new ResponseEntity<>(response, status);
        } catch (Exception err) {
            status = HttpStatus.BAD_REQUEST;
            ErrorResponseDto response = new ErrorResponseDto();
            response.errors = err;
            return new ResponseEntity<>(null, status);
        }
    }
}
