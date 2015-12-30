package com.javamonkeys.dao;

import com.javamonkeys.dao.statistics.IStatisticsDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-dao-config.xml"})
@Transactional
public class StatisticsDaoTest {

    @Autowired
    IStatisticsDao statisticsDao;

    @Test
    public void testGlobal() {
        statisticsDao.getGlobalStatistics();
    }
}
