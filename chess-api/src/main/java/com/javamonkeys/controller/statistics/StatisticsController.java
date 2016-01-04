package com.javamonkeys.controller.statistics;

import com.javamonkeys.dao.statistics.GlobalStatistics;
import com.javamonkeys.service.statistics.IStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/api/statistics")
public class StatisticsController implements IStatisticsController {

    @Autowired
    IStatisticsService statisticsService;

    @Override
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<List<GlobalStatistics>> getStatistics(@RequestParam(value = "userId", required = false) Integer userId) {
        if (userId == null)
            // top 10 users statistics
            return new ResponseEntity<>(statisticsService.getStatistics(null), HttpStatus.OK);
        else
            // user statistics
            return new ResponseEntity<>(statisticsService.getStatistics(userId), HttpStatus.OK);
    }
}
