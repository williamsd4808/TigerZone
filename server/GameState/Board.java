package GameState;

import Utilities.BoardUtilities;
import Utilities.PointUtilities;
import Utilities.FeatureUtilities;
import Utilities.Tuple;

import java.awt.Point;
import java.util.*;

import javax.json.*;

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

    public interface Transform {

        Point Transform(Point point);

    }

    public static class PlacedTile {

        private int meepleLocation = 0;
        private Meeple placedMeeple;
        private int numMeeples;

        private static final HashMap<Orientation, Transform> conversionMatrices = new HashMap<Orientation, Transform>() {

            {

                put(Orientation.NORTH, (point) -> new Point(point.x, point.y));
                put(Orientation.EAST, (point) -> new Point(4 - point.y, point.x));
                put(Orientation.SOUTH, (point) -> new Point(4 - point.x, 4 - point.y));
                put(Orientation.WEST, (point) -> new Point(point.y, 4 - point.x));

            }

        };

        public final Tile tile;
        public final Orientation placementOrientation;
        public final Point location;

        public PlacedTile(Tile tile, Orientation placementOrientation, Point location) {

            this.tile = tile;
            this.placementOrientation = placementOrientation;
            this.location = location;
            numMeeples = 0;

        }

        public Feature getFeature(int x, int y) {

            return getFeature(new Point(x, y));

        }

        public Meeple getMeeple() {

            return placedMeeple;

        }

        public Feature getFeature(Point point) {

            Point transformedPoint = conversionMatrices.get(placementOrientation).Transform(point);
            return tile.getFeature(transformedPoint);

        }

        //If meepleLocation = 0, no meeple on the tile.
        public int getMeepleLocation() {

            return meepleLocation;
        }

        public int getNumMeeples() {

            return numMeeples;
        }

        //This should be called only when a player has drawn a tile that cannot be placed and
        //has decided to place a meeple on top of another meeple of theirs.
        public void incrementMeeple() {
            numMeeples++;
        }

        //This exists to take care of the rare case of an unplayable tile being drawn
        //and the player chooses to pick up a meeple that was preciously placed on top
        //of another meeple.
        public void decrementMeeple() {
            numMeeples--;
        }

        //To remove all meeples from the tile, call this with meeplePlacement = 0
        //This is used to place the first meeple on a tile
        //Do not use this to place a meeple on top of another meeple
        public void setMeepleLocation(int meeplePlacement, Meeple placedMeeple) {

            if(meeplePlacement != 0) {
                numMeeples = 1;
            } else {
                numMeeples = 0;
            }

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
                        throw new RuntimeException("Error: cannot place meeple in location outside of index [0,9]");
                }
            }
        }

        public Player getMeepleOwner() {

            return placedMeeple.getOwner();

        }

        public String toString() {

            StringBuilder builder = new StringBuilder(tile.toString() + ", Point = " + location + ", Orientation: " + placementOrientation);

            builder.append("\n");

            for (int y = 0; y < 5; y++) {

                builder.append("\t");

                for (int x = 0; x < 5; x++) {

                    builder.append(getFeature(x, y) + (x == 4 ? "" : ", "));

                }

                builder.append("\n");

            }

            return builder.toString();

        }

    }

    private HashMap<Point, PlacedTile> board = new HashMap<>();
    private Engine engine;

    /*
     * The board ensures that tiles are only added to the map on spots that aren't already occupied,
     * and also ensures that you only add tiles adjacent to already placed tiles.
     *
     */

    public Board(Engine engine) {

        putTileInMap(new Point(0, 0), new Tile("TLTJ-"), Orientation.NORTH);
        this.engine = engine;

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



    private void putTileInMap(Point location, Tile tile, Orientation orientation) {

        PlacedTile newPlacedTile = new PlacedTile(tile, orientation, location);

        board.put(location, newPlacedTile); // Add the tile to the board

        // Then we will flood fill and adjust scores accordingly!

        ArrayList<Point> globalFeaturePointsInNewTile = new ArrayList<>();

        for (int i = 0; i < 5; i++) {

            for (int j = 0; j < 5; j++) {

                globalFeaturePointsInNewTile.add(FeatureUtilities.getGlobalFeaturePoint(newPlacedTile, new Point(i, j)));

            }

        }

        while (!globalFeaturePointsInNewTile.isEmpty()) {

            Point removedPoint = globalFeaturePointsInNewTile.get(0);
            globalFeaturePointsInNewTile.remove(0);

            Point removedLocalFeaturePoint = FeatureUtilities.getLocalFeaturePoint(removedPoint);

            Feature removedFeature = getTile(location).getFeature(removedLocalFeaturePoint);

            Set<Point> featureExtent = FeatureUtilities.getExtentOfFeature(this, removedPoint, removedFeature);

            for (Point featureExtentPoint : featureExtent) {

                globalFeaturePointsInNewTile.remove(featureExtentPoint); // Attempt to remove elements of this extent from the points in the new tile as they have already been accounted for

            }

            // Score here

        }

    }

    public static Board fromJson(Engine engine, JsonObject json) {

        Board board = new Board(engine);

        JsonArray tiles = json.getJsonArray("tiles");

        for (JsonObject rawTile : tiles.getValuesAs(JsonObject.class)) {

            Tile tile = new Tile(rawTile.getString("name"));
            Point point = new Point(rawTile.getInt("x"), rawTile.getInt("y"));
            Orientation orientation = Orientation.values()[rawTile.getInt("orientation")];

            board.putTileInMap(point, tile, orientation);

        }

        return board;

    }

    public static JsonObject toJson(Board board) {
        JsonArrayBuilder res = Json.createArrayBuilder();

        for(Map.Entry<Point,PlacedTile> entry : board.board.entrySet()){
            PlacedTile t = entry.getValue();
            res.add(
                    Json
                            .createObjectBuilder()
                            .add("x", t.location.x)
                            .add("y", t.location.y)
                            .add("orientation", t.placementOrientation.ordinal())
                            .add("name", t.tile.getName())
            );
        }

        return Json.createObjectBuilder().add("tiles", res.build()).build();
    }

}
