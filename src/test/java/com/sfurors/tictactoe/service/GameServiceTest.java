package com.sfurors.tictactoe.service;

import com.sfurors.tictactoe.model.CellCoordinates;
import com.sfurors.tictactoe.model.GameState;
import com.sfurors.tictactoe.model.Sign;
import com.sfurors.tictactoe.repository.InMemoryRepository;
import com.sfurors.tictactoe.service.impl.GameServiceImpl;
import com.sfurors.tictactoe.service.impl.ValidationServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
class GameServiceTest {

    private static final int TABLE_BOARD = 3;

    InMemoryRepository inMemoryRepository;
    ValidationService validationService;

    GameService gameService;

    @Test
    void handleMove() {
        inMemoryRepository = mock(InMemoryRepository.class);
        validationService = mock(ValidationService.class);
        gameService = new GameServiceImpl(validationService, inMemoryRepository);
        GameState gameState = new GameState();
        Sign[][] tableState = fillTableState();
        gameState.setTableState(tableState);
        CellCoordinates cellCoordinates = new CellCoordinates();
        cellCoordinates.setColumn(1);
        cellCoordinates.setRow(1);
        Mockito.when(inMemoryRepository.getGameStateInMemory()).thenReturn(gameState);

        GameState gameStateAfterMove = gameService.handleMove(cellCoordinates);

        Assert.assertEquals(gameStateAfterMove, gameState);
    }

    private Sign[][] fillTableState() {
        Sign[][] tableState = new Sign[TABLE_BOARD][TABLE_BOARD];
        for (int column = 0; column < TABLE_BOARD; column++) {
            for (int row = 0; row < TABLE_BOARD; row++) {
                if ((column % 2) == 1) {
                    tableState[column][row] = Sign.O;
                } else {
                    tableState[column][row] = Sign.X;
                }
            }
        }
        return tableState;
    }

    @Test
    void findGameState() {
    }

    @Test
    void resetGameState() {
    }
}