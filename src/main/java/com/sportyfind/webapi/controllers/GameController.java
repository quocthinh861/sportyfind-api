package com.sportyfind.webapi.controllers;

import com.sportyfind.webapi.dtos.ErrorResponseDto;
import com.sportyfind.webapi.dtos.FieldBookingDto;
import com.sportyfind.webapi.dtos.GameMatchCreateReqDto;
import com.sportyfind.webapi.dtos.SuccessResponseDto;
import com.sportyfind.webapi.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
@PreAuthorize("hasRole('USER')")
public class GameController {

    @Autowired
    private GameService gameService;

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

    @GetMapping("/getGameMatch")
    public ResponseEntity<Object> getGameMatch() {
        var status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();
            response.result = gameService.getAllGameMatch();
            return new ResponseEntity<>(response, status);
        } catch (Exception err) {
            status = HttpStatus.BAD_REQUEST;
            var response = new ErrorResponseDto();
            response.errors = err;
            return new ResponseEntity<>(response, status);
        }
    }
}
