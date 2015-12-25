package com.javamonkeys.dao.statistics;

import com.javamonkeys.dao.AbstractDao;
import com.javamonkeys.entity.game.GameStatus;
import com.javamonkeys.entity.statistics.GlobalStatistics;
import com.javamonkeys.entity.statistics.UserStatistics;
import com.javamonkeys.entity.user.User;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatisticsDao extends AbstractDao implements IStatisticsDao {

    @Override
    public List<GlobalStatistics> getGlobalStatistics() {

//        Query query = getSession().createSQLQuery("SELECT USERS.NAME AS USERNAME, SUM(1) AS WIN, SUM(0) AS LOSE FROM GAMES LEFT JOIN USERS ON GAMES.WINNERID = USERS.ID WHERE GAMES.USERBLACKID = GAMES.WINNERID AND GAMES.STATUS != :statusFinished" +
//                " UNION ALL " +
//                "SELECT USERS.NAME, COUNT(1) AS WIN FROM GAMES LEFT JOIN USERS ON GAMES.USERBLACKID = USERS.ID WHERE GAMES.USERBLACKID = GAMES.WINNERID AND GAMES.STATUS != :statusFinished");
//        query.setParameter("status", GameStatus.FINISHED);
//
//        List result = query.list();
//        if (result.isEmpty()) {
//            return null;
//        } else {
//            return (User)result.get(0);
//        }
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
