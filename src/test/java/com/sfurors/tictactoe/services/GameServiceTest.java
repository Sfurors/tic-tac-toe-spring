package com.sfurors.tictactoe.services;

import com.sfurors.tictactoe.models.CellCoordinates;
import com.sfurors.tictactoe.models.GameState;
import com.sfurors.tictactoe.models.Sign;
import com.sfurors.tictactoe.repositories.InMemoryRepository;
import com.sfurors.tictactoe.services.impl.GameServiceImpl;
import com.sfurors.tictactoe.services.impl.ValidationServiceImpl;
import com.sfurors.tictactoe.utils.GameTableBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static com.sfurors.tictactoe.models.GameState.TABLE_SIZE;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
class GameServiceTest {

    InMemoryRepository inMemoryRepository;

    ValidationService validationService;

    GameService gameService;

    @BeforeEach
    void setUp() {
        inMemoryRepository = mock(InMemoryRepository.class);
        validationService = new ValidationServiceImpl();
        gameService = new GameServiceImpl(validationService, inMemoryRepository);
    }

    @Test
    void shouldNotMakeMoveCellOccupied() {
        //given
        GameTableBuilder gameTableBuilder = new GameTableBuilder();
        GameState gameState = new GameState();
        GameState gameStateBackup = new GameState();
        Sign[][] tableState = gameTableBuilder
                .setSigns(new Sign[]{null, null, null, null, Sign.O, null, null, null, null}).build();
        Sign[][] tableStateBackup = gameTableBuilder
                .setSigns(new Sign[]{null, null, null, null, Sign.O, null, null, null, null}).build();
        gameState.setTableState(tableState);
        gameStateBackup.setTableState(tableStateBackup);

        CellCoordinates cellCoordinates = new CellCoordinates();
        cellCoordinates.setColumn(1);
        cellCoordinates.setRow(1);

        //when
        Mockito.when(inMemoryRepository.getGameStateInMemory()).thenReturn(gameState);
        GameState result = gameService.handleMove(cellCoordinates);

        //then
        Assert.assertArrayEquals(result.getTableState(), gameStateBackup.getTableState());
    }

    @Test
    void shouldMakeMoveAndWin() {
        //given
        GameTableBuilder gameTableBuilder = new GameTableBuilder();
        GameState gameState = new GameState();
        GameState gameStateBackup = new GameState();
        Sign[][] tableState = gameTableBuilder
                .setSigns(new Sign[]{Sign.O, Sign.O, Sign.X, Sign.O, null, null, Sign.X, null, null}).build();
        Sign[][] tableStateBackup = gameTableBuilder
                .setSigns(new Sign[]{Sign.O, Sign.O, Sign.X, Sign.O, null, null, Sign.X, null, null}).build();
        gameState.setTableState(tableState);
        gameStateBackup.setTableState(tableStateBackup);

        CellCoordinates cellCoordinates = new CellCoordinates();
        cellCoordinates.setColumn(1);
        cellCoordinates.setRow(1);

        //when
        Mockito.when(inMemoryRepository.getGameStateInMemory()).thenReturn(gameState);
        GameState result = gameService.handleMove(cellCoordinates);

        //then
        Assert.assertFalse(Arrays.equals(result.getTableState(), gameStateBackup.getTableState()));
        Assert.assertEquals("X won", result.getVerdict());
    }

    @Test
    void shouldMakeMoveAndDraw() {
        //given
        GameTableBuilder gameTableBuilder = new GameTableBuilder();
        GameState gameState = new GameState();
        GameState gameStateBackup = new GameState();
        Sign[][] tableState = gameTableBuilder
                .setSigns(new Sign[]{Sign.O, Sign.O, Sign.X, Sign.X, null, Sign.O, Sign.O, Sign.X, Sign.O}).build();
        Sign[][] tableStateBackup = gameTableBuilder
                .setSigns(new Sign[]{Sign.O, Sign.O, Sign.X, Sign.X, null, Sign.O, Sign.O, Sign.X, Sign.O}).build();
        gameState.setTableState(tableState);
        gameStateBackup.setTableState(tableStateBackup);

        CellCoordinates cellCoordinates = new CellCoordinates();
        cellCoordinates.setColumn(1);
        cellCoordinates.setRow(1);

        //when
        Mockito.when(inMemoryRepository.getGameStateInMemory()).thenReturn(gameState);
        GameState result = gameService.handleMove(cellCoordinates);

        //then
        Assert.assertFalse(Arrays.equals(result.getTableState(), gameStateBackup.getTableState()));
        Assert.assertEquals("Draw", result.getVerdict());
    }

    @Test
    void shouldMakeMoveAndNextPlayerTurn() {
        //given
        GameTableBuilder gameTableBuilder = new GameTableBuilder();
        GameState gameState = new GameState();
        GameState gameStateBackup = new GameState();
        Sign[][] tableState = gameTableBuilder
                .setSigns(new Sign[]{Sign.O, Sign.O, Sign.X, Sign.X, null, null, Sign.O, Sign.X, Sign.O}).build();
        Sign[][] tableStateBackup = gameTableBuilder
                .setSigns(new Sign[]{Sign.O, Sign.O, Sign.X, Sign.X, null, null, Sign.O, Sign.X, Sign.O}).build();
        gameState.setTableState(tableState);
        gameStateBackup.setTableState(tableStateBackup);

        CellCoordinates cellCoordinates = new CellCoordinates();
        cellCoordinates.setColumn(1);
        cellCoordinates.setRow(2);

        //when
        Mockito.when(inMemoryRepository.getGameStateInMemory()).thenReturn(gameState);
        GameState result = gameService.handleMove(cellCoordinates);

        //then
        Assert.assertFalse(Arrays.deepEquals(result.getTableState(), gameStateBackup.getTableState()));
        Assert.assertNull(result.getVerdict());
    }

    @Test
    void shouldFindGameState() {
        //given
        GameTableBuilder gameTableBuilder = new GameTableBuilder();
        GameState gameState = new GameState();
        Sign[][] tableState = gameTableBuilder
                .setSigns(new Sign[]{Sign.O, Sign.O, Sign.X, Sign.X, null, Sign.O, Sign.X, Sign.X, Sign.O}).build();
        gameState.setTableState(tableState);
        Sign nextPlayer = Sign.O;

        //when
        Mockito.when(inMemoryRepository.getGameStateInMemory()).thenReturn(gameState);
        GameState result = gameService.findGameState();

        //then
        Assert.assertEquals(nextPlayer, result.getCurrentPlayer());
        Assert.assertTrue(Arrays.deepEquals(tableState, result.getTableState()));
    }

    @Test
    void shouldResetGameState() {
        //given
        GameTableBuilder gameTableBuilder = new GameTableBuilder();
        GameState gameState = new GameState();
        Sign[][] tableState = gameTableBuilder
                .setSigns(new Sign[]{Sign.O, Sign.O, Sign.X, Sign.X, null, Sign.O, Sign.O, Sign.X, Sign.O}).build();
        gameState.setTableState(tableState);
        Sign[][] emptyTableState = new Sign[TABLE_SIZE][TABLE_SIZE];
        Sign nextPlayerSign = Sign.O;

        //when
        Mockito.when(inMemoryRepository.getGameStateInMemory()).thenReturn(gameState);
        GameState result = gameService.resetGameState();

        //then
        Sign[][] resultTableState = result.getTableState();
        Assert.assertTrue(Arrays.deepEquals(resultTableState, emptyTableState));
        Assert.assertEquals(nextPlayerSign, result.getCurrentPlayer());
        Assert.assertNull(result.getVerdict());
    }
}