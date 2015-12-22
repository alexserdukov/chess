package com.javamonkeys.controller.game;

import com.javamonkeys.entity.game.Game;
import org.springframework.http.ResponseEntity;

public interface IGameController {
    ResponseEntity<Game> createGame(Integer userId, Boolean isWhite, Long gameLength);

    ResponseEntity<Game> getGameById(Integer id);

    ResponseEntity updateGame(Integer id, Game game);

    ResponseEntity deleteGame(Integer id);
}
