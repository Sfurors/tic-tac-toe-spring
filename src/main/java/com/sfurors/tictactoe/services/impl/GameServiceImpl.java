package com.sfurors.tictactoe.services.impl;

import com.sfurors.tictactoe.models.CellCoordinates;
import com.sfurors.tictactoe.models.GameState;
import com.sfurors.tictactoe.models.Sign;
import com.sfurors.tictactoe.repositories.InMemoryRepository;
import com.sfurors.tictactoe.services.GameService;
import com.sfurors.tictactoe.services.ValidationService;
import org.springframework.stereotype.Service;

import static com.sfurors.tictactoe.models.GameState.TABLE_SIZE;

@Service
public class GameServiceImpl implements GameService {

    private final ValidationService validationService;

    private final InMemoryRepository inMemoryRepository;

    public GameServiceImpl(ValidationService validationService, InMemoryRepository inMemoryRepository) {
        this.validationService = validationService;
        this.inMemoryRepository = inMemoryRepository;
    }

    @Override
    public GameState handleMove(CellCoordinates move) {
        GameState gameStateInMemory = inMemoryRepository.getGameStateInMemory();
        Sign[][] tableState = gameStateInMemory.getTableState();
        if (validationService.validateMove(tableState, move)) {
            Sign currentMoveSign = calculateMoveSign(tableState);
            makeMove(tableState, move);
            if (validationService.checkWin(currentMoveSign, tableState)) {
                announceWinner(currentMoveSign);
                resetGameAtEnd();
            } else if (validationService.checkDraw(tableState)) {
                announceDraw();
                resetGameAtEnd();
            } else {
                gameStateInMemory.setVerdict(null);
            }
        }
        return gameStateInMemory;
    }

    private void resetGameAtEnd() {
        GameState gameStateInMemory = inMemoryRepository.getGameStateInMemory();
        gameStateInMemory.setTableState(new Sign[TABLE_SIZE][TABLE_SIZE]);
        gameStateInMemory.setCurrentPlayer(Sign.O);
    }

    @Override
    public GameState findGameState() {
        GameState gameStateInMemory = inMemoryRepository.getGameStateInMemory();
        calculateMoveSign(gameStateInMemory.getTableState());
        return gameStateInMemory;
    }

    @Override
    public GameState resetGameState() {
        GameState gameStateInMemory = inMemoryRepository.getGameStateInMemory();
        gameStateInMemory.setTableState(new Sign[TABLE_SIZE][TABLE_SIZE]);
        gameStateInMemory.setCurrentPlayer(Sign.O);
        return gameStateInMemory;
    }

    private void announceDraw() {
        inMemoryRepository.getGameStateInMemory().setVerdict("Draw");
    }

    private void announceWinner(Sign currentMoveSign) {
        inMemoryRepository.getGameStateInMemory().setVerdict(currentMoveSign + " won");
    }

    private Sign calculateMoveSign(Sign[][] tableState) {
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
        Sign currentMove = xCount < oCount ? Sign.X : Sign.O;
        inMemoryRepository.getGameStateInMemory().setCurrentPlayer(currentMove);
        return currentMove;
    }

    private void makeMove(Sign[][] tableState, CellCoordinates move) {
        Sign moveSign = inMemoryRepository.getGameStateInMemory().getCurrentPlayer();
        tableState[move.getColumn()][move.getRow()] = moveSign;
        inMemoryRepository.getGameStateInMemory().setTableState(tableState);
        Sign nextMoveSign = calculateMoveSign(tableState);
        inMemoryRepository.getGameStateInMemory().setCurrentPlayer(nextMoveSign);
    }
}
