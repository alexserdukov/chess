package com.javamonkeys.dao.game;

import com.javamonkeys.entity.game.Game;
import com.javamonkeys.entity.turn.Turn;

import java.util.List;

public interface IGameDao {
    Game createGame(Game game);

    Game getGameById(Integer id);

    List<Turn> getTurns(Game game);

    boolean updateGame(Game game);

    boolean deleteGame(Game game);
}