package com.sfurors.tictactoe.services;

import com.sfurors.tictactoe.models.CellCoordinates;
import com.sfurors.tictactoe.models.GameState;

public interface GameService {
    GameState handleMove(CellCoordinates move);

    GameState findGameState();

    GameState resetGameState();
}
