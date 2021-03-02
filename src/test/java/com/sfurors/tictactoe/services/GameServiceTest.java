package com.sfurors.tictactoe.services;

import com.sfurors.tictactoe.models.CellCoordinates;
import com.sfurors.tictactoe.models.GameState;
import com.sfurors.tictactoe.models.Sign;
import com.sfurors.tictactoe.repositories.InMemoryRepository;
import com.sfurors.tictactoe.services.impl.GameServiceImpl;
import com.sfurors.tictactoe.services.impl.ValidationServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;

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
        GameState gameState = createNewGameState();
        GameState gameStateBackup = createNewGameState();
        CellCoordinates cellCoordinates = new CellCoordinates();
        cellCoordinates.setColumn(1);
        cellCoordinates.setRow(1);

        //when
        Mockito.when(inMemoryRepository.getGameStateInMemory()).thenReturn(gameState);
        GameState result = gameService.handleMove(cellCoordinates);

        //then
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
        //given
        GameState gameState = createNewGameState();
        GameState gameStateBackup = createNewGameState();
        Sign[][] tableState = gameState.getTableState();
        Sign[][] tableStateBackup = gameStateBackup.getTableState();
        tableState[1][1] = null;
        tableStateBackup[1][1] = null;
        CellCoordinates cellCoordinates = new CellCoordinates();
        cellCoordinates.setColumn(1);
        cellCoordinates.setRow(1);

        //when
        Mockito.when(inMemoryRepository.getGameStateInMemory()).thenReturn(gameState);
        GameState result = gameService.handleMove(cellCoordinates);

        //then
        Assert.assertFalse(Arrays.equals(result.getTableState(), gameStateBackup.getTableState()));
        Assert.assertEquals("Player O wins!", result.getVerdict());
    }

    @Test
    void shouldMakeMoveAndDraw() {
        //given
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

        //when
        Mockito.when(inMemoryRepository.getGameStateInMemory()).thenReturn(gameState);
        GameState result = gameService.handleMove(cellCoordinates);

        //then
        Assert.assertFalse(Arrays.equals(result.getTableState(), gameStateBackup.getTableState()));
        Assert.assertEquals("Draw!", result.getVerdict());
    }

    private Sign[][] cloneTableState(Sign[][] tableState) {
        Sign[][] tableStateBackup = new Sign[TABLE_SIZE][TABLE_SIZE];
        BeanUtils.copyProperties(tableState, tableStateBackup);
        return tableStateBackup;
    }

    @Test
    void shouldMakeMoveAndNextPlayerTurn() {
        //given
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

        //when
        Mockito.when(inMemoryRepository.getGameStateInMemory()).thenReturn(gameState);
        GameState result = gameService.handleMove(cellCoordinates);

        //then
        Assert.assertFalse(Arrays.equals(result.getTableState(), gameStateBackup.getTableState()));
        Assert.assertEquals("Next player: O", result.getVerdict());
    }

    private Sign[][] fillTableState() {
        //x;o;x
        //o;o;o
        //x;o;x
        Sign[][] tableState = new Sign[TABLE_SIZE][TABLE_SIZE];
        for (int column = 0; column < TABLE_SIZE; column++) {
            for (int row = 0; row < TABLE_SIZE; row++) {
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
        //given
        GameState gameState = createNewGameState();
        Sign[][] tableState = gameState.getTableState();
        tableState[1][1] = null;
        Sign nextPlayer = Sign.O;

        //when
        Mockito.when(inMemoryRepository.getGameStateInMemory()).thenReturn(gameState);
        GameState result = gameService.findGameState();

        //then
        Assert.assertEquals(nextPlayer, result.getCurrentPlayer());
    }

    @Test
    void shouldResetGameState() {
        //given
        GameState gameState = createNewGameState();
        Sign[][] emptyTableState = new Sign[TABLE_SIZE][TABLE_SIZE];

        //when
        Mockito.when(inMemoryRepository.getGameStateInMemory()).thenReturn(gameState);
        GameState result = gameService.resetGameState();

        //then
        Sign[][] resultTableState = result.getTableState();
        Assert.assertTrue(Arrays.deepEquals(resultTableState, emptyTableState));
    }
}