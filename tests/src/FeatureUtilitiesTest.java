package src;

import org.junit.Assert;
import GameState.*;
import Utilities.*;
import java.awt.Point;
import java.util.*;

import static org.junit.Assert.*;

public class FeatureUtilitiesTest {

	@org.junit.Test
	public void getNeighborFeaturesTest() {
		Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
		Map<Point, Feature> neighborFeaturesMap = FeatureUtilities.getNeighborFeatures(engine.board, 0, 0);
		assertNotNull(neighborFeaturesMap);
	}

	@org.junit.Test
	public void getNeighborFeaturesOfInvalidLocationTest() {
		Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
		Map<Point, Feature> neighborFeaturesMap = FeatureUtilities.getNeighborFeatures(engine.board, 10, 10);
		assertEquals(0, neighborFeaturesMap.size());
	}

	@org.junit.Test
	public void getExtentOfValidFeatureTest() {
		Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
		Set<Point> featureExtent = FeatureUtilities.getExtentOfFeature(engine.board, new Point(2,1), Feature.TRAIL);
		assertNotNull(featureExtent);
		assertEquals(5, featureExtent.size());
	}

	@org.junit.Test
	public void getExtentOfInvalidFeatureTest() {
		Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
		Set<Point> featureExtent = FeatureUtilities.getExtentOfFeature(engine.board, new Point(3,0), Feature.TRAIL);
		assertNotNull(featureExtent);
		assertEquals(0, featureExtent.size());
	}

	@org.junit.Test
	public void getOpenEdgesInOpenExtentTest() {
		Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
		Set<Point> featureExtent = FeatureUtilities.getExtentOfFeature(engine.board, new Point(2,1), Feature.TRAIL);
		int numOpenEdges = FeatureUtilities.getOpenEdgesInExtent(featureExtent);
		assertEquals(2, numOpenEdges);
	}

	@org.junit.Test
	public void getOpenEdgesInClosedExtentTest() {
		Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
		engine.board.addTile(new Point(1,0), new Tile("TLTJ-"), Board.Orientation.SOUTH);
		Set<Point> featureExtent = FeatureUtilities.getExtentOfFeature(engine.board, new Point(4,2), Feature.LAKE);
		int numOpenEdges = FeatureUtilities.getOpenEdgesInExtent(featureExtent);
		assertEquals(0, numOpenEdges);
	}

	@org.junit.Test(expected = RuntimeException.class)
	public void getCorrespondingConnectionPointOfNonConnectionPointTest() throws Exception {
		Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
		FeatureUtilities.getCorrespondingConnectionPoint(new Point(4,4));
	}

	@org.junit.Test
	public void getCorrespondingConnectionPointOfValidConnectionPointTest() {
		Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
		Point p = FeatureUtilities.getCorrespondingConnectionPoint(new Point(4,2));
		assertEquals(5, p.x);
		assertEquals(2, p.y);
	}

	@org.junit.Test
	public void isValidConnectionPointTest() {
		Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
		boolean connectionPoint = FeatureUtilities.isConnectionPoint(new Point(4,2));
		assertTrue(connectionPoint);
	}

	@org.junit.Test
	public void isInvalidConnectionPointTest() {
		Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
		boolean connectionPoint = FeatureUtilities.isConnectionPoint(new Point(1,2));
		assertFalse(connectionPoint);
	}

	@org.junit.Test
	public void getGlobalTilePointTest() {
		Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
		Point globalPoint = FeatureUtilities.getGlobalTilePoint(new Point(8,3));
		assertEquals(1, globalPoint.x);
		assertEquals(0, globalPoint.y);
	}


	@org.junit.Test
	public void getLocalFeaturePointTest() {
		Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
		Point localFeaturePoint = FeatureUtilities.getLocalFeaturePoint(new Point(14,22));
		assertEquals(4, localFeaturePoint.x);
		assertEquals(2, localFeaturePoint.y);
	}

	@org.junit.Test
	public void getGlobalFeaturePointTest() {
		Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
		engine.board.addTile(new Point(1,0), new Tile("TLTJ-"), Board.Orientation.SOUTH);
		engine.board.addTile(new Point(2,0), new Tile("JJJJ-"), Board.Orientation.NORTH);
		engine.board.addTile(new Point(2,1), new Tile("JJJJ-"), Board.Orientation.NORTH);
		Board.PlacedTile t = engine.board.getTile(new Point(2,1));
		Point globalFeaturePoint = FeatureUtilities.getGlobalFeaturePoint(t, new Point(4,2));
		assertEquals(14, globalFeaturePoint.x);
		assertEquals(7, globalFeaturePoint.y);
	}

	@org.junit.Test
	public void getPlacedTilesInExtentTest() {
		Engine engine = new Engine();
		Random generator = new Random();
		long i = generator.nextLong();
		engine.newGame(i);
		engine.board.addTile(new Point(1,0), new Tile("TLTJ-"), Board.Orientation.SOUTH);
		engine.board.addTile(new Point(2,0), new Tile("JJJJ-"), Board.Orientation.NORTH);
		engine.board.addTile(new Point(2,1), new Tile("JJJJ-"), Board.Orientation.NORTH);
		Board.PlacedTile t = engine.board.getTile(new Point(2,1));
		Point globalFeaturePoint = FeatureUtilities.getGlobalFeaturePoint(t, new Point(4,2));
		Set<Point> extent = FeatureUtilities.getExtentOfFeature(engine.board, globalFeaturePoint, Feature.JUNGLE);
		Collection<Point> tilesInExtent = FeatureUtilities.getPlacedTilesInExtent(engine.board, extent);
		assertEquals(3, tilesInExtent.size());
	}

}