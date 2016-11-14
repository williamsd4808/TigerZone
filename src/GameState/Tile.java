package GameState;

import java.awt.*;
import java.io.*;

public class Tile implements Serializable {

    private static final long serialVersionUID = -1400979803257642370L;

    //2d array 5x5 of tile attributes
	private Feature[][] subGrid;
	private String name;
	private boolean shield = false;
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

        return name;

	}

	private Feature[][] initializeSubGrid(String name) {

        switch(name) {
            case "Complete shielded city":
                this.shield = true;
                return new Feature[][] {
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY},
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY},
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY},
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY},
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY}
                };
            case "3/4 city":
                return new Feature[][] {
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY},
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY},
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.FIELD, Feature.CITY, Feature.CITY},
                        new Feature[] {Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.CITY},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD}
                };
            case "3/4 shielded city":
                this.shield = true;
                return new Feature[][] {
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY},
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY},
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.FIELD, Feature.CITY, Feature.CITY},
                        new Feature[] {Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.CITY},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD}
                };
            case "3/4 city with road":
                return new Feature[][] {
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY},
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY},
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.ROAD, Feature.CITY, Feature.CITY},
                        new Feature[] {Feature.CITY, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.CITY},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD}
                };
            case "3/4 shielded city with road":
                this.shield = true;
                return new Feature[][] {
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY},
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY},
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.ROAD, Feature.CITY, Feature.CITY},
                        new Feature[] {Feature.CITY, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.CITY},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD}
                };
            case "Quadruple crossroads":
                return new Feature[][] {
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.ROAD, Feature.ROAD, Feature.ENDPOINT, Feature.ROAD, Feature.ROAD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD}
                };
            case "Triple corssroads":
                return new Feature[][] {
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.ROAD, Feature.ROAD, Feature.ENDPOINT, Feature.ROAD, Feature.ROAD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD}
                };
            case "Straight road":
                return new Feature[][] {
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD}
                };
            case "Elbow road":
                return new Feature[][] {
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.ROAD, Feature.ROAD, Feature.ROAD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD}
                };
            case "Monastary":
                return new Feature[][] {
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.MONASTARY, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD}
                };
            case "Monastary with road":
                return new Feature[][] {
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.MONASTARY, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD}
                };
            // //FLOOD FILL SHOULD CHECK DIAGANOLS AS WELL BEETEEDUBS
            case "1/2 city with elbow road":
                return new Feature[][] {
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.FIELD},
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.ROAD},
                        new Feature[] {Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD}
                };
            case "1/2 shielded city with elbow road":
                this.shield = true;
                return new Feature[][] {
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.FIELD},
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.ROAD},
                        new Feature[] {Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD}
                };
            case "Tunnel city":
                return new Feature[][] {
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.CITY},
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY},
                        new Feature[] {Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.CITY},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD}
                };
            case "Tunnel shielded city":
                this.shield = true;
                return new Feature[][] {
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.CITY},
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY},
                        new Feature[] {Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.CITY},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD}
                };
            case "1/2 city":
                return new Feature[][] {
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.FIELD},
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD}
                };
            case "1/2 shielded city":
                this.shield = true;
                return new Feature[][] {
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.FIELD},
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD}
                };
            //weird case might run into problems.
            case "Two bubble cities side by side":
                return new Feature[][] {
                        new Feature[] {Feature.FIELD, Feature.CITY, Feature.CITY, Feature.CITY, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.CITY, Feature.FIELD, Feature.CITY},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.CITY, Feature.CITY},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.CITY},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD}
                };
            case "Two bubble cities across from one another":
                return new Feature[][] {
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.CITY},
                        new Feature[] {Feature.CITY, Feature.CITY, Feature.FIELD, Feature.CITY, Feature.CITY},
                        new Feature[] {Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.CITY},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD}
                };
            case "Single bubble city":
                return new Feature[][] {
                        new Feature[] {Feature.FIELD, Feature.CITY, Feature.CITY, Feature.CITY, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.CITY, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD}
                };
            case "Single bubble city with elbow road":
                return new Feature[][] {
                        new Feature[] {Feature.FIELD, Feature.CITY, Feature.CITY, Feature.CITY, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.CITY, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.ROAD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD}
                };
            case "Single bubble city with elbow road (other direction)":
                return new Feature[][] {
                        new Feature[] {Feature.FIELD, Feature.CITY, Feature.CITY, Feature.CITY, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.CITY, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.ROAD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD}
                };
            case "Single bubble city with triple crossroads":
                return new Feature[][] {
                        new Feature[] {Feature.FIELD, Feature.CITY, Feature.CITY, Feature.CITY, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.CITY, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.ROAD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.ROAD},
                        new Feature[] {Feature.FIELD, Feature.ROAD, Feature.ENDPOINT, Feature.ROAD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD}
                };
            //start piece
            case "Single bubble city with straight road":
                return new Feature[][] {
                        new Feature[] {Feature.FIELD, Feature.CITY, Feature.CITY, Feature.CITY, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.CITY, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.ROAD, Feature.ROAD, Feature.FIELD, Feature.ROAD, Feature.ROAD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD},
                        new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD}
                };
            default:
                throw new RuntimeException("Invalid tile created!");
        }

    }

    private void readObject(ObjectInputStream inputStream) throws ClassNotFoundException, IOException {

        String name = (String) inputStream.readObject();
        this.name = name;
        this.subGrid = initializeSubGrid(name);

    }
    private void writeObject(ObjectOutputStream outputStream) throws IOException {

        outputStream.writeObject(name);

    }

}