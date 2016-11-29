package ApiEndpoints;

import GameState.*;
import Utilities.*;

import java.awt.*;
import java.util.Set;

import static Utilities.MeepleUtilities.meepleLocations;

public class Start {

    public static void main(String[] args) {

        Engine engine = new Engine();
        engine.newGame(0);
        engine.playerJoin("Austin");
        engine.playerJoin("Austin2");

        Tile tile = engine.drawTile();

        engine.addTileToBoard(new Point(1, 0), tile, Board.Orientation.SOUTH);

        System.out.println(engine.board.getTile(new Point(0, 0)));
        System.out.println(engine.board.getTile(new Point(1, 0)));

        Set<Point> extent = FeatureUtilities.getExtentOfFeature(engine.board, new Point(4, 3), Feature.LAKE);
        System.out.println(extent.size());

        System.out.println(FeatureUtilities.getOpenEdgesInExtent(extent));

        System.out.println("Valid meeple placements on tile (0, 0):");
        for (int i : MeepleUtilities.getValidMeeplePlacements(engine.board, engine.board.getTile(new Point(0, 0)))) {

            System.out.println("\t" + i + " | " + engine.board.getTile(new Point(0, 0)).getFeature((MeepleUtilities.meepleLocations.get(i))));

        }

    }

}