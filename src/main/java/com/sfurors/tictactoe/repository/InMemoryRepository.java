package com.sfurors.tictactoe.repository;

import com.sfurors.tictactoe.model.GameState;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

@Getter
@Setter
@Repository
public class InMemoryRepository {

    private GameState gameStateInMemory = new GameState();

}
