package com.testtask.task2;

import java.util.HashMap;
import java.util.Map;

public class City {

    private int id;
    private String name;
    private Map<Integer, Integer> distances;

    public City(int id, String name, Map<Integer, Integer> distances) {
        this.id = id;
        this.name = name;
        this.distances = distances;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Integer, Integer> getDistances() {
        return distances;
    }

    public void setDistances(Map<Integer, Integer> distances) {
        this.distances = distances;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", distances=" + distances +
                '}';
    }
}
