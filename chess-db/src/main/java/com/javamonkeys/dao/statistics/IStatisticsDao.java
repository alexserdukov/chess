package com.javamonkeys.dao.statistics;

import com.javamonkeys.entity.statistics.GlobalStatistics;
import com.javamonkeys.entity.statistics.UserStatistics;
import com.javamonkeys.entity.user.User;

import java.util.List;

public interface IStatisticsDao {
    List<GlobalStatistics> getGlobalStatistics();

    List<UserStatistics> getUserStatistics(User user);
}

