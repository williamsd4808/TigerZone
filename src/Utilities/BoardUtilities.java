package Utilities;

import GameState.Board;
import GameState.Tile;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.awt.*;
import java.util.*;

import static Utilities.TileUtilities.getPossibleConnections;

/**
 * Created by asebe on 11/10/2016.
 */
public class BoardUtilities {

    public static Map<Point, Board.PlacedTile> getEdgeTiles(Board board) {

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

    public static Collection<Board.Orientation> getOrientationsForTileAtLocation(Tile tile, Point point, Board board) {

        HashSet<Board.Orientation> validOrientations = new HashSet<>();
        boolean firstHit = false;

        if (board.containsElement(point)) {

            return validOrientations;

        }

        for (Board.Orientation orientation : Board.Orientation.values()) {

            Point orientedPoint = PointUtilities.getPointFromOrientation(point, orientation);

            if (board.containsElement(orientedPoint)) {

                getPossibleConnections(board.getTile(orientedPoint), tile, board);

            }

        }

        return true;

    }

    public static boolean canPlaceTileAtLocation(Tile tile, Point point, Board.Orientation placementOrientation, Board board) {

        if (board.containsElement(point)) {

            return false;

        }

        boolean firstHit = false;

        for (Board.Orientation orientation : Board.Orientation.values()) {

            Point orientedPoint = PointUtilities.getPointFromOrientation(point, orientation);

            if (board.containsElement(orientedPoint)) {

                getPossibleConnections(board.getTile(orientedPoint), tile, board);

            }

        }

        return true;

    }

    // Provide method to get all open spots for a new tile

}
