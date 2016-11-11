import GameState.Deck;
import GameState.Tile;

/**
 * Created by Austin Seber2 on 11/8/2016.
 */
public class Start {

    public static void main(String[] args) {

        /*Board board = new Board();
        board.addTile(new Point(1, 0), new Tile());
        board.addTile(new Point(2, 0), new Tile());
//        board.addTile(new Point(4, 0), new Tile());

        Map<Point, Tile> tiles = board.getTiles();

        for (Map.Entry<Point, Tile> entry : tiles.entrySet()) {

            System.out.println("Key: " + entry.getKey() + " | Value: " + entry.getValue());

            System.out.println("\tNorth: " + board.getTileNeighbor(entry.getKey(), Board.Orientation.NORTH));
            System.out.println("\tEast: " + board.getTileNeighbor(entry.getKey(), Board.Orientation.EAST));
            System.out.println("\tSouth: " + board.getTileNeighbor(entry.getKey(), Board.Orientation.SOUTH));
            System.out.println("\tWest: " + board.getTileNeighbor(entry.getKey(), Board.Orientation.WEST));

        }*/

        Deck deck = new Deck(1);

        Tile temp = deck.drawTile();
        printTileTest(temp);

        // while (deck.hasTileToDraw()) {

        //     System.out.println(deck.drawTile());

        // }        
    }
    public static void printTileTest(Tile temp) {
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                System.out.print(temp.subGrid[i][j] + " ");
            }
            System.out.println();
        }
    }
}
