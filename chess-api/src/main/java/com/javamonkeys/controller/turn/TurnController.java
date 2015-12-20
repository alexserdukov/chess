package com.javamonkeys.controller.turn;

import com.javamonkeys.entity.game.Game;
import com.javamonkeys.entity.turn.Turn;
import com.javamonkeys.entity.user.User;
import com.javamonkeys.service.game.IGameService;
import com.javamonkeys.service.turn.ITurnService;
import com.javamonkeys.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/turns")
public class TurnController implements ITurnController {

    @Autowired
    ITurnService turnService;

    @Autowired
    IUserService userService;

    @Autowired
    IGameService gameService;

    @Override
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<Turn> createTurn(Integer gameId,
                                           Integer userId,
                                           String startPosition,
                                           String endPosition,
                                           String fen,
                                           Boolean isGameOver) {

        Game game = gameService.getGameById(gameId);
        User user = userService.getUserById(userId);

        Turn turn = turnService.createTurn(game, user, startPosition, endPosition, fen, isGameOver);
        return  (user == null)
                ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(turn, HttpStatus.CREATED);
    }

    @Override
    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<Turn> getTurnById(@PathVariable("id") Integer id) {
        Turn turn = turnService.getTurnById(id);
        return (turn == null)
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(turn, HttpStatus.OK);
    }

    @Override
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<List<Turn>> getTurns(@RequestParam(value = "gameId") Integer gameId,
                                               @RequestParam(value = "lastTurn") Boolean onlyLastTurn) {

        List<Turn> list = null;
        if (onlyLastTurn == null) {
            list = turnService.getTurns(gameService.getGameById(gameId));
            return (list == null)
                    ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                    : new ResponseEntity<>(list, HttpStatus.OK);
        } else if (onlyLastTurn) {
            Turn turn = turnService.getLastTurn(gameService.getGameById(gameId));
            if (turn != null) {
                list = new ArrayList<>(1);
                list.add(turn);
            }
            return (list == null)
                    ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                    : new ResponseEntity<>(list, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateTurn(@PathVariable("id") Integer id, @RequestBody Turn turn) {
        return (turnService.updateTurn(id, turn))
                ? new ResponseEntity(HttpStatus.NO_CONTENT)
                : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTurn(@PathVariable("id") Integer id) {
        return (turnService.deleteTurn(id))
                ? new ResponseEntity(HttpStatus.NO_CONTENT)
                : new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
