package com.javamonkeys.dao.turn;

import com.javamonkeys.dao.AbstractDao;
import com.javamonkeys.entity.game.Game;
import com.javamonkeys.entity.turn.Turn;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TurnDao extends AbstractDao implements ITurnDao{

    @Override
    public Turn createTurn(Turn turn) {
        save(turn);
        return turn;
    }

    @Override
    public Turn getTurnById(Integer id) {
        return (Turn) getSession().get(Turn.class, id);
    }

    @Override
    public List<Turn> getTurns(Game game) {
        Query query = getSession().createQuery("from Turn where game = :game");
        query.setParameter("game", game);
        return (List<Turn>) query.list();
    }

    @Override
    public Turn getLastTurn(Game game) {
        Query query = getSession().createQuery("from Turn where game = :game order by id desc");
        query.setParameter("game", game);
        query.setMaxResults(1);
        return (Turn) query.uniqueResult();
    }

    @Override
    public boolean updateTurn(Turn turn) {
        persist(turn);
        return true;
    }

    @Override
    public boolean deleteTurn(Turn turn) {
        delete(turn);
        return true;
    }
}
