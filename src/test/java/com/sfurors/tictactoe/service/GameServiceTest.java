package com.sfurors.tictactoe.service;

import com.sfurors.tictactoe.model.CellCoordinates;
import com.sfurors.tictactoe.model.GameState;
import com.sfurors.tictactoe.model.Sign;
import com.sfurors.tictactoe.repository.InMemoryRepository;
import com.sfurors.tictactoe.service.impl.GameServiceImpl;
import com.sfurors.tictactoe.service.impl.ValidationServiceImpl;
import com.sfurors.tictactoe.util.GameTableBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;

import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
class GameServiceTest {

    private static final int TABLE_BOARD = 3;

    InMemoryRepository inMemoryRepository;
    ValidationService validationService;

    GameService gameService;

    @Test
    void shouldNotMakeMoveCellOccupied() {
        inMemoryRepository = mock(InMemoryRepository.class);
        validationService = new ValidationServiceImpl();
        gameService = new GameServiceImpl(validationService, inMemoryRepository);
        GameState gameState = createNewGameState();
        GameState gameStateBackup = createNewGameState();
        CellCoordinates cellCoordinates = new CellCoordinates();
        cellCoordinates.setColumn(1);
        cellCoordinates.setRow(1);
        Mockito.when(inMemoryRepository.getGameStateInMemory()).thenReturn(gameState);

        GameState result = gameService.handleMove(cellCoordinates);

        Assert.assertArrayEquals(result.getTableState(), gameStateBackup.getTableState());
    }

    private GameState createNewGameState() {
        GameState gameState = new GameState();
        Sign[][] tableState = fillTableState();
        gameState.setTableState(tableState);
        return gameState;
    }

    @Test
    void shouldMakeMoveAndWin() {
        inMemoryRepository = mock(InMemoryRepository.class);
        validationService = new ValidationServiceImpl();
        gameService = new GameServiceImpl(validationService, inMemoryRepository);
        GameState gameState = createNewGameState();
        GameState gameStateBackup = createNewGameState();
        Sign[][] tableState = gameState.getTableState();
        Sign[][] tableStateBackup = gameStateBackup.getTableState();
        tableState[1][1] = null;
        tableStateBackup[1][1] = null;
        CellCoordinates cellCoordinates = new CellCoordinates();
        cellCoordinates.setColumn(1);
        cellCoordinates.setRow(1);
        Mockito.when(inMemoryRepository.getGameStateInMemory()).thenReturn(gameState);

        GameState result = gameService.handleMove(cellCoordinates);

        Assert.assertFalse(Arrays.equals(result.getTableState(), gameStateBackup.getTableState()));
        Assert.assertEquals("Player O wins!", result.getVerdict());
    }

    @Test
    void shouldMakeMoveAndDraw() {
        inMemoryRepository = mock(InMemoryRepository.class);
        validationService = new ValidationServiceImpl();
        gameService = new GameServiceImpl(validationService, inMemoryRepository);
        GameState gameState = createNewGameState();
        GameState gameStateBackup = createNewGameState();
        Sign[][] tableState = gameState.getTableState();
        tableState[2][2] = Sign.O;
        tableState[1][2] = null;
        tableState[1][1] = Sign.X;
        Sign[][] tableStateBackup = cloneTableState(tableState);
        gameStateBackup.setTableState(tableStateBackup);
        CellCoordinates cellCoordinates = new CellCoordinates();
        cellCoordinates.setColumn(1);
        cellCoordinates.setRow(2);

        Mockito.when(inMemoryRepository.getGameStateInMemory()).thenReturn(gameState);

        GameState result = gameService.handleMove(cellCoordinates);

        Assert.assertFalse(Arrays.equals(result.getTableState(), gameStateBackup.getTableState()));
        Assert.assertEquals("Draw!", result.getVerdict());
    }

    private Sign[][] cloneTableState(Sign[][] tableState) {
        Sign[][] tableStateBackup = new Sign[3][3];
        BeanUtils.copyProperties(tableState, tableStateBackup);
        return tableStateBackup;
    }

    @Test
    void shouldMakeMoveAndNextPlayerTurn() {
        inMemoryRepository = mock(InMemoryRepository.class);
        validationService = new ValidationServiceImpl();
        gameService = new GameServiceImpl(validationService, inMemoryRepository);
        GameState gameState = createNewGameState();
        GameState gameStateBackup = createNewGameState();
        Sign[][] tableState = gameState.getTableState();
        tableState[2][2] = Sign.O;
        tableState[1][2] = null;
        tableState[1][1] = null;
        Sign[][] tableStateBackup = cloneTableState(tableState);
        gameStateBackup.setTableState(tableStateBackup);
        CellCoordinates cellCoordinates = new CellCoordinates();
        cellCoordinates.setColumn(1);
        cellCoordinates.setRow(2);

        Mockito.when(inMemoryRepository.getGameStateInMemory()).thenReturn(gameState);

        GameState result = gameService.handleMove(cellCoordinates);

        Assert.assertFalse(Arrays.equals(result.getTableState(), gameStateBackup.getTableState()));
        Assert.assertEquals("Next player: O", result.getVerdict());
    }

    private Sign[][] fillTableState() {
        //x;o;x
        //o;o;o
        //x;o;x
        Sign[][] tableState = new Sign[TABLE_BOARD][TABLE_BOARD];
        for (int column = 0; column < TABLE_BOARD; column++) {
            for (int row = 0; row < TABLE_BOARD; row++) {
                if ((column % 2) == 1 || (row % 2) == 1) {
                    tableState[column][row] = Sign.O;
                } else {
                    tableState[column][row] = Sign.X;
                }
            }
        }
        return tableState;
    }

    @Test
    void shouldFindGameState() {
        inMemoryRepository = mock(InMemoryRepository.class);
        validationService = new ValidationServiceImpl();
        gameService = new GameServiceImpl(validationService, inMemoryRepository);
        GameState gameState = createNewGameState();
        Sign[][] tableState = gameState.getTableState();
        tableState[1][1] = null;
        Sign nextPlayer = Sign.O;

        Mockito.when(inMemoryRepository.getGameStateInMemory()).thenReturn(gameState);

        GameState result = gameService.findGameState();

        Assert.assertEquals(nextPlayer, result.getCurrentPlayer());
    }

    @Test
    void shouldResetGameState() {
        inMemoryRepository = mock(InMemoryRepository.class);
        validationService = new ValidationServiceImpl();
        gameService = new GameServiceImpl(validationService, inMemoryRepository);
        GameState gameState = createNewGameState();
        Sign[][] emptyTableState = new Sign[TABLE_BOARD][TABLE_BOARD];

        Mockito.when(inMemoryRepository.getGameStateInMemory()).thenReturn(gameState);

        GameState result = gameService.resetGameState();
        Sign[][] resultTableState = result.getTableState();

        Assert.assertTrue(Arrays.deepEquals(resultTableState, emptyTableState));
    }
}