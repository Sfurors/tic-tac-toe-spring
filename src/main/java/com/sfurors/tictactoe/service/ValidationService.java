package com.sfurors.tictactoe.service;

import com.sfurors.tictactoe.model.CellCoordinates;
import com.sfurors.tictactoe.model.Sign;

public interface ValidationService {
    boolean validateMove(Sign[][] tableState, CellCoordinates move);
    boolean checkWin(Sign nextMoveSign, Sign[][] tableState);
    boolean checkDraw(Sign[][] tableState);
}
