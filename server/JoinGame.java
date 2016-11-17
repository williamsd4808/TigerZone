import javax.json.*;
import java.io.*;

import GameState.Board;

public class JoinGame {
  public static void main(String[] args) {
    try {
      File file = new File("server/datastore.json");
      FileInputStream fis = new FileInputStream(file);
      byte[] data = new byte[(int) file.length()];
      fis.read(data);
      fis.close();

      String str = new String(data, "UTF-8");

      JsonReader jsonReader = Json.createReader(new StringReader(str));
      JsonObject obj = jsonReader.readObject();

      JsonArray players = obj.getJsonArray("players");
      JsonArrayBuilder newPlayers = Json.createArrayBuilder();

      for (JsonValue player : players) {
  			newPlayers.add(player);
  		}

      newPlayers.add(args[0]);

      JsonObject newGameState = Json.createObjectBuilder()
        .add("players", newPlayers)
        .add("turn", obj.get("turn"))
        .add("board", obj.get("board"))
        .build();

      PrintWriter writer = new PrintWriter("server/datastore.json", "UTF-8");

      writer.println(newGameState);
      writer.close();

      System.out.println(newGameState);
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
