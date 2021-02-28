package com.sfurors.tictactoe.service;

import com.sfurors.tictactoe.model.CellCoordinates;
import com.sfurors.tictactoe.model.Sign;

public interface ValidationService {
    public boolean validateMove(Sign[][] tableState, CellCoordinates move);
    public boolean checkWin(Sign nextMoveSign, Sign[][] tableState);
    public boolean checkDraw(Sign[][] tableState);
}
