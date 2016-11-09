import GameState.Board;
import GameState.Tile;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Austin Seber2 on 11/8/2016.
 */
public class Start {

    public static void main(String[] args) {

        Board board = new Board();
        board.addTile(new Point(1, 0), new Tile());

        HashMap<Point, Tile> tiles = board.getTiles();

        for (Map.Entry<Point, Tile> entry : tiles.entrySet()) {

            System.out.println("Key: " + entry.getKey() + " | Value: " + entry.getValue());

        }

    }

}
