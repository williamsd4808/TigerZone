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
public class ExtentUtilities {

    // Expects: an extent 'baseExtent' that you will be projecting on to
    // Expects: an extent 'projectionExtent' that will be projecting from
    // Returns: a collection of points that exist in both sets

    public static Set<Point> union(Set<Point> baseExtent, Set<Point> projectionExtent) {

        Set<Point> union = new HashSet<>();

        if (baseExtent.size() < projectionExtent.size()) {

            for (Point extentPoint : baseExtent) {

                if (projectionExtent.contains(extentPoint)) {

                    union.add(extentPoint);

                }

            }

        } else {

            for (Point extentPoint : projectionExtent) {

                if (baseExtent.contains(extentPoint)) {

                    union.add(extentPoint);

                }

            }

        }

        return union;

    }

}