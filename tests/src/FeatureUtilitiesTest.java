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

}