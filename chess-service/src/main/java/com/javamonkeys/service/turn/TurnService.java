package com.javamonkeys.service.turn;

import com.javamonkeys.dao.game.IGameDao;
import com.javamonkeys.dao.turn.ITurnDao;
import com.javamonkeys.entity.game.Game;
import com.javamonkeys.entity.game.GameStatus;
import com.javamonkeys.entity.turn.Turn;
import com.javamonkeys.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TurnService implements ITurnService {

    @Autowired
    ITurnDao turnDao;

    @Autowired
    IGameDao gameDao;

    @Override
    @Transactional
    public Turn createTurn(Game game, User user, String startPosition, String endPosition, String fen, Boolean isGameOver) {
        if (game == null
                || user == null
                || startPosition == null
                || endPosition == null
                || fen == null
                || isGameOver == null)
            return null;

        Turn turn = new Turn(game, user, new Date(), null, startPosition, endPosition, fen);
        turn = turnDao.createTurn(turn);

        if (turn != null) {
            game.setStatus(GameStatus.FINISHED);
            gameDao.updateGame(game);
        }

        return turn;
    }

    @Override
    @Transactional(readOnly = true)
    public Turn getTurnById(Integer id) {
        if (id == null)
            return null;

        return turnDao.getTurn(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Turn> getTurns(Game game) {
        if (game == null)
            return null;

        return turnDao.getTurns(game);
    }

    @Override
    @Transactional(readOnly = true)
    public Turn getLastTurn(Game game) {
        if (game == null)
            return null;

        return turnDao.getLastTurn(game);
    }

    @Override
    @Transactional
    public boolean updateTurn(Integer id, Turn turn) {
        if (id == null || turn == null)
            return false;

        Turn existTurn = turnDao.getTurn(id);
        if (existTurn == null)
            return false;

        existTurn.setGame(turn.getGame());
        existTurn.setUser(turn.getUser());
        existTurn.setTurnDate(turn.getTurnDate());
        existTurn.setPiece(turn.getPiece());
        existTurn.setStartPosition(turn.getStartPosition());
        existTurn.setEndPosition(turn.getEndPosition());
        existTurn.setFen(turn.getFen());

        return turnDao.updateTurn(turn);
    }

    @Override
    @Transactional
    public boolean deleteTurn(Integer id) {
        if (id == null)
            return false;

        Turn turn = turnDao.getTurn(id);
        if (turn == null)
            return false;

        return turnDao.deleteTurn(turn);
    }
}
