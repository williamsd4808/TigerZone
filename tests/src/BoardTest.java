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
    public void getInexistentTileNeighborTest() throws Exception {
    	Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
    	engine.board.getTileNeighbor(new Point(4, 4), Board.Orientation.NORTH);

    }

    @org.junit.Test
    public void getValidFeatureTest() {
    	Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
		Board.PlacedTile t = engine.board.getTile(new Point(0,0));
		Feature f = t.getFeature(new Point(2,4));
		Feature f2 = Feature.TRAIL;
		assertEquals(f2, f);
    }

    @org.junit.Test
    public void getTileInexistentNeighborTest() {
    	Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
		Board.PlacedTile t = engine.board.getTileNeighbor(new Point(0, 0), Board.Orientation.NORTH);
		assertNull(t);
    }

    @org.junit.Test
    public void getTileNeighborThatExistsTest() {
    	Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
		Tile t = new Tile("JJJJ-");
		engine.board.addTile(new Point(-1, 0), t, Board.Orientation.NORTH);
		Board.PlacedTile t2 = engine.board.getTileNeighbor(new Point(0, 0), Board.Orientation.WEST);
		assertNotNull(t2);
    }

    @org.junit.Test(expected = RuntimeException.class)
    public void setInvalidMeepleLocation() throws Exception {
    	Player p = new Player("Dan");
    	Meeple m = new Meeple(p);
    	Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
    	engine.board.getTile(new Point (0,0)).setMeepleLocation(12, m);
    }

    @org.junit.Test
    public void setValidMeepleLocation() {
    	Player p = new Player("Dan");
    	Meeple m = new Meeple(p);
    	Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
		Board.PlacedTile t = engine.board.getTile(new Point (0,0));
		t.setMeepleLocation(1, m);
		assertEquals(1, engine.board.getTile(new Point (0,0)).getMeepleLocation());
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
		assertTrue(2 == t.getNumMeeples());
    }

    @org.junit.Test(expected = RuntimeException.class)
    public void getInexistentTileTest() throws Exception{
    	Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
		engine.board.getTile(new Point(1, 1));		
    }

    @org.junit.Test
    public void getExistentTileTest() {
    	Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
		Board.PlacedTile t = engine.board.getTile(new Point(0, 0));
		assertNotNull(t);
    }

    //Add a tile where its features conflict with connectiveness
    //for features of neighboring tiles
    @org.junit.Test(expected = RuntimeException.class)
    public void addTileInvalidConnectionTest() {
    	Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
		Tile t = new Tile("JJJJ-");
		engine.board.addTile(new Point(1, 0), t, Board.Orientation.NORTH);
    }

    //Add a tile at a location such that it has no neighbors
    @org.junit.Test(expected = RuntimeException.class)
    public void addTileNotConnectedTest() {
    	Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
		Tile t = new Tile("JJJJ-");
		engine.board.addTile(new Point(1, 1), t, Board.Orientation.NORTH);
    }

    @org.junit.Test
    public void getTilesTest() {
    	Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
		Map<Point, Board.PlacedTile> map = engine.board.getTiles();
		assertNotNull(map);
    }

}