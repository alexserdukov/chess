package com.javamonkeys.entity.statistics;

import com.javamonkeys.entity.user.User;

import java.util.List;

public class GlobalStatistics {
    private Integer place;
    private User user;
    private List<UserStatistics> statisticsList;

    public GlobalStatistics(Integer place, User user, List<UserStatistics> statisticsList) {
        this.place = place;
        this.user = user;
        this.statisticsList = statisticsList;
    }

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<UserStatistics> getStatisticsList() {
        return statisticsList;
    }

    public void setStatisticsList(List<UserStatistics> statisticsList) {
        this.statisticsList = statisticsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GlobalStatistics that = (GlobalStatistics) o;

        if (place != null ? !place.equals(that.place) : that.place != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        return !(statisticsList != null ? !statisticsList.equals(that.statisticsList) : that.statisticsList != null);
    }

    @Override
    public int hashCode() {
        int result = place != null ? place.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (statisticsList != null ? statisticsList.hashCode() : 0);
        return result;
    }
}
