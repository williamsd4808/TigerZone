package GameState;

public class Tile {

	//2d array 5x5 of tile attributes
	Feature[][] subGrid = new Feature[5][5];
	private String name;
	private final boolean shield = false;
	private final int id = 0;

    public Tile() {

        this.name = "Not set";

    }

	public Tile(String name) {

		this.name = name;

	}

	public String toString() {

		return name;

	}

}