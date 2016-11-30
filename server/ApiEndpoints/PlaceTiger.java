package ApiEndpoints;

import java.awt.*;

import GameState.Engine;

import GameState.Board;
import GameState.Tile;
import GameState.Player;
import GameState.Meeple;


//So far tested, not working. Not called anywhere
public class PlaceTiger {

    public static void main(String[] args) {

        String savedEngine = args[0]; // The name of the saved engine state
        int tileX = Integer.parseInt(args[1]); // The X location of the tile we just placed
        int tileY = Integer.parseInt(args[2]); // The Y location of the tile we just placed
        int meepleLocation = Integer.parseInt(args[3]); //The location of the meeple to be placed.
        String ownerName = args[4]; //Name of the Player who owns the meeple

        BaseApiEndpoint endpoint = new BaseMutableApiEndpoint() {

            protected void doExecute(Engine engine) {

                Point point = new Point(tileX, tileY);
                Player meepleOwner = engine.getPlayer(ownerName);
                Meeple playerMeeple = new Meeple(meepleOwner);

                engine.board.getTile(point).setMeepleLocation(meepleLocation, playerMeeple);

            }

        };

        endpoint.execute(savedEngine);

    }

}