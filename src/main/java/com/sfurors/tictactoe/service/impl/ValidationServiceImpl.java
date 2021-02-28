package com.sfurors.tictactoe.service.impl;

import com.sfurors.tictactoe.model.CellCoordinates;
import com.sfurors.tictactoe.model.Sign;
import com.sfurors.tictactoe.service.ValidationService;
import org.springframework.stereotype.Service;

import static com.sfurors.tictactoe.model.GameState.BOARD_SIZE;
@Service
public class ValidationServiceImpl implements ValidationService {

    public boolean validateMove(Sign[][] tableState, CellCoordinates move) {
        if (tableState[move.getColumn()][move.getRow()] != null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkWin(Sign nextMoveSign, Sign[][] tableState) {
        boolean isWinningMove = false;

        for (int column = 0; column < tableState.length; column++) {
            isWinningMove = true;
            for (int row = 0; row < tableState[0].length; row++) {
                System.out.println(tableState[column][row]);
                if (tableState[column][row] != nextMoveSign) {
                    isWinningMove = false;
                    break;
                }
            }
            if (isWinningMove) {
                return true;
            }
        }

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

        isWinningMove = true;
        for (int column = 0; column < tableState.length; column++) {
            if (tableState[column][column] != nextMoveSign) {
                isWinningMove = false;
                break;
            }
        }
        if (isWinningMove) {
            return true;
        }

        isWinningMove = true;
        for (int column = 0; column < tableState[0].length; column++) {
            if (tableState[column][tableState.length - 1 - column] != nextMoveSign) {
                isWinningMove = false;
                break;
            }
        }

        return isWinningMove;
    }

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
        return xCount == BOARD_SIZE && oCount == BOARD_SIZE;
    }

}
