package com.javamonkeys.dao.statistics;

import com.javamonkeys.dao.AbstractDao;
import com.javamonkeys.entity.game.GameStatus;
import com.javamonkeys.entity.user.User;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StatisticsDao extends AbstractDao implements IStatisticsDao {

    // if user == nul - return top 10 users statistics
    // else - return user statistics
    @Override
    public List<GlobalStatistics> getStatistics(User user) {

        List<GlobalStatistics> statisticsList = new ArrayList<>(10);
        User[] users = new User[10];

        // get winners
        String hql = "select game.winner as user, " +
                "count(*) as wins " +
                "from Game game " +
                "join game.winner " +
                "where game.status = :status " +
                ((user == null) ? "and game.winner is not null " : "and game.winner = :user ") +
                "group by game.winner " +
                "order by wins desc";
        Query query = getSession().createQuery(hql);
        query.setMaxResults(10);
        query.setParameter("status", GameStatus.FINISHED);
        if (user != null)
            query.setParameter("user", user);
        List<Object[]> results = query.list();
        int i = 0;
        for(Object[] result : results) {
            users[i] = (User) result[0];
            GlobalStatistics stat = new GlobalStatistics(++i, (User) result[0]);
            stat.setValue("win", ((Long) result[1]).intValue());
            statisticsList.add(stat);
        }

        // lose as white
        hql = "select " +
                "game.white as user, " +
                "count(*) as loses " +
                "from Game game " +
                "join game.white " +
                "where " +
                "game.white in :users " +
                "and game.status = :status " +
                "and game.winner is not  null " +
                "and game.winner != game.white " +
                "group by game.white";
        query = getSession().createQuery(hql);
        query.setParameter("status", GameStatus.FINISHED);
        query.setParameterList("users", users);
        results = query.list();
        for(Object[] result : results) {
            User currentUser = (User) result[0];
            for(GlobalStatistics stat : statisticsList) {
                if (stat.getUser() == currentUser) {
                    stat.setValue("lose", ((Long) result[1]).intValue());
                    break;
                }
            }
        }

        // lose as black
        hql = "select " +
                "game.black as user, " +
                "count(*) as loses " +
                "from Game game " +
                "join game.black " +
                "where " +
                "game.black in :users " +
                "and game.status = :status " +
                "and game.winner is not  null " +
                "and game.winner != game.black " +
                "group by game.black";
        query = getSession().createQuery(hql);
        query.setParameter("status", GameStatus.FINISHED);
        query.setParameterList("users", users);
        results = query.list();
        for(Object[] result : results) {
            User currentUser = (User) result[0];
            for(GlobalStatistics stat : statisticsList) {
                if (stat.getUser() == currentUser) {
                    Integer value = stat.getValueByName("lose");
                    if (value == null)
                        value = 0;
                    stat.setValue("lose", value + ((Long) result[1]).intValue());
                    break;
                }
            }
        }
        return statisticsList;
    }
}
