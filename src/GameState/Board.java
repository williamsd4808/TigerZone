package GameState;

import Utilities.PointUtilities;
import Utilities.Tuple;

import java.awt.Point;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

    private interface Transform {

        Tuple<Integer, Integer> Transform(int x, int y);

    }

    public static class PlacedTile {

        private static final HashMap<Orientation, Transform> conversionMatrices = new HashMap<Orientation, Transform>() {

            {

                put(Orientation.NORTH, (x, y) -> Tuple.Create(x, y));
                put(Orientation.EAST, (x, y) -> Tuple.Create(4 - y, x));
                put(Orientation.SOUTH, (x, y) -> Tuple.Create(4 - x, 4 - y));
                put(Orientation.WEST, (x, y) -> Tuple.Create(y, 4 - x));

            }

        };

        public final Tile tile;
        public final Orientation placementOrientation;
        public final Point location;

        public PlacedTile(Tile tile, Orientation placementOrientation, Point location) {

            this.tile = tile;
            this.placementOrientation = placementOrientation;
            this.location = location;

        }

        public Feature getFeature(int x, int y) {

            Transform transformationMatrix = conversionMatrices.get(placementOrientation);
            Tuple<Integer, Integer> newValues = transformationMatrix.Transform(x, y);

            return tile.getFeature(newValues.item1, newValues.item2);

        }

        public Feature getFeature(Point point) {

            return getFeature(point.x, point.y);

        }

        public String toString() {

            StringBuilder builder = new StringBuilder(tile.toString() + ", Point = " + location + ", Orientation: " + placementOrientation);

            builder.append("\n");

            for (int i = 0; i < 5; i++) {

                builder.append("\t");

                for (int j = 0; j < 5; j++) {

                    builder.append(getFeature(i, j) + (j == 4 ? "" : ", "));

                }

                builder.append("\n");

            }

            return builder.toString();

        }

    }

    private HashMap<Point, PlacedTile> board = new HashMap<>();

    /*
     * The board ensures that tiles are only added to the map on spots that aren't already occupied,
     * and also ensures that you only add tiles adjacent to already placed tiles.
     *
     */

    public Board() {

        putTileInSet(new Point(0, 0), new Tile("Single bubble city with straight road"), Orientation.NORTH);

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

    public void addTile(Point point, Tile tile, Orientation placementOrientation) {

        boolean hasNeighbor = false;

        if (containsElement(point)) {

            throw new RuntimeException("Cannot add tile where one already exists. Point = (" + point + ")");

        }

        for (Orientation orientation : Orientation.values()) {

            Point orientedPoint = PointUtilities.getPointFromOrientation(point, orientation);

            if (containsElement(orientedPoint)) {

                hasNeighbor = true;
                break;

            }

        }

        if (!hasNeighbor) {

            throw new RuntimeException("Tile does not connect to an already placed tile and therefore cannot be added. Point = (" + point + ")");

        }

        putTileInSet(point, tile, placementOrientation);

    }

    /*
     * This method will throw exceptions in one case:
     *      1. If the point does not exist in the board
     *
     */

    public PlacedTile getTile(Point point) {

        if (!containsElement(point)) {

            throw new RuntimeException("Point (" + point + " does not exist on board");

        }

        return board.get(point);

    }

    /*
     * This method will throw exceptions in one case:
     *      1. If the point does not exist in the board
     *
     */

    public PlacedTile getTileNeighbor(Point point, Orientation orientation) {

        if (!containsElement(point)) {

            throw new RuntimeException("Point (" + point + " does not exist on board");

        }

        Point orientedPoint = PointUtilities.getPointFromOrientation(point, orientation);

        if (!containsElement(orientedPoint)) {

            return null;

        }

        return getTile(orientedPoint);

    }

    public Map<Point, PlacedTile> getTiles() {

        return Collections.unmodifiableMap(board);

    }

    public boolean containsElement(Point point) {

        return board.containsKey(point);

    }

    private void putTileInSet(Point location, Tile tile, Orientation orientation) {

        board.put(location, new PlacedTile(tile, orientation, location));

    }

}