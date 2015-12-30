package com.javamonkeys.dao.statistics;

import com.javamonkeys.dao.AbstractDao;
import com.javamonkeys.entity.game.GameStatus;
import com.javamonkeys.entity.statistics.GlobalStatistics;
import com.javamonkeys.entity.statistics.UserStatistics;
import com.javamonkeys.entity.user.User;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;

@Repository
public class StatisticsDao extends AbstractDao implements IStatisticsDao {

    @Override
    public List<GlobalStatistics> getGlobalStatistics() {

        String hql = "";
//        String hql = "select " +
//                "game.winner " +
////                "count(game.id) " +
//                "from Game game " +
//                "where " +
//                "status = :status ";
////                "and not winner is null " +
////                "group by " +
////                "winner";
        hql = "select game.winner, game.id from Game game where status = :status";
        Query query = getSession().createQuery(hql);
        query.setParameter("status", GameStatus.FINISHED);

        for(Iterator it=query.iterate();it.hasNext();)
        {
            System.out.println("1");
        }
    return null;
    }

    @Override
    public List<UserStatistics> getUserStatistics(User user) {
//        Query query = getSession().createQuery("from User where LOWER(email) = :email");
//        query.setParameter("email", email.toLowerCase());
//
//        List result = query.list();
//        if (result.isEmpty()) {
//            return null;
//        } else {
//            return (User)result.get(0);
//        }
        return null;
    }
}
