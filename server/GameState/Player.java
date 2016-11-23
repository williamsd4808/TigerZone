package GameState;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

class OutofMeeplesException extends Exception {
	public OutofMeeplesException(String msg) {
		super(msg);
	}
}

public class Player {
	private String name;
	private int score;
	private int numOfMeeples;

    private Player() {
        this.name = "";
        this.score = 0;
        this.numOfMeeples = 7;
    }

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

	public int getNumOfMeeples() {
		return this.numOfMeeples;
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

    public static Player fromJson(JsonObject json) {

        Player player = new Player();

        player.name = json.getString("name");
        player.score = json.getInt("score");
        player.numOfMeeples = json.getInt("numOfMeeples");

        return player;

    }

    public static JsonObject toJson(Player player) {

        JsonObjectBuilder res = Json.createObjectBuilder();

        res
                .add("name", player.name)
                .add("score", player.score)
                .add("numOfMeeples", player.numOfMeeples);


        return res.build();
    }

}