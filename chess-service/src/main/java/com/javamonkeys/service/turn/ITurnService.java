package com.javamonkeys.service.turn;

import com.javamonkeys.entity.game.Game;
import com.javamonkeys.entity.turn.Pieces;
import com.javamonkeys.entity.turn.Turn;
import com.javamonkeys.entity.user.User;

import java.util.Date;
import java.util.List;

public interface ITurnService {
    Turn createTurn(Game game, User user, Date turnDate, Pieces piece, String startPosition, String endPosition, String fen, Boolean isGameOver);

    Turn getTurnById(Integer id);

    List<Turn> getTurns(Game game);

    Turn getLastTurn(Game game);

    boolean updateTurn(Integer id, Turn turn);

    boolean deleteTurn(Integer id);
}
