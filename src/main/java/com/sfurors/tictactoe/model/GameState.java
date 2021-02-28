package com.sfurors.tictactoe.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameState {
    public static final int BOARD_SIZE = 3;
    private Sign[][] tableState = new Sign[BOARD_SIZE][BOARD_SIZE];
    private String verdict;
    private Sign currentPlayer;
}
