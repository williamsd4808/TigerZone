import GameState.Board;
import GameState.Deck;
import GameState.Tile;
import PersistenceManagement.LoadBoard;
import PersistenceManagement.SaveBoard;
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

        Board board = LoadBoard.loadFromFile("Test");
        Deck deck = new Deck(1000);

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
