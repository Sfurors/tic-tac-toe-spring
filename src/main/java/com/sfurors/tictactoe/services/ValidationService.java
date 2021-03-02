package com.sfurors.tictactoe.services;

import com.sfurors.tictactoe.models.CellCoordinates;
import com.sfurors.tictactoe.models.Sign;

public interface ValidationService {
    boolean validateMove(Sign[][] tableState, CellCoordinates move);
    boolean checkWin(Sign currentMoveSign, Sign[][] tableState);
    boolean checkDraw(Sign[][] tableState);
}
