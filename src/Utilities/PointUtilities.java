package Utilities;

import GameState.Board;

import java.awt.*;

/**
 * Created by Austin Seber2 on 11/9/2016.
 */
public class PointUtilities {

    public static Point getPointFromOrientation(Point point, Board.Orientation orientation) {

        if (orientation == Board.Orientation.NORTH) {

            return new Point(point.x, point.y + 1);

        } else if (orientation == Board.Orientation.EAST) {

            return new Point(point.x + 1, point.y);

        } else if (orientation == Board.Orientation.SOUTH) {

            return new Point(point.x, point.y - 1);

        } else { // == WEST

            return new Point(point.x - 1, point.y);

        }

    }

}
