package com.javamonkeys.dao.statistics;

import com.javamonkeys.entity.user.User;

import java.util.List;

public interface IStatisticsDao {
    List<GlobalStatistics> getStatistics(User user);
}

