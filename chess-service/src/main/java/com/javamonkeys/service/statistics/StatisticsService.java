package com.javamonkeys.service.statistics;

import com.javamonkeys.dao.statistics.GlobalStatistics;
import com.javamonkeys.dao.statistics.IStatisticsDao;
import com.javamonkeys.dao.user.IUserDao;
import com.javamonkeys.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StatisticsService implements IStatisticsService {

    @Autowired
    IStatisticsDao statisticsDao;

    @Autowired
    IUserDao userDao;

   @Override
    @Transactional(readOnly = true)
    public List<GlobalStatistics> getStatistics(Integer userId) {
        if (userId == null)
            // top 10 users statistics
            return statisticsDao.getStatistics(null);

        User user = userDao.getUserById(userId);
        if (user == null)
            return null;

       // user statistics
        return statisticsDao.getStatistics(user);
    }
}
