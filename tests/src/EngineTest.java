package src;

import org.junit.Assert;
import GameState.*;
import java.awt.Point;
import java.util.*;

import static org.junit.Assert.*;

//Remember to change batch/shell script to include new files created

public class EngineTest {

    @org.junit.Test
    public void newGameBoardTest() {
    	Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
    	assertNotNull(engine.board);
    }

    @org.junit.Test
    public void newGameDeckTest() {
    	Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
    	assertNotNull(engine.deck);
    }

    @org.junit.Test
    public void newGamePlayersTest() {
    	Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
    	assertNotNull(engine.players);
    }

    @org.junit.Test
    public void addTileTurnTest() {
    	Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
		int previousTurn = engine.turn;
		engine.addTileToBoard(new Point(-1,0), new Tile("JJJJ-"), Board.Orientation.WEST);
		assertNotNull(previousTurn < engine.turn);
    }

}