package GameState;

class OutofMeeplesException extends Exception {
	public OutofMeeplesException(String msg) {
		super(msg);
	}
}

public class Player {
	private final String name;
	private int score;
	private int numOfMeeples;

	public Player(String name) {
		this.name = name;
		this.score = 0;
		this.numOfMeeples = 7;
	}

	public String getName() {
		return this.name;
	}

	public int getScore() {
		return this.score;
	}

	public void setScore() {
		this.score = score;
	}

	public void placeMeeple() throws OutofMeeplesException {
		if(numOfMeeples == 0) {
			throw new OutofMeeplesException("no more meeples!");
		}
		else {
			numOfMeeples--;
		}
	}

	public String toString() {
		return this.name;
	}	

}