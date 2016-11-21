package GameState;

import Utilities.UniformPicker;

import javax.json.*;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Deck {

    private static int TILE_COUNT = 71;

    /*
     * Tile distribution
     *
     * 1 - complete shielded city
     * 3 - 3/4 city
     * 1 - 3/4 shielded city
     * 1 - 3/4 city with road
     * 2 - 3/4 shielded city with road
     * 1 - quadruple crossroads
     * 4 - triple crossroads
     * 8 - straight road
     * 9 - elbow road
     * 4 - monestary
     * 2 - monestary with road
     * 3 - 1/2 city with elbow road
     * 2 - 1/2 shielded city with elbow road
     * 1 - tunnel city
     * 2 - tunnel shielded city
     * 3 - 1/2 city
     * 2 - 1/2 shielded city
     * 2 - two bubble cities side by side
     * 3 - two bubble cities across from one another
     * 5 - single bubble city
     * 3 - single bubble city with elbow road
     * 3 - single bubble city with elbow road (other direction)
     * 3 - single bubble city with triple crossroads
     * 3 + 1 (start) - single bubble city with straight road
     */

	private final Queue<Tile> deck = new LinkedBlockingQueue<>(TILE_COUNT);

    private Deck() {}

	public Deck(long seed) {

        UniformPicker<Tile> tilePicker = new UniformPicker<>(seed);
        addTilesToPicker(tilePicker, new Tile("Complete shielded city"), 1); // 1 - complete shielded city
        addTilesToPicker(tilePicker, new Tile("3/4 city"), 3); // 3 - 3/4 city
        addTilesToPicker(tilePicker, new Tile("3/4 shielded city"), 1); // 1 - 3/4 shielded city
        addTilesToPicker(tilePicker, new Tile("3/4 city with road"), 1); // 1 - 3/4 city with road
        addTilesToPicker(tilePicker, new Tile("3/4 shielded city with road"), 2); // 2 - 3/4 shielded city with road
        addTilesToPicker(tilePicker, new Tile("Quadruple crossroads"), 1); // 1 - quadruple crossroads
        addTilesToPicker(tilePicker, new Tile("Triple corssroads"), 4); // 4 - triple crossroads
        addTilesToPicker(tilePicker, new Tile("Straight road"), 8); // 8 - straight road
        addTilesToPicker(tilePicker, new Tile("Elbow road"), 9); // 9 - elbow road
        addTilesToPicker(tilePicker, new Tile("Monastary"), 4); // 4 - monestary
        addTilesToPicker(tilePicker, new Tile("Monastary with road"), 2); // 2 - monestary with road
        addTilesToPicker(tilePicker, new Tile("1/2 city with elbow road"), 3); // 3 - 1/2 city with elbow road
        addTilesToPicker(tilePicker, new Tile("1/2 shielded city with elbow road"), 2); // 2 - 1/2 shielded city with elbow road
        addTilesToPicker(tilePicker, new Tile("Tunnel city"), 1); // 1 - tunnel city
        addTilesToPicker(tilePicker, new Tile("Tunnel shielded city"), 2); // 2 - tunnel shielded city
        addTilesToPicker(tilePicker, new Tile("1/2 city"), 3); // 3 - 1/2 city
        addTilesToPicker(tilePicker, new Tile("1/2 shielded city"), 2); // 2 - 1/2 shielded city
        addTilesToPicker(tilePicker, new Tile("Two bubble cities side by side"), 2); // 2 - two bubble cities side by side
        addTilesToPicker(tilePicker, new Tile("Two bubble cities across from one another"), 3); // 3 - two bubble cities across from one another
        addTilesToPicker(tilePicker, new Tile("Single bubble city"), 5); // 5 - single bubble city
        addTilesToPicker(tilePicker, new Tile("Single bubble city with elbow road"), 3); // 3 - single bubble city with elbow road
        addTilesToPicker(tilePicker, new Tile("Single bubble city with elbow road (other direction)"), 3); // 3 - single bubble city with elbow road (other direction)
        addTilesToPicker(tilePicker, new Tile("Single bubble city with triple crossroads"), 3); // 3 - single bubble city with triple crossroads
        addTilesToPicker(tilePicker, new Tile("Single bubble city with straight road"), 3); // 3 + 1 (start) - single bubble city with straight road

        while(tilePicker.validDecisionsToPick()) {

            deck.add(tilePicker.pollChoice()); // Randomly picks tiles from the picker until it is empty

        }

	}

	public Tile drawTile() {

        if (deck.isEmpty()) {

            return null;

		}

		return deck.poll();

	}

	public boolean hasTileToDraw() {

        return !deck.isEmpty();

    }

    private void addTilesToPicker(UniformPicker<Tile> picker, Tile tile, int count) {

        for (int i = 0; i < count; i++) {

            picker.addChoice(tile, 1.0);

        }

    }

    public static Deck fromJson(JsonObject json) {

        Deck deck = new Deck();

        JsonArray tiles = json.getJsonArray("tiles");

        for (JsonObject rawTile : tiles.getValuesAs(JsonObject.class)) {

            deck.deck.add(new Tile(rawTile.getString("name")));

        }

        return deck;

    }

    public static JsonObject toJson(Deck deck) {

        JsonObjectBuilder res = Json.createObjectBuilder();

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        while (deck.hasTileToDraw()) {

            Tile tile = deck.drawTile();
            arrayBuilder.add(Json.createObjectBuilder().add("name", tile.getName()));

        }

        return res.build();
    }

}