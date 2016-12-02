package GameState;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Engine {
	public List<Player> players;
	public Deck deck;
	public Board board;
    public int turn = 0;

    public void newGame(long seed) {

        players = new ArrayList<>();
        deck = new Deck(this, seed);
        board = new Board(this);
        turn = 0;

    }

    public void playerJoin(String playerName) {

        if (players.size() > 2) {

            players.add(new Player(playerName));

        }

    }

    public Tile drawTile() {

        return deck.drawTile();

    }

    public void addTileToBoard(Point point, Tile tile, Board.Orientation orientation) {

        board.addTile(point, tile, orientation);
        turn = turn++ % 2;

    }

    // Add functions here that deal with all of carcassonne

    public void setPlayers(List<Player> players) {

        this.players = players;

    }

    public void setDeck(Deck deck) {

        this.deck = deck;

    }

    public void setBoard(Board board) {

        this.board = board;

    }

    public static Engine fromJson(String saveName) {

        Engine engine = new Engine();
        engine.players = new ArrayList<>();

        try {

            File file = new File("server/ApiEndpoints/SavedGames/" + saveName + ".json");
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            String str = new String(data, "UTF-8");

            JsonReader jsonReader = Json.createReader(new StringReader(str));
            JsonObject obj = jsonReader.readObject();

            engine.turn = obj.getInt("turn");

            JsonObject player1obj = obj.getJsonObject("Player1");

            if (player1obj != null) {

                engine.players.add(Player.fromJson(player1obj));

            }

            JsonObject player2obj = obj.getJsonObject("Player2");

            if (player2obj != null) {

                engine.players.add(Player.fromJson(player2obj));

            }

            engine.deck = Deck.fromJson(engine, obj.getJsonObject("Deck"));
            engine.board = Board.fromJson(engine, obj.getJsonObject("Board"));

        } catch (Exception e) {

            System.out.println(e);

        }

        return engine;

    }

    public static void toJson(String saveName, Engine engine) {

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        objectBuilder.add("turn", engine.turn);

        if (engine.players.size() == 1) {

            objectBuilder.add("Player1", Player.toJson(engine.players.get(0)));

        } else if (engine.players.size() == 2) {

            objectBuilder.add("Player1", Player.toJson(engine.players.get(0)));
            objectBuilder.add("Player2", Player.toJson(engine.players.get(1)));

        }

        objectBuilder.add("Deck", Deck.toJson(engine.deck));
        objectBuilder.add("Board", Board.toJson(engine.board));

        try {

            File file = new File("server/ApiEndpoints/SavedGames/" + saveName + ".json");
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file));

            writer.write(objectBuilder.build().toString());

            writer.close();

        } catch (Exception e) {

            System.out.println(e);

        }

    }

}
