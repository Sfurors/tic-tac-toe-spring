package com.sfurors.tictactoe.controllers;

import com.sfurors.tictactoe.models.CellCoordinates;
import com.sfurors.tictactoe.models.GameState;
import com.sfurors.tictactoe.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@CrossOrigin
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping(value = "/game-state")
    public GameState findGameState() {
        return gameService.findGameState();
    }

    @GetMapping(value = "/reset")
    public GameState resetGameState() {
        return gameService.resetGameState();
    }

    @PostMapping(value = "/submit")
    public GameState submitMove(@RequestBody CellCoordinates move) {
        return gameService.handleMove(move);
    }
}
