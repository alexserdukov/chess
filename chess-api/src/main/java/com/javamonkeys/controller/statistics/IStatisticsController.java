package com.javamonkeys.controller.statistics;

import com.javamonkeys.dao.statistics.GlobalStatistics;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IStatisticsController {
    ResponseEntity<List<GlobalStatistics>> getStatistics(Integer userId);
}
