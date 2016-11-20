package GameState;

import Utilities.BoardUtilities;
import Utilities.PointUtilities;
import Utilities.Tuple;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.json.*;

public class Board implements Serializable {

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

    public static class PlacedTile implements Serializable {

        private int meepleLocation = 0;
        private Meeple placedMeeple;

        private static final HashMap<Orientation, Transform> conversionMatrices = new HashMap<Orientation, Transform>() {

            {

                put(Orientation.NORTH, (x, y) -> Tuple.Create(x, y));
                put(Orientation.EAST, (x, y) -> Tuple.Create(4 - y, x));
                put(Orientation.SOUTH, (x, y) -> Tuple.Create(4 - x, 4 - y));
                put(Orientation.WEST, (x, y) -> Tuple.Create(y, 4 - x));

            }

        };

        private static final long serialVersionUID = -1486570415012074966L;

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

        //If meepleLocation = 0, no meeple on the tile.
        public int getMeepleLocation() {

            return meepleLocation;
        }

        //To remove meeple from the tile, call this with meeplePlacement = 0
        //This is used to place meeple
        public void setMeepleLocation(int meeplePlacement, Meeple placedMeeple) {

            meepleLocation = meeplePlacement;
            this.placedMeeple = placedMeeple;

        }

        public Feature getMeepleFeature() {
            if(meepleLocation == 0) {
                //return the "HOLE" feature type?
                System.out.println("No meeple present on tile");
                return null;
            } else {
                
                //return Meeple mapped feature
                switch(meepleLocation) {
                    case 1 :
                        return getFeature(1,1);
                    case 2 :
                        return getFeature(0,2);
                    case 3 :
                        return getFeature(1,3);
                    case 4 :
                        return getFeature(2,0);
                    case 5 :
                        return getFeature(2,2);
                    case 6 :
                        return getFeature(2,4);
                    case 7 :
                        return getFeature(3,1);
                    case 8 :
                        return getFeature(4,2);
                    case 9 :
                        return getFeature(3,3);
                    default :
                        return null;
                }
            }
        }

        public Player getMeepleOwner() {

            return placedMeeple.getOwner();

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

    private static final long serialVersionUID = -6037642475395251125L;

    private HashMap<Point, PlacedTile> board = new HashMap<>();

    /*
     * The board ensures that tiles are only added to the map on spots that aren't already occupied,
     * and also ensures that you only add tiles adjacent to already placed tiles.
     *
     */

    public Board() {

        putTileInMap(new Point(0, 0), new Tile("Single bubble city with straight road"), Orientation.NORTH);

    }

    /*
     * This method will throw exceptions in two cases:
     *      1. if the tile should not be placed on the map due to game rules
     *      2. if item1 tile is trying to be added where one already exists
     *      3. if item1 tile does not connect to item1 tile already on the map,
     *         it will not be added to the map
     *
     * This method guarantees that internally, all neighbors are set properly
     *
     */

    public void addTile(Point point, Tile tile, Orientation placementOrientation) {

        if (!BoardUtilities.canPlaceTileAtLocation(tile, point, placementOrientation, this)) {

            throw new RuntimeException("Cannot add tile there as it is not a valid location for this type of tile");

        }

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

        putTileInMap(point, tile, placementOrientation);

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

    public void fromJSON(JsonObject json) {
      JsonArray tiles = json.getJsonArray("board");

      for (JsonObject rawTile : tiles.getValuesAs(JsonObject.class)) {
        Tile tile = new Tile(rawTile.getString("name"));
        Point point = new Point(rawTile.getInt("x"), rawTile.getInt("y"));

        // Orientation needs to be updated, so for now we just pass in NORTH as a default
        putTileInMap(point, tile, Orientation.NORTH);
      }
    }

    public JsonArray toJSON() {
      JsonArrayBuilder res = Json.createArrayBuilder();

      for(Map.Entry<Point,PlacedTile> entry : board.entrySet()){
        PlacedTile t = entry.getValue();
        res.add(
          Json
            .createObjectBuilder()
            .add("x", t.location.x)
            .add("y", t.location.y)
            .add("orientation", t.placementOrientation.toString())
            .add("name", t.tile.toString())
        );
      }

      return res.build();
    }

    private void putTileInMap(Point location, Tile tile, Orientation orientation) {

        board.put(location, new PlacedTile(tile, orientation, location));

    }

    private void readObject(ObjectInputStream inputStream) throws ClassNotFoundException, IOException {

        inputStream.defaultReadObject();

    }
    private void writeObject(ObjectOutputStream outputStream) throws IOException {

        outputStream.defaultWriteObject();

    }

}
