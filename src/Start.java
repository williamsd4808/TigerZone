import GameState.Board;
import GameState.Deck;
import GameState.Tile;
import Utilities.BoardUtilities;
import Utilities.TileUtilities;
import Utilities.Tuple;

import java.awt.*;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Austin Seber2 on 11/8/2016.
 */
public class Start {

    public static void main(String[] args) {

        Deck deck = new Deck(1);

        Board board = new Board();
        board.addTile(new Point(0, 1), deck.drawTile(), Board.Orientation.NORTH);
        board.addTile(new Point(0, 2), deck.drawTile(), Board.Orientation.NORTH);
        board.addTile(new Point(1, 0), deck.drawTile(), Board.Orientation.NORTH);
        board.addTile(new Point(1, 1), deck.drawTile(), Board.Orientation.NORTH);
        board.addTile(new Point(1, 2), deck.drawTile(), Board.Orientation.NORTH);
        board.addTile(new Point(2, 0), deck.drawTile(), Board.Orientation.NORTH);
        board.addTile(new Point(2, 1), deck.drawTile(), Board.Orientation.NORTH);
        board.addTile(new Point(2, 2), deck.drawTile(), Board.Orientation.NORTH);

        Collection<Tuple<Board.Orientation, Board.Orientation>> possibleOrientations = TileUtilities.getPossibleConnections(board.getTile(new Point(0, 0)), deck.drawTile(), board);

        for (Tuple<Board.Orientation, Board.Orientation> possibleOrientation : possibleOrientations) {

            System.out.println(possibleOrientation.item1 + " | " + possibleOrientation.item2);

        }

    }
}
