package com.javamonkeys.dao.statistics;

import com.javamonkeys.entity.user.User;

import java.util.ArrayList;
import java.util.List;

public class GlobalStatistics {
    private Integer place;
    private User user;
    private List<Pair<String, Integer>> pairList = new ArrayList<>();

    public GlobalStatistics(Integer place, User user) {
        this.place = place;
        this.user = user;
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

    public List<Pair<String, Integer>> getPairList() {
        return pairList;
    }

    public void setPairList(List<Pair<String, Integer>> pairList) {
        this.pairList = pairList;
    }

    public Integer getValueByName(String name) {
        for (Pair<String, Integer> pair : pairList) {
            if (pair.getLeft() != null && pair.getLeft().equals(name))
                return pair.getRight();
        }
        return null;
    }

    public void setValue(String name, Integer value) {
        for (Pair<String, Integer> pair : pairList) {
            if (pair.getLeft() != null && pair.getLeft().equals(name)) {
                pair.setRight(value);
                return;
            }
        }
        Pair<String, Integer> pair = new Pair<>(name, value);
        pairList.add(pair);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GlobalStatistics that = (GlobalStatistics) o;

        if (place != null ? !place.equals(that.place) : that.place != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        return !(pairList != null ? !pairList.equals(that.pairList) : that.pairList != null);
    }

    @Override
    public int hashCode() {
        int result = place != null ? place.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (pairList != null ? pairList.hashCode() : 0);
        return result;
    }
}
