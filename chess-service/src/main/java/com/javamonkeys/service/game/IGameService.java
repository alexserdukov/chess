package com.javamonkeys.service.game;

import com.javamonkeys.entity.game.Game;

public interface IGameService {
    Game createGame(Integer userId, Boolean isWhite, Integer gameLength);

    Game getGameById(Integer id);

    boolean updateGame(Integer id, Game game);

    boolean deleteGame(Integer id);
}
