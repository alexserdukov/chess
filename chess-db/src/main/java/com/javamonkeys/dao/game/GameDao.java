package com.javamonkeys.dao.game;

import com.javamonkeys.dao.AbstractDao;
import com.javamonkeys.entity.game.Game;
import com.javamonkeys.entity.turn.Turn;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GameDao extends AbstractDao implements IGameDao {

    @Override
    public Game createGame(Game game) {
        save(game);
        return game;
    }

    @Override
    public Game getGameById(Integer id) {
        return (Game) getSession().get(Game.class, id);
    }

    @Override
    public List<Turn> getTurns(Game game) {
        Query query = getSession().createQuery("from Turn where game = :game");
        query.setParameter("game", game);

        return (List<Turn>) query.list();
    }

    @Override
    public boolean updateGame(Game game) {
        persist(game);
        return true;
    }

    @Override
    public boolean deleteGame(Game game) {
        delete(game);
        return true;
    }
}
