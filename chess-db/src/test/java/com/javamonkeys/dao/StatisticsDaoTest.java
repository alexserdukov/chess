package com.javamonkeys.dao;

import com.javamonkeys.dao.game.IGameDao;
import com.javamonkeys.dao.statistics.GlobalStatistics;
import com.javamonkeys.dao.statistics.IStatisticsDao;
import com.javamonkeys.dao.statistics.Pair;
import com.javamonkeys.dao.user.IUserDao;
import com.javamonkeys.entity.game.Game;
import com.javamonkeys.entity.game.GameStatus;
import com.javamonkeys.entity.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-dao-config.xml"})
@Transactional
public class StatisticsDaoTest {

    @Autowired
    IStatisticsDao statisticsDao;

    @Autowired
    IUserDao userDao;

    @Autowired
    IGameDao gameDao;

    private User testUser1, testUser2;
    private Game testGame1, testGame2, testGame3, testGame4;

    @Before
    public void init() {
        testUser1 = userDao.createUser(new User("testUser1@javamonkeys.com", "12345"));
        testUser2 = userDao.createUser(new User("testUser2@javamonkeys.com", "12345"));

        testGame1 = new Game(testUser1, new Date(), true, 3600L);
        testGame1.setBlack(testUser2);
        testGame1.setStatus(GameStatus.FINISHED);
        testGame1.setWinner(testUser1);
        testGame1 = gameDao.createGame(testGame1);

        testGame2 = new Game(testUser1, new Date(), false, 3600L);
        testGame2.setBlack(testUser2);
        testGame2.setStatus(GameStatus.FINISHED);
        testGame2.setWinner(testUser1);
        testGame2 = gameDao.createGame(testGame2);

        testGame3 = new Game(testUser2, new Date(), true, 3600L);
        testGame3.setBlack(testUser1);
        testGame3.setStatus(GameStatus.FINISHED);
        testGame3.setWinner(testUser2);
        testGame3 = gameDao.createGame(testGame3);

        testGame4 = new Game(testUser2, new Date(), true, 3600L);
        testGame4.setBlack(testUser1);
        testGame4.setStatus(GameStatus.IN_PROGRESS);
        testGame4 = gameDao.createGame(testGame4);
    }

    /* Test method: "getStatistics(null)"
     * global statistics - top 10 */
    @Test
    public void testGetGlobalStatistics() {
        List<GlobalStatistics> statisticsList = statisticsDao.getStatistics(null);
        assertNotNull(statisticsList);
        assertEquals(2, statisticsList.size());

        GlobalStatistics element1 = statisticsList.get(0);
        assertEquals(new Integer(1), element1.getPlace());
        assertEquals(testUser1, element1.getUser());
        List<Pair<String, Integer>> pairList = element1.getPairList();
        assertNotNull(pairList);
        assertEquals(2, pairList.size());
        assertEquals("win", pairList.get(0).getLeft());
        assertEquals(new Integer(2), pairList.get(0).getRight());
        assertEquals("lose", pairList.get(1).getLeft());
        assertEquals(new Integer(1), pairList.get(1).getRight());

        GlobalStatistics element2 = statisticsList.get(1);
        assertEquals(new Integer(2), element2.getPlace());
        assertEquals(testUser2, element2.getUser());
        pairList = element2.getPairList();
        assertNotNull(pairList);
        assertEquals(2, pairList.size());
        assertEquals("win", pairList.get(0).getLeft());
        assertEquals(new Integer(1), pairList.get(0).getRight());
        assertEquals("lose", pairList.get(1).getLeft());
        assertEquals(new Integer(2), pairList.get(1).getRight());
    }

    /* Test method: "getStatistics(user)"
    * user statistics */
    @Test
    public void testGetUserStatistics() {
        List<GlobalStatistics> statisticsList = statisticsDao.getStatistics(testUser1);
        assertNotNull(statisticsList);
        assertEquals(1, statisticsList.size());

        GlobalStatistics element1 = statisticsList.get(0);
        assertEquals(new Integer(1), element1.getPlace());
        assertEquals(testUser1, element1.getUser());
        List<Pair<String, Integer>> pairList = element1.getPairList();
        assertNotNull(pairList);
        assertEquals(2, pairList.size());
        assertEquals("win", pairList.get(0).getLeft());
        assertEquals(new Integer(2), pairList.get(0).getRight());
        assertEquals("lose", pairList.get(1).getLeft());
        assertEquals(new Integer(1), pairList.get(1).getRight());
    }
}
