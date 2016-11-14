package Utilities;

import GameState.Board;
import GameState.Tile;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by asebe on 11/10/2016.
 */
public class TileUtilities {

    private static final HashMap<Board.Orientation, Tuple<Point, Point>> connectionHashMap = new HashMap<Board.Orientation, Tuple<Point, Point>>() {

        {

            put(Board.Orientation.NORTH, Tuple.Create(new Point(0, 2), new Point(4, 2)));
            put(Board.Orientation.EAST, Tuple.Create(new Point(2, 4), new Point(2, 0)));
            put(Board.Orientation.SOUTH, Tuple.Create(new Point(4, 2), new Point(0, 2)));
            put(Board.Orientation.WEST, Tuple.Create(new Point(2, 0), new Point(2, 4)));

        }

    };

    // Expects: a placedTile on the board
    // Expects: the board you expect to be working with
    // Returns: a collection of edges that do not currently have tiles placed on it

    public static Collection<Board.Orientation> getOpenEdges(Board.PlacedTile placedTile, Board board) {

        HashSet<Board.Orientation> openEdges = new HashSet<>();

        for (Board.Orientation orientation : Board.Orientation.values()) {

            Point orientedPoint = PointUtilities.getPointFromOrientation(placedTile.location, orientation);

            if (!board.containsElement(orientedPoint)) {

                openEdges.add(orientation);

            }

        }

        return openEdges;

    }

    // Expects: a placedTile on the board that we will be connecting to
    // Expects: a tile you intend to place on the board
    // Expects: an orientation you intend to place that tile in
    // Expects: the board you expect to be working with
    // Returns: a collection of orientations that the new tile may be validly placed in

    public static Collection<Board.Orientation> getValidOrientationsFromPlacedTile(Board.PlacedTile placedTile, Tile newTile, Board.Orientation directionFromPlacedTile, Board board) {

        HashSet<Board.Orientation> validOrientations = new HashSet<>();

        if (getOpenEdges(placedTile, board).contains(directionFromPlacedTile)) {

            for (Board.Orientation orientation : Board.Orientation.values()) {

                if (canConnectNewTile(placedTile, newTile, directionFromPlacedTile, orientation)) {

                    validOrientations.add(orientation);

                }

            }

        }

        return validOrientations;

    }

    // Expects: a placedTile on the board that we will be connecting to
    // Expects: a tile you intend to place on the board
    // Expects: an orientation you are placing the new tile in with respect to the placed tile (aka, above below, right, or left of it)
    // Expects: an orientation you intend to place that tile in
    // Returns: a boolean whether or not you can place that tile in that orientation in the direction of the placed tile

    public static boolean canConnectNewTile(Board.PlacedTile placedTile, Tile newTile, Board.Orientation directionFromPlacedTile, Board.Orientation newTileOrientation) {

        Board.PlacedTile newPlacedTile = new Board.PlacedTile(newTile, newTileOrientation, null); // The point is irrelevant here
        Tuple<Point, Point> points = connectionHashMap.get(directionFromPlacedTile);

        return placedTile.getFeature(points.item1) == newPlacedTile.getFeature(points.item2);

    }

}
