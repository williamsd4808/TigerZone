package GameState;

import Utilities.BoardUtilities;
import Utilities.UniformPicker;

import javax.json.*;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Deck {

    private static int TILE_COUNT = 76;

	private final Queue<Tile> deck = new LinkedBlockingQueue<>(TILE_COUNT);
    private Engine engine;

    private Deck(Engine engine) {
        this.engine = engine;
    }

	public Deck(Engine engine, long seed) {

        this.engine = engine;

        UniformPicker<Tile> tilePicker = new UniformPicker<>(seed);
        addTilesToPicker(tilePicker, new Tile("JJJJ-"), 1);
        addTilesToPicker(tilePicker, new Tile("JJJJX"), 4);
        addTilesToPicker(tilePicker, new Tile("JJTJX"), 2);
        addTilesToPicker(tilePicker, new Tile("TTTT-"), 1);
        addTilesToPicker(tilePicker, new Tile("TJTJ-"), 8);
        addTilesToPicker(tilePicker, new Tile("TJJT-"), 9);
        addTilesToPicker(tilePicker, new Tile("TJTT-"), 4);

        addTilesToPicker(tilePicker, new Tile("LLLL-"), 1);
        addTilesToPicker(tilePicker, new Tile("JLLL-"), 4);
        addTilesToPicker(tilePicker, new Tile("LLJJ-"), 5);
        addTilesToPicker(tilePicker, new Tile("JLJL-"), 3);
        addTilesToPicker(tilePicker, new Tile("LJLJ-"), 3);
        addTilesToPicker(tilePicker, new Tile("LJJJ-"), 5);
        addTilesToPicker(tilePicker, new Tile("JLLJ-"), 2);

        addTilesToPicker(tilePicker, new Tile("TLJT-"), 1);
        addTilesToPicker(tilePicker, new Tile("TLJTP"), 2);
        addTilesToPicker(tilePicker, new Tile("JLTT-"), 1);
        addTilesToPicker(tilePicker, new Tile("JLTTB"), 2);
        addTilesToPicker(tilePicker, new Tile("TLTJ-"), 2); //STARTING TILE
        addTilesToPicker(tilePicker, new Tile("TLTJD"), 2);
        addTilesToPicker(tilePicker, new Tile("TLLL-"), 1);

        addTilesToPicker(tilePicker, new Tile("TLTT-"), 1);
        addTilesToPicker(tilePicker, new Tile("TLTTP"), 2);
        addTilesToPicker(tilePicker, new Tile("TLLT-"), 3);
        addTilesToPicker(tilePicker, new Tile("TLLTB"), 2);
        addTilesToPicker(tilePicker, new Tile("LJTJ-"), 1);
        addTilesToPicker(tilePicker, new Tile("LJTJD"), 2);
        addTilesToPicker(tilePicker, new Tile("TLLLC"), 2);

        while(tilePicker.validDecisionsToPick()) {

            deck.add(tilePicker.pollChoice()); // Randomly picks tiles from the picker until it is empty

        }

	}

	public Tile drawTile() {

        if (deck.isEmpty()) {

            return null;

		}

        Tile drawnTile;

        do {

            drawnTile = deck.poll();

        } while (BoardUtilities.getValidAdjacentPoints(drawnTile, engine.board).isEmpty());

		return drawnTile;

	}

    // public void generateDeck(String[] deckFromServer, int numOfTiles) {
    //     for(int i = 0; i < numOfTiles; i++) {
    //         deck.add(deckFromServer[i]);
    //     }
    // }

	public boolean hasTileToDraw() {

        return !deck.isEmpty();

    }

    private void addTilesToPicker(UniformPicker<Tile> picker, Tile tile, int count) {

        for (int i = 0; i < count; i++) {

            picker.addChoice(tile, 1.0);

        }

    }

    public static Deck fromJson(Engine engine, JsonObject json) {

        Deck deck = new Deck(engine);

        JsonArray tiles = json.getJsonArray("tiles");

        for (JsonObject rawTile : tiles.getValuesAs(JsonObject.class)) {

            deck.deck.add(new Tile(rawTile.getString("name")));

        }

        return deck;

    }

    public static JsonObject toJson(Deck deck) {

        JsonObjectBuilder res = Json.createObjectBuilder();

        JsonArrayBuilder tiles = Json.createArrayBuilder();

        while (deck.hasTileToDraw()) {

            Tile tile = deck.drawTile();
            tiles.add(Json.createObjectBuilder().add("name", tile.getName()).build());

        }

        res.add("tiles", tiles);

        return res.build();
    }

}