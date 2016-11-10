package GameState;

import Utilities.PointUtilities;
import Utilities.Tuple;

import java.awt.Point;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Board {

    /*
     * Do NOT modify this enumeration. The order of this enumeration is connascent with the PointUtilities class implementation
     *
     */

    public enum Orientation {

        NORTH,
        EAST,
        SOUTH,
        WEST;

        public Orientation getOpposite() {

            return Orientation.values()[(ordinal() + 2) % 4];

        }

    }

    private HashMap<Point, Tile> board = new HashMap<>();

    /*
     * The board ensures that tiles are only added to the map on spots that aren't already occupied,
     * and also ensures that you only add tiles adjacent to already placed tiles.
     *
     */

    public Board() {

        board.put(new Point(0, 0), new Tile()); // add the start tile here

    }

    /*
     * This method will throw exceptions in two cases:
     *      1. if item1 tile is trying to be added where one already exists
     *      2. if item1 tile does not connect to item1 tile already on the map,
     *         it will not be added to the map
     *
     * This method guarantees that internally, all neighbors are set properly
     *
     */

    public void addTile(Point point, Tile tile) {

        boolean hasNeighbor = false;

        if (boardContainsElement(point)) {

            throw new RuntimeException("Cannot add tile where one already exists. Point = (" + point + ")");

        }

        for (Orientation orientation : Orientation.values()) {

            Point orientedPoint = PointUtilities.getPointFromOrientation(point, orientation);

            if (boardContainsElement(orientedPoint)) {

                hasNeighbor = true;
                break;

            }

        }

        if (!hasNeighbor) {

            throw new RuntimeException("Tile does not connect to an already placed tile and therefore cannot be added. Point = (" + point + ")");

        }

        board.put(point, tile);

    }

    /*
     * This method will throw exceptions in one case:
     *      1. If the point does not exist in the board
     *
     */

    public Tile getTile(Point point) {

        if (!boardContainsElement(point)) {

            throw new RuntimeException("Point (" + point + " does not exist on board");

        }

        return board.get(point);

    }

    /*
     * This method will throw exceptions in one case:
     *      1. If the point does not exist in the board
     *
     */

    public Tile getTileNeighbor(Point point, Orientation orientation) {

        if (!boardContainsElement(point)) {

            throw new RuntimeException("Point (" + point + " does not exist on board");

        }

        Point orientedPoint = PointUtilities.getPointFromOrientation(point, orientation);

        if (!boardContainsElement(orientedPoint)) {

            return null;

        }

        return getTile(orientedPoint);

    }

    public Map<Point, Tile> getTiles() {

        return Collections.unmodifiableMap(board);

    }

    private boolean boardContainsElement(Point point) {

        return board.containsKey(point);

    }

}