package common;

import obstacles.Obstacle;

import java.util.ArrayList;

/**
 * Represents a map with obstacles.
 */
public class Map {
    private final ArrayList<Obstacle> obstacles = new ArrayList<>();
    private final int PADDING = 2;

    /**
     * Constructs a new Map object with the given obstacles.
     * @param obstacles The obstacles on the map
     */
    public Map(ArrayList<Obstacle> obstacles) {
        this.obstacles.addAll(obstacles);
    }

    /**
     * Returns the obstacle at the given location, or null if there is no obstacle at the given location.
     * @param x The x coordinate of the location
     * @param y The y coordinate of the location
     * @return The obstacle at the given location, or null if there is no obstacle at the given location
     */
    private Obstacle getObstacleAtLocation(int x, int y) {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.isLocationObstructed(x, y)) {
                return obstacle;
            }
        }
        return null;
    }

    /**
     * Returns a string representation of the map.
     * @return A string representation of the map
     */
    private String matrixToString(char[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for (char[] row : matrix) {
            for (char symbol : row) {
                sb.append(symbol);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Returns a string representation of the map with the given start and target locations.
     * @param start The start location
     * @param target The target location
     * @return A string representation of the map with the given start and target locations
     */
    public String getSolvedMap(Location start, Location target) {
        // Define the bounds (including padding) based on the start and target locations
        Location topLeft, bottomRight;
        int maxX, maxY, minX, minY;
        maxX = Math.max(start.getX(), target.getX());
        maxY = Math.max(start.getY(), target.getY());
        minX = Math.min(start.getX(), target.getX());
        minY = Math.min(start.getY(), target.getY());
        topLeft = new Location(minX - PADDING, minY - PADDING);
        bottomRight = new Location(maxX + PADDING, maxY + PADDING);

        // Create the map
        // +1 because the bounds are inclusive
        char[][] solvedMap = new char[bottomRight.getY() - topLeft.getY() + 1][bottomRight.getX() - topLeft.getX() + 1];
        for (int y = topLeft.getY(); y <= bottomRight.getY(); y++) {
            for (int x = topLeft.getX(); x <= bottomRight.getX(); x++) {
                // 1. Check start and target
                if (x == start.getX() && y == start.getY()) {
                    solvedMap[y - topLeft.getY()][x - topLeft.getX()] = 'S';
                    continue;
                }
                if (x == target.getX() && y == target.getY()) {
                    solvedMap[y - topLeft.getY()][x - topLeft.getX()] = 'E';
                    continue;
                }

                // 2. Check obstruction
                Obstacle obstructingObstacle = getObstacleAtLocation(x, y);
                // Calculate the index in the map 2D array
                int j = y - topLeft.getY();
                int i = x - topLeft.getX();
                if (obstructingObstacle != null) {
                    solvedMap[j][i] = obstructingObstacle.getSymbol();
                    continue;
                }

                // 3. Empty space
                solvedMap[j][i] = '.';
            }
        }

        // Convert the map to a string
        return matrixToString(solvedMap);
    }
}