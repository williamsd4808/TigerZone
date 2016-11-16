package Utilities;

import GameState.Board;
import GameState.Tile;

import java.awt.*;
import java.util.*;

import static Utilities.TileUtilities.getValidOrientationsFromPlacedTile;

/**
 * Created by asebe on 11/10/2016.
 */
public class BoardUtilities {

    // Expects: the board you want
    // Returns: a map with keys as Points, and values as PlacedTile of all edge tiles

    public static Map<Point, Board.PlacedTile> getAllEdgeTiles(Board board) {

        Map<Point, Board.PlacedTile> edgeTiles = new HashMap<>();

        for (Map.Entry<Point, Board.PlacedTile> entry : board.getTiles().entrySet()) {

            Point point = entry.getKey();
            Board.PlacedTile tile = entry.getValue();
            Collection<Board.Orientation> openEdges = TileUtilities.getOpenEdges(tile, board);

            if (!openEdges.isEmpty()) {

                edgeTiles.put(point, tile);

            }


        }

        return Collections.unmodifiableMap(edgeTiles);

    }

    // Expects: the board you want
    // Returns: a collection of all the neighboring points on the board

    public static Collection<Point> getAllAdjacentPoints(Board board) {

        ArrayList<Point> adjacentPoints = new ArrayList<>();

        Map<Point, Board.PlacedTile> edgeTiles = getAllEdgeTiles(board);

        for (Map.Entry<Point, Board.PlacedTile> entry : edgeTiles.entrySet()) {

            Point point = entry.getKey();
            Board.PlacedTile placedTile = entry.getValue();

            Collection<Board.Orientation> openEdges = TileUtilities.getOpenEdges(placedTile, board);

            for (Board.Orientation orientation : openEdges) {

                Point orientedPoint = PointUtilities.getPointFromOrientation(point, orientation);
                adjacentPoints.add(orientedPoint);

            }

        }

        return adjacentPoints;

    }

    // Expects: a tile you intend to place
    // Expects: a point you expect to put it at
    // Expects: the board you wish to perform the operation on
    // Returns a collection of orientations that you may put the tile in that follows the game rules

    public static Collection<Board.Orientation> getValidOrientationsForTileAtLocation(Tile tile, Point point, Board board) {

        HashSet<Board.Orientation> validOrientations = createNewOrientationSet();

        if (board.containsElement(point)) { // If the point has already been taken on the map, simply return nothing

            return new HashSet<>();

        }

        for (Board.Orientation orientation : Board.Orientation.values()) {

            Point orientedPoint = PointUtilities.getPointFromOrientation(point, orientation);

            if (board.containsElement(orientedPoint)) {

                validOrientations.retainAll(getValidOrientationsFromPlacedTile(board.getTile(orientedPoint), tile, orientation.getOpposite(), board));

            }

        }

        return validOrientations;

    }

    // Expects: a tile you intend to place
    // Expects: a point you expect to put it at
    // Expects: an orientation you expect to put it in
    // Expects: the board you wish to perform the operation on
    // Returns a boolean of whether or not you can place that tile in that location at that orientation

    public static boolean canPlaceTileAtLocation(Tile tile, Point point, Board.Orientation placementOrientation, Board board) {

        return getValidOrientationsForTileAtLocation(tile, point, board).contains(placementOrientation);

    }

    // Expects: a tile you intend to place
    // Expects: a board you intend to place it on
    // Returns a map of points and collections of orientations that tile can be validly placed in

    public static Map<Point, Collection<Board.Orientation>> getValidAdjacentPoints(Tile tile, Board board) {

        HashMap<Point, Collection<Board.Orientation>> validAdjacentPoints = new HashMap<>();

        for (Point adjacentPoint : getAllAdjacentPoints(board)) {

            Collection<Board.Orientation> validOrientations = getValidOrientationsForTileAtLocation(tile, adjacentPoint, board);

            if (!validOrientations.isEmpty()) {

                validAdjacentPoints.put(adjacentPoint, validOrientations);

            }

        }

        return validAdjacentPoints;

    }

    private static HashSet<Board.Orientation> createNewOrientationSet() {

        return new HashSet<Board.Orientation>() {

            {

                add(Board.Orientation.NORTH);
                add(Board.Orientation.EAST);
                add(Board.Orientation.SOUTH);
                add(Board.Orientation.WEST);

            }

        };

    }

}
