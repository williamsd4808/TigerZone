package ApiEndpoints;

import GameState.*;
import Utilities.*;

import java.awt.*;
import java.util.Set;

public class Start {

    public static void main(String[] args) {

        Engine engine = new Engine();
        engine.newGame();
        engine.playerJoin("Austin");
        engine.playerJoin("Austin2");

        Tile tile = engine.drawTile();

        Board.PlacedTile placedTile = engine.board.getTile(new Point(0, 0));

        System.out.println(placedTile);
        System.out.println(placedTile.getFeature(2, 1));

        Set<Point> extent = FeatureUtilities.getExtentOfFeature(engine.board, new Point(2, 1), Feature.JUNGLE);
        System.out.println(extent.size());

        Tile myTile = new Tile("JJJJ-");
        engine.board.addTile(new Point(-1,0), myTile, Board.Orientation.SOUTH);
        Board.PlacedTile placedTile2 = engine.board.getTile(new Point(-1,0));

		System.out.println(placedTile2);
        System.out.println(placedTile2.getFeature(2, 0));
        Set<Point> extent2 = FeatureUtilities.getExtentOfFeature(engine.board, new Point(-1, 0), Feature.JUNGLE);
        System.out.println(extent2.size());
        for (Point myPoint : extent2) {
			System.out.println("Point: [" + myPoint.x + ", " + myPoint.y + "]");
        }

    }

}