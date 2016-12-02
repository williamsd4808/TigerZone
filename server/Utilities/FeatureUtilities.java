package Utilities;

import GameState.Board;
import GameState.Feature;
import Utilities.MathUtilities;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.Queue;
import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Austin Seber2 on 11/23/2016.
 */
public class FeatureUtilities {

    // This method expects an int x and y that correspond to the exact center of the center tile
    // Given this information, this method will return valid features adjacent to the current feature regardless of what PlaceTile they reside in

    private static final Map<Board.Orientation, Board.Transform> neighborTilesTransform = new HashMap<Board.Orientation, Board.Transform>() {

        {

            put(Board.Orientation.NORTH, (point) -> new Point(point.x, point.y + 1));
            put(Board.Orientation.EAST, (point) -> new Point(point.x + 1, point.y));
            put(Board.Orientation.SOUTH, (point) -> new Point(point.x, point.y - 1));
            put(Board.Orientation.WEST, (point) -> new Point(point.x - 1, point.y));

        }

    };

    public static Map<Point, Feature> getNeighborFeatures(Board board, int x, int y) {

        return getNeighborFeatures(board, new Point(x, y));

    }

    public static Map<Point, Feature> getNeighborFeatures(Board board, Point point) {

        Map<Point, Feature> neighborFeatures = new HashMap<>();

        for (Board.Orientation orientation : Board.Orientation.values()) {

            Point rawOrientedFeaturePoint = neighborTilesTransform.get(orientation).Transform(point);
            Point orientedFeaturePoint = getLocalFeaturePoint(rawOrientedFeaturePoint);
            Point orientedPlacedTile = getGlobalTilePoint(rawOrientedFeaturePoint);

            if (board.containsElement(orientedPlacedTile)) {

                Board.PlacedTile placedTile = board.getTile(orientedPlacedTile);
                neighborFeatures.put(rawOrientedFeaturePoint, placedTile.getFeature(orientedFeaturePoint));

            }

        }

        return neighborFeatures;

    }

    // Expects: the board you intend to find the extent of
    // Expects: a start point in terms of global feature locations
    // Expects: the type of feature you are trying to find the extent of
    // Returns a map of global feature points that are connected to this feature on this start point

    public static Set<Point> getExtentOfFeature(Board board, Point startFeaturePoint, Feature featureType) {

        HashSet<Point> featureExtent = new HashSet<>();
        Queue<Point> featurePointQueue = new LinkedBlockingQueue<>();

        if (board.getTile(getGlobalTilePoint(startFeaturePoint)).getFeature(getLocalFeaturePoint(startFeaturePoint)) != featureType) {

            return featureExtent;

        }

        featurePointQueue.offer(startFeaturePoint);

        while (!featurePointQueue.isEmpty()) {

            Point globalFeaturePoint = featurePointQueue.poll();

            featureExtent.add(globalFeaturePoint);
            Point globalTilePoint = getGlobalTilePoint(globalFeaturePoint);

            Map<Point, Feature> neighborFeatures = getNeighborFeatures(board, globalFeaturePoint);

            for (Map.Entry<Point, Feature> entry : neighborFeatures.entrySet()) {

                Point entryPoint = entry.getKey();
                Feature entryFeature = entry.getValue();

                if (entryFeature == featureType) {

                    Point entryGlobalTilePoint = getGlobalTilePoint(entryPoint);
                    Point entryLocalFeaturePoint = getLocalFeaturePoint(entryPoint);

                    if (globalTilePoint.equals(entryGlobalTilePoint) || isConnectionPoint(entryLocalFeaturePoint)) {

                        if (!featureExtent.contains(entryPoint)) {

                            featurePointQueue.offer(entryPoint);

                        }

                    }

                }

            }

        }

        return featureExtent;

    }

    public static int getOpenEdgesInExtent(Set<Point> extentOfFeature) {

        int count = 0;

        for (Point extentPoint : extentOfFeature) {

            if (isConnectionPoint(extentPoint)) {

                Point correspondingConnectionPoint = getCorrespondingConnectionPoint(extentPoint);

                if (!extentOfFeature.contains(correspondingConnectionPoint)) {

                    count++;

                }

            }

        }

        return count;

    }

    public static Point getCorrespondingConnectionPoint(Point point) {

        if (isConnectionPoint(point)) {

            return PointUtilities.getPointFromOrientation(point, getConnectionPointOrientation(point));

        }

        throw new RuntimeException("Cannot find corresponding connection point for a point that isn't a connection point");

    }

    public static Board.Orientation getConnectionPointOrientation(Point point) {

        if (isConnectionPoint(point)) {

            Point localFeaturePoint = getLocalFeaturePoint(point);

            if (localFeaturePoint.x == 2 && localFeaturePoint.y == 0) {

                return Board.Orientation.SOUTH;

            } else if (localFeaturePoint.x == 2 && localFeaturePoint.y == 4) {

                return Board.Orientation.NORTH;

            } else if (localFeaturePoint.x == 0 && localFeaturePoint.y == 2) {

                return Board.Orientation.WEST;

            } else if (localFeaturePoint.x == 4 && localFeaturePoint.y == 2) {

                return Board.Orientation.EAST;

            }

        }

        throw new RuntimeException("Cannot find connection orientation connection point for a point that isn't a connection point");

    }

    public static boolean isConnectionPoint(Point point) {

        Point localFeaturePoint = getLocalFeaturePoint(point);

        if ((localFeaturePoint.x == 2 && localFeaturePoint.y == 0) ||
            (localFeaturePoint.x == 2 && localFeaturePoint.y == 4) ||
            (localFeaturePoint.x == 0 && localFeaturePoint.y == 2) ||
            (localFeaturePoint.x == 4 && localFeaturePoint.y == 2)) {

            return true;

        }

        return false;

    }

    public static Point getGlobalTilePoint(Point point) {

        return new Point((int) Math.floor(point.getX() / 5.0), (int) Math.floor(point.getY() / 5.0));

    }

    public static Point getLocalFeaturePoint(Point point) {

        Point globalTilePoint = getGlobalTilePoint(point);

        return new Point(5 - ((globalTilePoint.x + 1) * 5 - point.x), 5 - ((globalTilePoint.y + 1) * 5 - point.y));

    }

    public static Point getGlobalFeaturePoint(Board.PlacedTile placedTile, Point localFeaturePoint) {

        return new Point(placedTile.location.x * 5 + localFeaturePoint.x, placedTile.location.y * 5 + localFeaturePoint.y);

    }

    public static Collection<Point> getPlacedTilesInExtent(Board board, Set<Point> extent) {

        HashSet<Point> visitedPlacedTileLocations = new HashSet<>();

        for (Point extentPoint : extent) {

            Point featurePoint = FeatureUtilities.getGlobalTilePoint(extentPoint);

            if (!visitedPlacedTileLocations.contains(featurePoint)) {

                visitedPlacedTileLocations.add(featurePoint);

            }

        }

        return visitedPlacedTileLocations;

    }

}
