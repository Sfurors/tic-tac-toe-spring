package com.sfurors.tictactoe.service.impl;

import com.sfurors.tictactoe.model.CellCoordinates;
import com.sfurors.tictactoe.model.GameState;
import com.sfurors.tictactoe.model.Sign;
import com.sfurors.tictactoe.repository.InMemoryRepository;
import com.sfurors.tictactoe.service.GameService;
import com.sfurors.tictactoe.service.ValidationService;
import org.springframework.stereotype.Service;

import static com.sfurors.tictactoe.model.GameState.TABLE_SIZE;

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
            Sign nextMoveSign = calculateMove(tableState);
            makeMove(tableState, move);
            if (validationService.checkWin(nextMoveSign, tableState)) {
                announceWinner();
                resetGameAtEnd();
            } else if (validationService.checkDraw(tableState)) {
                announceDraw();
                resetGameAtEnd();
            } else {
                announceNextPlayerMove();
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
        calculateMove(gameStateInMemory.getTableState());
        announceNextPlayerMove();
        return gameStateInMemory;
    }

    @Override
    public GameState resetGameState() {
        GameState gameStateInMemory = inMemoryRepository.getGameStateInMemory();
        gameStateInMemory.setTableState(new Sign[TABLE_SIZE][TABLE_SIZE]);
        gameStateInMemory.setCurrentPlayer(Sign.O);
        gameStateInMemory.setVerdict("Player " + gameStateInMemory.getCurrentPlayer().name() + " begins");
        return gameStateInMemory;
    }

    private void announceNextPlayerMove() {
        Sign currentMoveSign = inMemoryRepository.getGameStateInMemory().getCurrentPlayer();
        inMemoryRepository.getGameStateInMemory().setVerdict("Next player: " + (currentMoveSign == Sign.O ? Sign.X.name() : Sign.O.name()));
    }

    private void announceDraw() {
        inMemoryRepository.getGameStateInMemory().setVerdict("Draw!");
    }

    private void announceWinner() {
        Sign nextMoveSign = inMemoryRepository.getGameStateInMemory().getCurrentPlayer();
        inMemoryRepository.getGameStateInMemory().setVerdict("Player " + nextMoveSign.name() + " won!");
    }

    private Sign calculateMove(Sign[][] tableState) {
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
        inMemoryRepository.getGameStateInMemory().setCurrentPlayer(moveSign);
    }
}
