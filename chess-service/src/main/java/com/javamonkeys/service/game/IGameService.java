package com.javamonkeys.service.game;

import com.javamonkeys.entity.game.Game;

import java.util.Date;

public interface IGameService {
    Game createGame(Integer userId, Date matchDate, Boolean isWhite, Long gameLength);

    Game getGameById(Integer id);

    boolean updateGame(Integer id, Game game);

    boolean deleteGame(Integer id);
}
