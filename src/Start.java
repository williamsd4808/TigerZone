import GameState.Board;
import GameState.Deck;
import GameState.Tile;
import Utilities.BoardUtilities;

import java.awt.*;
import java.util.Collection;
import java.util.Map;
import GameState.Player;
import GameState.Meeple;

/**
 * Created by Austin Seber2 on 11/8/2016.
 */
public class Start {

    public static void main(String[] args) {

        Deck deck = new Deck(1000);
        Board board = new Board();
        board.addTile(new Point(0, 1), deck.drawTile(), Board.Orientation.EAST);
        board.addTile(new Point(1, 1), deck.drawTile(), Board.Orientation.WEST);

        Tile newTile = deck.drawTile();

        System.out.println(newTile);

        Map<Point, Collection<Board.Orientation>> validPointsAndOrientations = BoardUtilities.getValidAdjacentPoints(newTile, board);

        for (Map.Entry<Point, Collection<Board.Orientation>> entry : validPointsAndOrientations.entrySet()) {

            Point point = entry.getKey();
            Collection<Board.Orientation> orientations = entry.getValue();

            System.out.println(point);

            for (Board.Orientation orientation : orientations) {

                System.out.println("\t" + orientation);

            }

        }

        Player player1 = new Player("ricky");
        Meeple meeple = new Meeple(player1);
        System.out.println(meeple.getOwner());

    }

    public static void printTileTest(Tile temp) {

        for(int i = 0; i < 5; i++) {

            for(int j = 0; j < 5; j++) {

                System.out.print(temp.getFeature(i, j) + " ");

            }

            System.out.println();

        }

    }

}
