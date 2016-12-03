package src;

import org.junit.Assert;
import GameState.*;
import java.awt.Point;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by Austin Seber2 on 11/9/2016.
 */
public class BoardTest {

    @org.junit.Test(expected = RuntimeException.class)
    public void addTileOverExistingTile() throws Exception {

    	Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
    	engine.board.addTile(new Point(0, 0), new Tile("JJJJ-"), Board.Orientation.NORTH);
    }

    @org.junit.Test(expected = RuntimeException.class)
    public void getTile() throws Exception {
    	Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
    	engine.board.getTile(new Point(1, 1));
    }

    @org.junit.Test(expected = RuntimeException.class)
    public void getTileNeighbor() throws Exception {
    	Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
    	engine.board.getTileNeighbor(new Point(4, 4), Board.Orientation.NORTH);

    }

    @org.junit.Test
    public void getMeepleFeatureOfInvalidMeepleLocation() throws Exception {
    	Player p = new Player("Dan");
    	Meeple m = new Meeple(p);
    	Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
    	engine.board.getTile(new Point (0,0)).setMeepleLocation(12, m);
    }

    @org.junit.Test
    public void decrementMeepleCountTest() {
    	Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
		Player p = new Player("Dan");
    	Meeple m = new Meeple(p);
    	Board.PlacedTile t = engine.board.getTile(new Point (0, 0));
		t.setMeepleLocation(1, m);
		t.decrementMeeple();
		assertTrue( 0 == t.getNumMeeples());
    }

    @org.junit.Test
    public void incrementMeepleCountTest() {
    	Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
		Player p = new Player("Dan");
    	Meeple m = new Meeple(p);
    	Board.PlacedTile t = engine.board.getTile(new Point (0, 0));
		t.setMeepleLocation(1, m);
		t.incrementMeeple();
		assertTrue(t.getNumMeeples() == 2);
    }

/*
    @org.junit.Test
    public void 
  */  
}