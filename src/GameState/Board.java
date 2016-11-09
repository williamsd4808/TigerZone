package GameState;

import Utilities.PointUtilities;
import Utilities.Tuple;

import java.awt.Point;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Board {

    public enum Orientation {

        NORTH,
        EAST,
        SOUTH,
        WEST;

        public Orientation getOpposite() {

            return Orientation.values()[(ordinal() + 2) % 4];

        }

    }

    private class BoardElement {

        private HashMap<Orientation, Tile> neighbors = new HashMap<>();

        private Tile currentTile;

    }

    private HashMap<Point, BoardElement> board = new HashMap<>();

    /*
     * The board ensures that tiles are only added to the map on spots that aren't already occupied,
     * and also ensures that you only add tiles adjacent to already placed tiles.
     *
     */

    public Board() {

        BoardElement startElement = new BoardElement();
        startElement.currentTile = new Tile();

        board.put(new Point(0, 0), startElement); // add the start tile here

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

        BoardElement element = new BoardElement();
        element.currentTile = tile;

        for (Orientation orientation : Orientation.values()) {

            Point orientedPoint = PointUtilities.getPointFromOrientation(point, orientation);

            if (boardContainsElement(orientedPoint)) {

                hasNeighbor = true;

                BoardElement neighbor = board.get(orientedPoint);
                neighbor.neighbors.put(orientation.getOpposite(), tile);
                element.neighbors.put(orientation, neighbor.currentTile);

            }

        }

        if (!hasNeighbor) {

            throw new RuntimeException("Tile does not connect to an already placed tile and therefore cannot be added. Point = (" + point + ")");

        }

        board.put(point, element);

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

        return getBoardElement(point).currentTile;

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

        return getBoardElement(point).neighbors.get(orientation);

    }

    public HashMap<Point, Tile> getTiles() {

        HashMap<Point, Tile> tiles = new HashMap<>();

        Queue<Tuple<Point, BoardElement>> elements = new LinkedBlockingQueue<>();
        elements.add(Tuple.Create(new Point(0, 0), getBoardElement(new Point(0, 0))));

        while (!elements.isEmpty()) {

            Tuple<Point, BoardElement> tuple = elements.poll();
            Point point = tuple.item1;
            BoardElement element = tuple.item2;

           for (Orientation orientation : Orientation.values()) {

               Point orientedPoint = PointUtilities.getPointFromOrientation(point, orientation);
               BoardElement neighbor = getBoardElement(orientedPoint);

               if (neighbor != null && !tiles.containsKey(orientedPoint)) {

                   elements.add(Tuple.Create(orientedPoint, neighbor));

               }

           }

           tiles.put(point, element.currentTile);

        }

        return tiles;

    }

    private boolean boardContainsElement(Point point) {

        return board.containsKey(point);

    }

    private BoardElement getBoardElement(Point point) {

        return board.get(point);

    }

}