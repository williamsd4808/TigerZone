package Utilities;

import GameState.Board;
import GameState.Feature;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

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
            Point orientedFeaturePoint = new Point(rawOrientedFeaturePoint.x % 5, rawOrientedFeaturePoint.y % 5);
            Point orientedPlacedTile = new Point(rawOrientedFeaturePoint.x / 5, rawOrientedFeaturePoint.y / 5);

            if (board.containsElement(orientedPlacedTile)) {

                Board.PlacedTile placedTile = board.getTile(orientedPlacedTile);
                neighborFeatures.put(rawOrientedFeaturePoint, placedTile.getFeature(orientedFeaturePoint));

            }

        }

        return neighborFeatures;

    }

}
