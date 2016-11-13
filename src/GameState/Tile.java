package GameState;

import java.awt.*;

public class Tile {

	//2d array 5x5 of tile attributes
	private Feature[][] subGrid;
	private String name;
	private boolean shield = false;
	private final int id = 0;

    public Tile() {
        this.name = "Not set";
    }

	public Tile(String name) {
		this.name = name;
		switch(name) {
			case "Complete shielded city":
				this.shield = true;
				this.subGrid = new Feature[][] {
					new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY},
					new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY},
					new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY},
					new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY},
					new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY}															
				};
				break;
			case "3/4 city":		
				this.subGrid = new Feature[][] {
					new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY},
					new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY},
					new Feature[] {Feature.CITY, Feature.CITY, Feature.FIELD, Feature.CITY, Feature.CITY},
					new Feature[] {Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.CITY},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD}															
				};			
				break;
			case "3/4 shielded city":
				this.subGrid = new Feature[][] {
					new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY},
					new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY},
					new Feature[] {Feature.CITY, Feature.CITY, Feature.FIELD, Feature.CITY, Feature.CITY},
					new Feature[] {Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.CITY},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD}															
				};				
				this.shield = true;
				break;
			case "3/4 city with road":
				this.subGrid = new Feature[][] {
					new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY},
					new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY},
					new Feature[] {Feature.CITY, Feature.CITY, Feature.ROAD, Feature.CITY, Feature.CITY},
					new Feature[] {Feature.CITY, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.CITY},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD}															
				};					
				break;
			case "3/4 shielded city with road":
				this.shield = true;
				this.subGrid = new Feature[][] {
					new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY},
					new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY},
					new Feature[] {Feature.CITY, Feature.CITY, Feature.ROAD, Feature.CITY, Feature.CITY},
					new Feature[] {Feature.CITY, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.CITY},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD}															
				};					
				break;
			case "Quadruple crossroads":
				this.subGrid = new Feature[][] {
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.ROAD, Feature.ROAD, Feature.ENDPOINT, Feature.ROAD, Feature.ROAD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD}															
				};				
				break;
			case "Triple corssroads":
				this.subGrid = new Feature[][] {
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.ROAD, Feature.ROAD, Feature.ENDPOINT, Feature.ROAD, Feature.ROAD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD}															
				};				
				break;
			case "Straight road":
				this.subGrid = new Feature[][] {
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD}													
				};				
				break;
			case "Elbow road":
				this.subGrid = new Feature[][] {
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.ROAD, Feature.ROAD, Feature.ROAD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD}													
				};				
				break;
			case "Monastary":
				this.subGrid = new Feature[][] {
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.MONASTARY, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD}													
				};					
				break;
			case "Monastary with road":
				this.subGrid = new Feature[][] {
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.MONASTARY, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD}													
				};				
				break;
			// //FLOOD FILL SHOULD CHECK DIAGANOLS AS WELL BEETEEDUBS
			case "1/2 city with elbow road":
				this.subGrid = new Feature[][] {
					new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.FIELD},
					new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.CITY, Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.ROAD},
					new Feature[] {Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD}													
				};				
				break;
			case "1/2 shielded city with elbow road":
				this.shield = true;
				this.subGrid = new Feature[][] {
					new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.FIELD},
					new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.CITY, Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.ROAD},
					new Feature[] {Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD}													
				};					
				break;
			case "Tunnel city":
				this.subGrid = new Feature[][] {
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.CITY},
					new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY},
					new Feature[] {Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.CITY},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD}													
				};				
				break;
			case "Tunnel shielded city":
				this.shield = true;
				this.subGrid = new Feature[][] {
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.CITY},
					new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY},
					new Feature[] {Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.CITY},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD}													
				};					
				break;
			case "1/2 city":
				this.subGrid = new Feature[][] {
					new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.FIELD},
					new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.CITY, Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD}													
				};			
				break;
			case "1/2 shielded city":
				this.shield = true;
				this.subGrid = new Feature[][] {
					new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.CITY, Feature.FIELD},
					new Feature[] {Feature.CITY, Feature.CITY, Feature.CITY, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.CITY, Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD}													
				};				
				break;
			//weird case might run into problems. 
			case "Two bubble cities side by side":
				this.subGrid = new Feature[][] {
					new Feature[] {Feature.FIELD, Feature.CITY, Feature.CITY, Feature.CITY, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.CITY, Feature.FIELD, Feature.CITY},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.CITY, Feature.CITY},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.CITY},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD}													
				};
				break;
			case "Two bubble cities across from one another":
				this.subGrid = new Feature[][] {
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.CITY},
					new Feature[] {Feature.CITY, Feature.CITY, Feature.FIELD, Feature.CITY, Feature.CITY},
					new Feature[] {Feature.CITY, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.CITY},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD}													
				};				
				break;
			case "Single bubble city":
				this.subGrid = new Feature[][] {
					new Feature[] {Feature.FIELD, Feature.CITY, Feature.CITY, Feature.CITY, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.CITY, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD}													
				};			
				break;
			case "Single bubble city with elbow road":
				this.subGrid = new Feature[][] {
					new Feature[] {Feature.FIELD, Feature.CITY, Feature.CITY, Feature.CITY, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.CITY, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.ROAD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD}																			
				};				
				break;
			case "Single bubble city with elbow road (other direction)":
				this.subGrid = new Feature[][] {
					new Feature[] {Feature.FIELD, Feature.CITY, Feature.CITY, Feature.CITY, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.CITY, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.ROAD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD}												
				};				
				break;
			case "Single bubble city with triple crossroads":
				this.subGrid = new Feature[][] {
					new Feature[] {Feature.FIELD, Feature.CITY, Feature.CITY, Feature.CITY, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.CITY, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.ROAD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.ROAD},
					new Feature[] {Feature.FIELD, Feature.ROAD, Feature.ENDPOINT, Feature.ROAD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD}													
				};				
				break;
			//start piece
			case "Single bubble city with straight road":
				this.subGrid = new Feature[][] {
					new Feature[] {Feature.FIELD, Feature.CITY, Feature.CITY, Feature.CITY, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.CITY, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.ROAD, Feature.ROAD, Feature.FIELD, Feature.ROAD, Feature.ROAD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.ROAD, Feature.FIELD, Feature.FIELD},
					new Feature[] {Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD, Feature.FIELD}													
				};				
				break;
		}		

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

}