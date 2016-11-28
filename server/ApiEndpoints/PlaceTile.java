package ApiEndpoints;

import java.awt.*;

import GameState.Engine;

import GameState.Board;
import GameState.Tile;
import GameState.Player;
import GameState.Meeple;

public class PlaceTile {

    public static void main(String[] args) {

        String savedEngine = args[0]; // The name of the saved engine state
        String tileName = args[1]; // The name of the tile we intend to place on the board
        int tileX = Integer.parseInt(args[2]); // The X location of the tile
        int tileY = Integer.parseInt(args[3]); // The Y location of the tile
        int orientationInt = Integer.parseInt(args[4]); // The orientation to place the tile in
        int meepleLocation = Integer.parseInt(args[5]); //The location of the meeple to be placed. Call with 0 as arg[5] for no meeple placement
        Player meepleOwner = new Player(args[6]);
        Meeple playerMeeple = new Meeple(meepleOwner);

        BaseApiEndpoint endpoint = new BaseMutableApiEndpoint() {

            protected void doExecute(Engine engine) {

                Point point = new Point(tileX, tileY);
                Tile tile = new Tile(tileName);
                Board.Orientation orientation = Board.Orientation.values()[orientationInt];

                engine.board.addTile(point, tile, orientation);
                engine.board.getTile(point).setMeepleLocation(meepleLocation, playerMeeple);
                engine.deck.drawTile();

            }

        };

        endpoint.execute(savedEngine);

    }

}
