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

        Set<Point> extent = FeatureUtilities.getExtentOfFeature(engine.board, new Point(4, 2), Feature.LAKE);
        System.out.println(extent.size());

    }

}