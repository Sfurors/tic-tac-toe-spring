package com.sfurors.tictactoe.utils;

import com.sfurors.tictactoe.models.Sign;

import static com.sfurors.tictactoe.models.GameState.TABLE_SIZE;

public class GameTableBuilder {

    Sign[] signs;

    public GameTableBuilder() {
        this.signs = signs;
    }

    public GameTableBuilder setSigns(Sign[] signs) {
        this.signs = signs;
        return this;
    }

    public Sign[][] build() {
        Sign[][] tableState = new Sign[TABLE_SIZE][TABLE_SIZE];
        int counter = 0;
        for (int column = 0; column < TABLE_SIZE; column++) {
            for (int row = 0; row < TABLE_SIZE; row++) {
                tableState[column][row] = signs[counter++];
            }
        }
        return tableState;
    }
}
