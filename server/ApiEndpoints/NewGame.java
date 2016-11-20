package ApiEndpoints;

import javax.json.*;
import java.io.PrintWriter;

import GameState.Board;

public class NewGame {
  public static void main(String[] args) {
    Board board = new Board();

    JsonObject res = Json.createObjectBuilder()
      .add("players", Json.createArrayBuilder())
      .add("turn", 0)
      .add("board", board.toJSON())
      .build();

    try{
      PrintWriter writer = new PrintWriter("server/datastore.json", "UTF-8");

      writer.println(res);
      writer.close();

      System.out.println(res);
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
