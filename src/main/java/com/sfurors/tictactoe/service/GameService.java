package com.sfurors.tictactoe.service;

import com.sfurors.tictactoe.model.CellCoordinates;
import com.sfurors.tictactoe.model.GameState;

public interface GameService {
    GameState handleMove(CellCoordinates move);

    GameState findGameState();

    GameState resetGameState();
}
