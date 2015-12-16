package com.javamonkeys.dao.game;

import com.javamonkeys.entity.user.User;

import java.util.ArrayList;

public interface IGameDao {

    public Game getGame(int id);

    public Game createGame(User user, Boolean isWhite, long gameLength);

    public Game updateGame(Game game) throws GameNotFoundException;

    public void deleteGame(Game game) throws GameNotFoundException;

    public void deleteGame(int id) throws GameNotFoundException;

    public void saveTurn(int id, String turn) throws GameNotFoundException;

    public ArrayList<Game> getListGames(User author);

    public ArrayList<Game> getListGames(GameStatus status);

    public ArrayList<Turn> getGamesTurns(Game game);
}