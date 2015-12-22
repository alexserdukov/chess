package com.javamonkeys.controller.game;

import com.javamonkeys.entity.game.Game;
import com.javamonkeys.service.game.IGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/games")
public class GameController implements IGameController {

    @Autowired
    IGameService gameService;

    @Override
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<Game> createGame(@RequestHeader(value = "userId") Integer userId,
                                           @RequestHeader(value = "isWhite") Boolean isWhite,
                                           @RequestHeader(value = "gameLength") Long gameLength) {

        Game game = gameService.createGame(userId, isWhite, gameLength);
        return (game == null)
                ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(game, HttpStatus.CREATED);
    }

    @Override
    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<Game> getGameById(@PathVariable(value = "id") Integer id) {
        Game game = gameService.getGameById(id);
        return (game == null)
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(game, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateGame(@PathVariable(value = "id") Integer id, @RequestBody Game game) {
        return (gameService.updateGame(id, game))
                ? new ResponseEntity(HttpStatus.NO_CONTENT)
                : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteGame(@PathVariable(value = "id") Integer id) {
        return (gameService.deleteGame(id))
                ? new ResponseEntity(HttpStatus.NO_CONTENT)
                : new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
