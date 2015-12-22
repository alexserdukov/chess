package com.javamonkeys.dao.turn;

import com.javamonkeys.entity.game.Game;
import com.javamonkeys.entity.turn.Turn;

import java.util.List;

public interface ITurnDao {
    Turn createTurn(Turn turn);

    Turn getTurnById(Integer id);

    List<Turn> getTurns(Game game);

    Turn getLastTurn(Game game);

    boolean updateTurn(Turn turn);

    boolean deleteTurn(Turn turn);
}
