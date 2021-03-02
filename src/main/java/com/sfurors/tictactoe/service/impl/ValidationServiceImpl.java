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
    public boolean checkWin(Sign currentMoveSign, Sign[][] tableState) {
        boolean isWinningMove;

        if (checkRows(currentMoveSign, tableState)) return true;

        if (checkColumns(currentMoveSign, tableState)) return true;

        if (checkDiagonalFromUp(currentMoveSign, tableState)) return true;

        isWinningMove = checkDiagonalFromDown(currentMoveSign, tableState);

        return isWinningMove;
    }

    private boolean checkDiagonalFromDown(Sign currentMoveSign, Sign[][] tableState) {
        boolean isWinningMove = true;
        for (int column = 0; column < TABLE_SIZE; column++) {
            if (tableState[column][TABLE_SIZE - 1 - column] != currentMoveSign) {
                isWinningMove = false;
                break;
            }
        }
        return isWinningMove;
    }

    private boolean checkDiagonalFromUp(Sign currentMoveSign, Sign[][] tableState) {
        boolean isWinningMove = true;
        for (int column = 0; column < TABLE_SIZE; column++) {
            if (tableState[column][column] != currentMoveSign) {
                isWinningMove = false;
                break;
            }
        }
        return isWinningMove;
    }

    private boolean checkColumns(Sign currentMoveSign, Sign[][] tableState) {
        boolean isWinningMove;
        for (int row = 0; row < TABLE_SIZE; row++) {
            isWinningMove = true;
            for (int column = 0; column < TABLE_SIZE; column++) {
                if (tableState[column][row] != currentMoveSign) {
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

    private boolean checkRows(Sign currentMoveSign, Sign[][] tableState) {
        boolean isWinningMove;
        for (int column = 0; column < TABLE_SIZE; column++) {
            isWinningMove = true;
            for (int row = 0; row < TABLE_SIZE; row++) {
                if (tableState[column][row] != currentMoveSign) {
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
        for (int column = 0; column < TABLE_SIZE; column++) {
            for (int row = 0; row < TABLE_SIZE; row++) {
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
