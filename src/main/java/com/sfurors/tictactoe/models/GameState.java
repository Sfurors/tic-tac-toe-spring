package com.sfurors.tictactoe.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameState {
    public static final int TABLE_SIZE = 3;
    private Sign[][] tableState = new Sign[TABLE_SIZE][TABLE_SIZE];
    private String verdict;
    private Sign currentPlayer;
}
