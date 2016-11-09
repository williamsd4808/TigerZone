package Utilities;

import GameState.Board;

import java.awt.*;

/**
 * Created by Austin Seber2 on 11/9/2016.
 */
public class PointUtilities {

    private static final Point[] orientationTransformations = {new Point (0, 1), new Point(1, 0), new Point(0, -1), new Point (-1, 0)};

    public static Point getPointFromOrientation(Point point, Board.Orientation orientation) {

        Point orientationTransform = orientationTransformations[orientation.ordinal()];

        return new Point(point.x + orientationTransform.x, point.y + orientationTransform.y);

    }

}
