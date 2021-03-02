package com.sfurors.tictactoe.repositories;

import com.sfurors.tictactoe.models.GameState;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

@Getter
@Setter
@Repository
public class InMemoryRepository {

    private GameState gameStateInMemory = new GameState();

}
