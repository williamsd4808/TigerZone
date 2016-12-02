package ApiEndpoints;

import java.awt.*;

import GameState.Engine;

import GameState.Board;
import GameState.Tile;

public class PlaceTile {

    public static void main(String[] args) {

        String savedEngine = args[0]; // The name of the saved engine state
        String tileName = args[1]; // The name of the tile we intend to place on the board
        int tileX = Integer.parseInt(args[2]); // The X location of the tile
        int tileY = Integer.parseInt(args[3]); // The Y location of the tile
        int orientationInt = Integer.parseInt(args[4]); // The orientation to place the tile in

        System.out.println("EngineName: " + savedEngine + "\nTileName: " + tileName + "\nPoint: (" + tileX + ", " + tileY + ")\nOrientation: " + orientationInt);

        BaseApiEndpoint endpoint = new BaseMutableApiEndpoint() {

            protected void doExecute(Engine engine) {

                Point point = new Point(tileX, tileY);
                Tile tile = new Tile(tileName);
                Board.Orientation orientation = Board.Orientation.values()[orientationInt];

                try {

                    engine.board.addTile(point, tile, orientation);
                    engine.deck.drawTile();

                } catch (RuntimeException e) {

                    System.out.println(e.getStackTrace());

                }

            }

        };

        endpoint.execute(savedEngine);

    }

}
