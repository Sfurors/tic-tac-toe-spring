package com.sfurors.tictactoe.service.impl;

import com.sfurors.tictactoe.model.CellCoordinates;
import com.sfurors.tictactoe.model.Sign;
import com.sfurors.tictactoe.service.ValidationService;
import org.springframework.stereotype.Service;

import static com.sfurors.tictactoe.model.GameState.TABLE_SIZE;
@Service
public class ValidationServiceImpl implements ValidationService {

    @Override
    public boolean validateMove(Sign[][] tableState, CellCoordinates move) {
        return tableState[move.getColumn()][move.getRow()] == null;
    }

    @Override
    public boolean checkWin(Sign nextMoveSign, Sign[][] tableState) {
        boolean isWinningMove;

        if (checkRows(nextMoveSign, tableState)) return true;

        if (checkColumns(nextMoveSign, tableState)) return true;

        if (checkDiagonalFromUp(nextMoveSign, tableState)) return true;

        isWinningMove = checkDiagonalFromDown(nextMoveSign, tableState);

        return isWinningMove;
    }

    private boolean checkDiagonalFromDown(Sign nextMoveSign, Sign[][] tableState) {
        boolean isWinningMove = true;
        for (int column = 0; column < tableState[0].length; column++) {
            if (tableState[column][tableState.length - 1 - column] != nextMoveSign) {
                isWinningMove = false;
                break;
            }
        }
        return isWinningMove;
    }

    private boolean checkDiagonalFromUp(Sign nextMoveSign, Sign[][] tableState) {
        boolean isWinningMove = true;
        for (int column = 0; column < tableState.length; column++) {
            if (tableState[column][column] != nextMoveSign) {
                isWinningMove = false;
                break;
            }
        }
        if (isWinningMove) {
            return true;
        }
        return false;
    }

    private boolean checkColumns(Sign nextMoveSign, Sign[][] tableState) {
        boolean isWinningMove;
        for (int row = 0; row < tableState[0].length; row++) {
            isWinningMove = true;
            for (int column = 0; column < tableState.length; column++) {
                if (tableState[column][row] != nextMoveSign) {
                    isWinningMove = false;
                    break;
                }
            }
            if (isWinningMove) {
                return true;
            }
        }
        return false;
    }

    private boolean checkRows(Sign nextMoveSign, Sign[][] tableState) {
        boolean isWinningMove;
        for (int column = 0; column < tableState.length; column++) {
            isWinningMove = true;
            for (int row = 0; row < tableState[0].length; row++) {
                if (tableState[column][row] != nextMoveSign) {
                    isWinningMove = false;
                    break;
                }
            }
            if (isWinningMove) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkDraw(Sign[][] tableState) {
        int xCount = 0;
        int oCount = 0;
        for (int column = 0; column < 3; column++) {
            for (int row = 0; row < 3; row++) {
                if (tableState[column][row] == Sign.X) {
                    xCount++;
                } else if (tableState[column][row] == Sign.O) {
                    oCount++;
                }
            }
        }
        return xCount + oCount == TABLE_SIZE * TABLE_SIZE;
    }

}
