package com.javamonkeys.service.game;

import com.javamonkeys.dao.game.IGameDao;
import com.javamonkeys.dao.user.IUserDao;
import com.javamonkeys.entity.game.Game;
import com.javamonkeys.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService implements IGameService {

    @Autowired
    IGameDao gameDao;

    @Autowired
    IUserDao userDao;

    @Override
    @Transactional
    public Game createGame(Integer userId, Boolean isWhite, Integer gameLength) {
        if (userId == null || isWhite == null || gameLength == null)
            return null;

        User user = userDao.getUserById(userId);
        if (user == null)
            return null;

        Game game = new Game(user, isWhite, gameLength);
        return gameDao.createGame(game);
    }

    @Override
    @Transactional(readOnly = true)
    public Game getGameById(Integer id) {
        if (id == null)
            return null;

        return gameDao.getGameById(id);
    }

    @Override
    @Transactional
    public boolean updateGame(Integer id, Game game) {
        if (id == null || game == null)
            return false;

        Game existGame = gameDao.getGameById(id);
        if (existGame == null)
            return false;

        existGame.setMatchDate(game.getMatchDate());
        existGame.setAuthor(game.getAuthor());
        existGame.setWhite(game.getWhite());
        existGame.setBlack(game.getBlack());
        existGame.setStartTime(game.getStartTime());
        existGame.setGameLength(game.getGameLength());
        existGame.setWhiteTime(game.getWhiteTime());
        existGame.setBlackTime(game.getBlackTime());
        existGame.setResult(game.getResult());
        existGame.setMoveText(game.getMoveText());
        existGame.setStatus(game.getStatus());

        return gameDao.updateGame(existGame);
    }

    @Override
    @Transactional
    public boolean deleteGame(Integer id) {
        if (id == null)
            return false;

        Game game = gameDao.getGameById(id);
        if (game == null)
            return false;

        return gameDao.deleteGame(game);
    }
}
