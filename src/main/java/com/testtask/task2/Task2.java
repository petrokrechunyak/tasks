package com.testtask.task2;

import java.io.*;
import java.util.*;

public class Task2 {

    private static final String INPUT = "input.txt";
    private static final String OUTPUT = "output.txt";
    private static final List<City> cities = new ArrayList<>();
    private static final int MAX_PATH_COST = 200000;

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT));
             BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT))) {
            int testsCount = Integer.parseInt(reader.readLine());

            for (int i = 0; i < testsCount; i++) {
                processTest(reader, writer);
                // skip empty line between tests
                reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Unable to parse file!");
        }
    }

    /**
     * Process a single test case by reading city data and finding paths.
     *
     * @param reader BufferedReader for input.
     * @param writer BufferedWriter for output.
     * @throws IOException If an I/O error occurs.
     */
    private static void processTest(BufferedReader reader, BufferedWriter writer) throws IOException {
        int citiesCount = Integer.parseInt(reader.readLine());
        // Clear the list for the current test case.
        cities.clear();

        // Load city data and connections
        loadCities(reader, citiesCount);

        int pathToFind = Integer.parseInt(reader.readLine());
        // Process the required paths
        for (int i = 0; i < pathToFind; i++) {

            String[] tokens = reader.readLine().split(" ");
            String from = tokens[0];
            String to = tokens[1];

            int cost = 0;
            try {
                cost = findShortestPath(from, to);
            } catch (RuntimeException e) {
                writer.write(e.getMessage());
                writer.newLine();
                continue;
            }

            // Write the result to the output file
            writer.write(String.valueOf(cost));
            writer.newLine();
        }
    }

    /**
     * Loads city data and their connections from the input file.
     *
     * @param reader      BufferedReader to read data.
     * @param citiesCount Number of cities to process.
     * @throws IOException If an I/O error occurs.
     */
    private static void loadCities(BufferedReader reader, int citiesCount) throws IOException {
        for (int j = 0; j < citiesCount; j++) {
            String cityName = reader.readLine();
            int connectionsCount = Integer.parseInt(reader.readLine());
            Map<Integer, Integer> connections = new HashMap<>();

            // Load connected cities and path cost
            for (int k = 0; k < connectionsCount; k++) {
                String[] tokens = reader.readLine().split(" ");
                int nextCityId = Integer.parseInt(tokens[0]) - 1;
                int pathCost = Integer.parseInt(tokens[1]);
                connections.put(nextCityId, pathCost);
            }

            cities.add(new City(j, cityName, connections));
        }
    }

    /**
     * Finds the shortest path between two cities
     *
     * @param from Name of the source city.
     * @param to   Name of the destination city.
     * @return The minimum transportation cost.
     * @throws RuntimeException if no path found
     */
    private static int findShortestPath(String from, String to) throws RuntimeException {
        City fromCity = findCityByName(from);
        City toCity = findCityByName(to);

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1])); // {cityId, cost}
        pq.add(new int[]{fromCity.getId(), 0});

        // Initialize minimum costs for all cities
        Map<Integer, Integer> minCosts = initializeMinCosts();
        minCosts.put(fromCity.getId(), 0);

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int currentCityId = current[0];
            int currentCost = current[1];

            // Stop if we reach the destination city
            if (currentCityId == toCity.getId()) {
                return currentCost;
            }

            // Skip if path cost more that minimum one
            if (currentCost > minCosts.get(currentCityId)) {
                continue;
            }

            City currentCity = cities.get(currentCityId);

            // Explore neighbors
            for (Map.Entry<Integer, Integer> neighbor : currentCity.getDistances().entrySet()) {
                int neighborId = neighbor.getKey();
                int pathCost = neighbor.getValue();
                int newCost = currentCost + pathCost;

                // Update the cost if a cheaper path is found
                if (newCost < minCosts.get(neighborId)) {
                    minCosts.put(neighborId, newCost);
                    pq.offer(new int[]{neighborId, newCost});
                }
            }
        }

        // If no path exists
        throw new RuntimeException("Unable to find path from " + from + " to " + to);
    }

    /**
     * Finds a city by its name.
     *
     * @param name Name of the city.
     * @return The corresponding City object.
     * @throws IllegalArgumentException If the city is not found.
     */
    private static City findCityByName(String name) {
        return cities.stream()
                .filter(city -> city.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unable to find city " + name));
    }

    /**
     * Initializes a map of minimum costs with maximum integer values for all cities.
     *
     * @return A map where the keys are city number and values are integer max value.
     */
    private static Map<Integer, Integer> initializeMinCosts() {
        Map<Integer, Integer> minCosts = new HashMap<>();
        for (City city : cities) {
            minCosts.put(city.getId(), MAX_PATH_COST);
        }
        return minCosts;
    }
}
