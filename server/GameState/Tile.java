package GameState;

import java.awt.*;
import java.io.*;

public class Tile {

    //2d array 5x5 of tile attributes
	private Feature[][] subGrid;
	private String name;
	// private boolean shield = false;
    // croc is placed on tile that has a shore or game trail as long as tile tile doesn't have one already
    private boolean croc = false;
    //unique animals
    private boolean buffalo = false;
    private boolean boar = false;
    private boolean deer = false;
	private final int id = 0;

	public Tile(String name) {
		this.name = name;
		this.subGrid = initializeSubGrid(name);

	}

	public Feature getFeature(int x, int y) {

		return subGrid[x][y];

	}

	public Feature getFeature(Point point) {

		return getFeature(point.x, point.y);

	}

	public String toString() {

        return getName();

	}

	public String getName() {

        return name;

    }

	private Feature[][] initializeSubGrid(String name) {

        switch(name) {
            case "JJJJ-":
                return new Feature[][] {
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE}
                };
            case "JJJJX":
                return new Feature[][] {
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.DEN, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE}
                };
            case "JJTJX":
                return new Feature[][] {
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.DEN, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.JUNGLE}
                };
            case "TTTT-":
                return new Feature[][] {
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.TRAIL, Feature.TRAIL, Feature.ENDPOINT, Feature.TRAIL, Feature.TRAIL},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.JUNGLE}
                };
            case "TJTJ-":
                return new Feature[][] {
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.JUNGLE}
                };
            case "TJJT-":
                return new Feature[][] {
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.TRAIL, Feature.TRAIL, Feature.TRAIL, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE}
                };
            case "TJTT-":
                return new Feature[][] {
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.TRAIL, Feature.TRAIL, Feature.ENDPOINT, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.JUNGLE}
                };
            case "LLLL-":
                return new Feature[][] {
                        new Feature[] {Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE},
                        new Feature[] {Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE},
                        new Feature[] {Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE},
                        new Feature[] {Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE},
                        new Feature[] {Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE}
                };
            case "JLLL-":
                return new Feature[][] {
                        new Feature[] {Feature.UNKNOWN, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.UNKNOWN},
                        new Feature[] {Feature.LAKE, Feature.LAKE, Feature.JUNGLE, Feature.LAKE, Feature.LAKE},
                        new Feature[] {Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE},
                        new Feature[] {Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE},
                        new Feature[] {Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE}
                };
            case "LLJJ-":
                return new Feature[][] {
                        new Feature[] {Feature.UNKNOWN, Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE},
                        new Feature[] {Feature.JUNGLE, Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.LAKE, Feature.LAKE, Feature.LAKE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.LAKE, Feature.LAKE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.UNKNOWN}
                };
            case "JLJL-":
                return new Feature[][] {
                        new Feature[] {Feature.UNKNOWN, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.UNKNOWN},
                        new Feature[] {Feature.LAKE, Feature.LAKE, Feature.JUNGLE, Feature.LAKE, Feature.LAKE},
                        new Feature[] {Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE},
                        new Feature[] {Feature.LAKE, Feature.LAKE, Feature.JUNGLE, Feature.LAKE, Feature.LAKE},
                        new Feature[] {Feature.UNKNOWN, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.UNKNOWN}
                };
            case "LJLJ-":
                return new Feature[][] {
                        new Feature[] {Feature.UNKNOWN, Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.UNKNOWN},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.LAKE, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.LAKE, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.UNKNOWN, Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.UNKNOWN}
                };
            case "LJJJ-":
                return new Feature[][] {
                        new Feature[] {Feature.UNKNOWN, Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.UNKNOWN},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.LAKE, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE}
                };
            case "JLLJ-":
                return new Feature[][] {
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.UNKNOWN},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.LAKE, Feature.LAKE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.LAKE, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.UNKNOWN, Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.UNKNOWN}
                };
            case "TLJT-":
                return new Feature[][] {
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.UNKNOWN},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.TRAIL, Feature.TRAIL, Feature.TRAIL, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.UNKNOWN}
                };
            case "TLJTP":
                this.boar = true;
                return new Feature[][] {
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.UNKNOWN},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.TRAIL, Feature.TRAIL, Feature.TRAIL, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.UNKNOWN}
                };
            case "JLTT-":
                return new Feature[][] {
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.UNKNOWN},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.TRAIL, Feature.TRAIL, Feature.TRAIL, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.UNKNOWN}
                };
            case "JLTTB":
                this.buffalo = true;
                return new Feature[][] {
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.UNKNOWN},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.TRAIL, Feature.TRAIL, Feature.TRAIL, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.UNKNOWN}
                };
            //STARTING TILE
            case "TLTJ-":
                return new Feature[][] {
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.UNKNOWN},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.UNKNOWN}
                };
            case "TLTJD":
                this.deer = true;
                return new Feature[][] {
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.UNKNOWN},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.UNKNOWN}
                };
            case "TLLL-":
                return new Feature[][] {
                        new Feature[] {Feature.UNKNOWN, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.UNKNOWN},
                        new Feature[] {Feature.LAKE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE},
                        new Feature[] {Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE},
                        new Feature[] {Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE}
                };
            case "TLTT-":
                return new Feature[][] {
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.UNKNOWN},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.TRAIL, Feature.TRAIL, Feature.ENDPOINT, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.UNKNOWN}
                };
            case "TLTTP":
                this.boar = true;
                return new Feature[][] {
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.UNKNOWN},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.TRAIL, Feature.TRAIL, Feature.ENDPOINT, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.UNKNOWN}
                };
            //flood fill should connect regions. meeple placement of lake at 6 not 8 or 9
            case "TLLT-":
                return new Feature[][] {
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.UNKNOWN},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.TRAIL, Feature.TRAIL, Feature.TRAIL, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.UNKNOWN, Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE}
                };
            case "TLLTB":
                this.buffalo = true;
                return new Feature[][] {
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.UNKNOWN},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.TRAIL, Feature.TRAIL, Feature.TRAIL, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.UNKNOWN, Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE}
                };
            case "LJTJ-":
                return new Feature[][] {
                        new Feature[] {Feature.UNKNOWN, Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.UNKNOWN},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.LAKE, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.JUNGLE}
                };
            case "LJTJD":
                this.deer = true;
                return new Feature[][] {
                        new Feature[] {Feature.UNKNOWN, Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.UNKNOWN},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.LAKE, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.JUNGLE},
                        new Feature[] {Feature.JUNGLE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.JUNGLE}
                };
            case "TLLLC":
                this.croc = true;
                return new Feature[][] {
                        new Feature[] {Feature.UNKNOWN, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.UNKNOWN},
                        new Feature[] {Feature.LAKE, Feature.JUNGLE, Feature.TRAIL, Feature.JUNGLE, Feature.LAKE},
                        new Feature[] {Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE},
                        new Feature[] {Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE},
                        new Feature[] {Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE, Feature.LAKE}
                };
            default:
                throw new RuntimeException("Invalid tile created!");
        }

    }

}