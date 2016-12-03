package src;

import org.junit.Assert;
import GameState.*;
import Utilities.*;
import java.awt.Point;
import java.util.*;

import static org.junit.Assert.*;
import static Utilities.TileUtilities.getValidOrientationsFromPlacedTile;

public class BoardUtilitiesTest {

	@org.junit.Test
	public void getAllEdgeTilesTest() {
		Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
		Map<Point, Board.PlacedTile> edgeTiles = BoardUtilities.getAllEdgeTiles(engine.board);
		assertNotNull(edgeTiles);
	}

	@org.junit.Test
	public void getAllAdjacentPointsTest() {
		Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
		Collection<Point> adjacentPoints = BoardUtilities.getAllAdjacentPoints(engine.board);
		assertNotNull(adjacentPoints);
	}

	@org.junit.Test
	public void getValidOrientationsOfOccupiedLocationTest() {
		Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
		Collection<Board.Orientation> orientations = BoardUtilities.getValidOrientationsForTileAtLocation(new Tile("JJJJ-"), new Point (0,0), engine.board);
		assertEquals(0, orientations.size());	
	}

	@org.junit.Test
	public void getValidOrientationsOfValidLocationTest() {
		Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
		Collection<Board.Orientation> orientations = BoardUtilities.getValidOrientationsForTileAtLocation(new Tile("JJJJ-"), new Point (-1,0), engine.board);
		assertTrue(0 < orientations.size());	
	}

	@org.junit.Test
	public void getMapOfAdjacentPointsTest() {
		Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
		Map<Point, Collection<Board.Orientation>> map = BoardUtilities.getValidAdjacentPoints(new Tile("JJJJ-"), engine.board);
		assertTrue(0 < map.size());	
	}
}