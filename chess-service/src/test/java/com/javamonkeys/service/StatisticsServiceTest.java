package com.javamonkeys.service;

import com.javamonkeys.dao.statistics.GlobalStatistics;
import com.javamonkeys.dao.statistics.IStatisticsDao;
import com.javamonkeys.dao.user.IUserDao;
import com.javamonkeys.entity.user.User;
import com.javamonkeys.service.statistics.IStatisticsService;
import com.javamonkeys.service.statistics.StatisticsService;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

import static org.easymock.EasyMock.verify;
import static org.junit.Assert.*;

@RunWith(EasyMockRunner.class)
@Transactional
public class StatisticsServiceTest {

    @TestSubject
    IStatisticsService statisticsService = new StatisticsService();

    @Mock(fieldName = "statisticsDao")
    IStatisticsDao mockStatisticsDao;

    @Mock(fieldName = "userDao")
    IUserDao mockUserDao;

    private User testUser1, testUser2, testUser3;
    private GlobalStatistics stat1, stat2, stat3;

    @Before
    public void init() {
        testUser1 = new User("testUser1@javamonkeys.com", "12345");
        testUser1.setId(1);
        testUser2 = new User("testUser2@javamonkeys.com", "54321");
        testUser2.setId(2);
        testUser3 = new User("testUser3@javamonkeys.com", "54321");
        testUser3.setId(3);

        stat1 = new GlobalStatistics(1, testUser1);
        stat1.setValue("win", 20);
        stat1.setValue("lose", 5);

        stat2 = new GlobalStatistics(1, testUser2);
        stat2.setValue("win", 15);
        stat2.setValue("lose", 15);

        stat3 = new GlobalStatistics(1, testUser3);
        stat3.setValue("win", 5);
        stat3.setValue("lose", 20);
    }

    /* Test method: "getStatistics(null)"
    * global statistics */
    @Test
    public void getGlobalStatisticsTest() {
        List<GlobalStatistics> statisticsList = new ArrayList<>(3);
        statisticsList.add(stat1);
        statisticsList.add(stat2);
        statisticsList.add(stat3);
        expect(mockStatisticsDao.getStatistics(null)).andReturn(statisticsList).once();
        replay(mockUserDao);
        replay(mockStatisticsDao);

        List<GlobalStatistics> newStatisticsList = statisticsService.getStatistics(null);
        verify(mockUserDao);
        verify(mockStatisticsDao);
        assertNotNull(newStatisticsList);
        assertEquals(statisticsList, newStatisticsList);
    }

    /* Test method: "getStatistics(userId)"
    * user statistics */
    @Test
    public void getUserStatisticsTest() {
        List<GlobalStatistics> statisticsList = new ArrayList<>(1);
        statisticsList.add(stat1);
        expect(mockUserDao.getUserById(stat1.getUser().getId())).andReturn(stat1.getUser()).once();
        expect(mockStatisticsDao.getStatistics(testUser1)).andReturn(statisticsList).once();
        replay(mockUserDao);
        replay(mockStatisticsDao);

        List<GlobalStatistics> newStatisticsList = statisticsService.getStatistics(stat1.getUser().getId());
        verify(mockUserDao);
        verify(mockStatisticsDao);
        assertNotNull(newStatisticsList);
        assertEquals(statisticsList, newStatisticsList);
    }

    /* Test method: "getStatistics(userId)"
    * user statistics
    * User not found */
    @Test
    public void getUserStatisticsUserNotFoundTest() {
        expect(mockUserDao.getUserById(-1)).andReturn(null).once();
        replay(mockUserDao);
        replay(mockStatisticsDao);

        List<GlobalStatistics> statisticsList = statisticsService.getStatistics(-1);
        verify(mockUserDao);
        verify(mockStatisticsDao);
        assertNull(statisticsList);
    }
}
