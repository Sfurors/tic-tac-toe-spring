package com.sfurors.tictactoe.service;

import com.sfurors.tictactoe.model.CellCoordinates;
import com.sfurors.tictactoe.model.Sign;
import com.sfurors.tictactoe.service.impl.ValidationServiceImpl;
import com.sfurors.tictactoe.util.GameTableBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static com.sfurors.tictactoe.model.GameState.TABLE_SIZE;
import static org.junit.jupiter.api.Assertions.*;

class ValidationServiceTest {

    ValidationService validationService = new ValidationServiceImpl();

    @Test
    void shouldReturnTrueAsMoveIsValid() {
        GameTableBuilder gameTableBuilder = new GameTableBuilder();
        Sign[][] TableState = gameTableBuilder.setSigns(new Sign[]{Sign.O, null, null, null, null, null, null, null, null}).build();
        CellCoordinates cellCoordinates = new CellCoordinates();
        cellCoordinates.setColumn(0);
        cellCoordinates.setRow(1);

        boolean result = validationService.validateMove(TableState, cellCoordinates);

        Assert.assertTrue(result);
    }

    @Test
    void shouldReturnFalseAsMoveIsInvalid() {
        GameTableBuilder gameTableBuilder = new GameTableBuilder();
        Sign[][] tableState = gameTableBuilder
                .setSigns(new Sign[]{Sign.O, null, null, null, null, null, null, null, null}).build();
        CellCoordinates cellCoordinates = new CellCoordinates();
        cellCoordinates.setColumn(0);
        cellCoordinates.setRow(0);

        boolean result = validationService.validateMove(tableState, cellCoordinates);

        Assert.assertFalse(result);
    }

    @Test
    void shouldReturnTrueAsPlayerWinsHorizontal() {
        GameTableBuilder gameTableBuilder = new GameTableBuilder();
        Sign[][] tableState = gameTableBuilder
                .setSigns(new Sign[]{Sign.O, Sign.O, Sign.O, Sign.X, Sign.X, Sign.O, Sign.O, Sign.X, Sign.X}).build();
        Sign currentMoveSign = Sign.O;

        boolean result = validationService.checkWin(currentMoveSign, tableState);

        Assert.assertTrue(result);
    }

    @Test
    void shouldReturnTrueAsPlayerWinsVertical() {
        GameTableBuilder gameTableBuilder = new GameTableBuilder();
        Sign[][] tableState = gameTableBuilder
                .setSigns(new Sign[]{Sign.X, Sign.O, Sign.O, Sign.X, Sign.O, Sign.O, Sign.X, Sign.X, null}).build();
        Sign currentMoveSign = Sign.X;

        boolean result = validationService.checkWin(currentMoveSign, tableState);

        Assert.assertTrue(result);
    }

    @Test
    void shouldReturnTrueAsPlayerWinsDiagonalFromTop() {
        GameTableBuilder gameTableBuilder = new GameTableBuilder();
        Sign[][] tableState = gameTableBuilder
                .setSigns(new Sign[]{Sign.O, Sign.X, null, Sign.X, Sign.O, null, null, null, Sign.O}).build();
        Sign currentMoveSign = Sign.O;

        boolean result = validationService.checkWin(currentMoveSign, tableState);

        Assert.assertTrue(result);
    }

    @Test
    void shouldReturnTrueAsPlayerWinsDiagonalFromBottom() {
        GameTableBuilder gameTableBuilder = new GameTableBuilder();
        Sign[][] tableState = gameTableBuilder
                .setSigns(new Sign[]{Sign.O, Sign.O, Sign.X, null, Sign.X, null, Sign.X, null, null}).build();
        Sign currentMoveSign = Sign.X;

        boolean result = validationService.checkWin(currentMoveSign, tableState);

        Assert.assertTrue(result);
    }

    @Test
    void shouldReturnTrueAsItIsDraw() {
        GameTableBuilder gameTableBuilder = new GameTableBuilder();
        Sign[][] tableState = gameTableBuilder
                .setSigns(new Sign[]{Sign.O, Sign.O, Sign.X, Sign.X, Sign.O, Sign.O, Sign.X, Sign.X, Sign.O}).build();
        Sign currentMoveSign = Sign.O;

        boolean result = validationService.checkWin(currentMoveSign, tableState);

        Assert.assertTrue(result);
    }
}