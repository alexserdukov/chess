package com.javamonkeys.controller.turn;

import com.javamonkeys.entity.turn.Turn;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITurnController {
    ResponseEntity<Turn> createTurn(Integer gameId,
                                    Integer userId,
                                    String startPosition,
                                    String endPosition,
                                    String fen,
                                    Boolean isGameOver);

    ResponseEntity<Turn> getTurnById(Integer id);

    ResponseEntity<List<Turn>> getTurns(Integer gameId, Boolean onlyLastTurn);

    ResponseEntity updateTurn(Integer id, Turn turn);

    ResponseEntity deleteTurn(Integer id);
}
