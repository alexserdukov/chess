package com.javamonkeys.service.statistics;

import com.javamonkeys.dao.statistics.GlobalStatistics;

import java.util.List;

public interface IStatisticsService {
    List<GlobalStatistics> getStatistics(Integer userId);
}
