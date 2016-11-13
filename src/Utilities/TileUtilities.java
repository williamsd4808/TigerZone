package Utilities;

import GameState.Board;
import GameState.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by asebe on 11/10/2016.
 */
public class TileUtilities {

    private static final HashMap<Board.Orientation, Tuple<Point, Point>> connectionHashMap = new HashMap<Board.Orientation, Tuple<Point, Point>>() {

        {

            put(Board.Orientation.NORTH, Tuple.Create(new Point(2, 0), new Point(2, 4)));
            put(Board.Orientation.EAST, Tuple.Create(new Point(4, 2), new Point(0, 2)));
            put(Board.Orientation.SOUTH, Tuple.Create(new Point(2, 4), new Point(2, 0)));
            put(Board.Orientation.WEST, Tuple.Create(new Point(0, 2), new Point(4, 2)));

        }

    };

    public static Collection<Board.Orientation> getOpenEdges(Board.PlacedTile placedTile, Board board) {

        ArrayList<Board.Orientation> openEdges = new ArrayList<>();

        for (Board.Orientation orientation : Board.Orientation.values()) {

            Point orientedPoint = PointUtilities.getPointFromOrientation(placedTile.location, orientation);

            if (!board.containsElement(orientedPoint)) {

                openEdges.add(orientation);

            }

        }

        return openEdges;

    }

    public static Collection<Tuple<Board.Orientation, Board.Orientation>> getPossibleConnections(Board.PlacedTile placedTile, Tile newTile, Board board) {

        ArrayList<Tuple<Board.Orientation, Board.Orientation>> validOrientations = new ArrayList<>();

        for (Board.Orientation openEdge : getOpenEdges(placedTile, board)) {

            for (Board.Orientation orientation : Board.Orientation.values()) {

                // bug exists for tile placements where it connects to two tiles at once, we don't account for it

                if (canConnect(placedTile, newTile, openEdge, orientation)) {

                    validOrientations.add(Tuple.Create(openEdge, orientation));

                }

            }

        }

        return validOrientations;

    }

    public static boolean canConnect(Board.PlacedTile placedTile, Tile newTile, Board.Orientation directionFromPlacedTile, Board.Orientation newTileOrientation) {

        Board.PlacedTile newPlacedTile = new Board.PlacedTile(newTile, newTileOrientation, new Point(0, 0));

        Tuple<Point, Point> points = connectionHashMap.get(directionFromPlacedTile);

        if (placedTile.getFeature(points.item1) == newPlacedTile.getFeature(points.item2)) {

            return true;

        }

        return false;

    }

}
