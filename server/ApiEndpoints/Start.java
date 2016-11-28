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
        System.out.println(placedTile.getFeature(2, 4));

        Set<Point> extent = FeatureUtilities.getExtentOfFeature(engine.board, new Point(2, 4), Feature.LAKE);
        System.out.println(extent.size());

    }

}