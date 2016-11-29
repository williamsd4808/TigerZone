package Utilities;

import GameState.Meeple;
import GameState.Board;
import GameState.Tile;

import java.awt.*;
import java.util.*;

import static Utilities.TileUtilities.getValidOrientationsFromPlacedTile;

/**
 * Created by asebe on 11/10/2016.
 */
public class MeepleUtilities {

    public static final Map<Integer, Point> meepleLocations = new HashMap<Integer, Point>() {

        {

            put(1, new Point(1,1));
            put(2, new Point(0,2));
            put(3, new Point(1,3));
            put(4, new Point(2,0));
            put(5, new Point(2,2));
            put(6, new Point(2,4));
            put(7, new Point(3,1));
            put(8, new Point(4,2));
            put(9, new Point(3,3));

        }

    };

    private static final Map<Point, Integer> reverseMeepleLocations = new HashMap<Point, Integer>() {

        {

            put(new Point(1,1), 1);
            put(new Point(0,2), 2);
            put(new Point(1,3), 3);
            put(new Point(2,0), 4);
            put(new Point(2,2), 5);
            put(new Point(2,4), 6);
            put(new Point(3,1), 7);
            put(new Point(4,2), 8);
            put(new Point(3,3), 9);

        }

    };

    public static ArrayList<Integer> getValidMeeplePlacements(Board board, Board.PlacedTile placedTile) {

        ArrayList<Integer> validMeepleLocations = new ArrayList<>();

        ArrayList<Set<Point>> placedTileExtents = TileUtilities.getExtentsForTile(board, placedTile);
        Set<Point> placedTileExtent = new HashSet<>();
        Set<Point> possibleMeepleExtent = new HashSet<>();

        for (int i = 0; i < 5; i++) {

            for (int j = 0; j < 5; j++) {

                placedTileExtent.add(FeatureUtilities.getGlobalFeaturePoint(placedTile, new Point(i, j)));

            }

        }

        for (int i = 1; i < 10; i++) {

            possibleMeepleExtent.add(FeatureUtilities.getGlobalFeaturePoint(placedTile, meepleLocations.get(i)));

        }

        for (int i = 0; i < placedTileExtents.size(); i++) {

            Set<Point> extent = placedTileExtents.get(i);

            if (getMeeplesInExtent(board, extent).size() == 0) {

                Set<Point> newExtent = ExtentUtilities.union(extent, placedTileExtent);
                newExtent = ExtentUtilities.union(newExtent, possibleMeepleExtent);

                int lowestIndex = Integer.MAX_VALUE;

                for (Point extentPoint : newExtent) {

                    int newIndex = reverseMeepleLocations.get(extentPoint);

                    if (newIndex < lowestIndex) {

                        lowestIndex = newIndex;

                    }

                }

                if (lowestIndex != Integer.MAX_VALUE) {

                    validMeepleLocations.add(lowestIndex);

                }

            }

        }

        return validMeepleLocations;

    }

    public static ArrayList<Meeple> getMeeplesInExtent(Board board, Set<Point> extent) {

        Collection<Point> tilesInExtent = FeatureUtilities.getPlacedTilesInExtent(board, extent);
        ArrayList<Meeple> meeplesInExtent = new ArrayList<>();

        for (Point placedTileLocation : tilesInExtent) {

            Board.PlacedTile placedTile = board.getTile(placedTileLocation);

            for (int i = 0; i < placedTile.getNumMeeples(); i++) {

                meeplesInExtent.add(placedTile.getMeeple());

            }

        }

        return meeplesInExtent;

    }

}
