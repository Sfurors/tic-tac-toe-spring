package com.sfurors.tictactoe.service;

import com.sfurors.tictactoe.model.CellCoordinates;
import com.sfurors.tictactoe.model.GameState;

public interface GameService {
    public GameState handleMove(CellCoordinates move);

    public GameState findGameState();

    public  GameState resetGameState();
}
