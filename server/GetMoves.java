import javax.json.*;
import java.io.*;

import java.awt.*;
import java.util.*;

import Utilities.BoardUtilities;

import GameState.Board;
import GameState.Tile;

public class GetMoves {
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

      Board board = new Board();

      board.fromJSON(obj);

      Map<Point, Collection<Board.Orientation>> points = BoardUtilities.getValidAdjacentPoints(new Tile("Single bubble city with straight road"), board);
      JsonArrayBuilder a = Json.createArrayBuilder();

      for (Map.Entry<Point, Collection<Board.Orientation>> entry : points.entrySet()) {
        Point point = entry.getKey();
        Collection<Board.Orientation> orientations = entry.getValue();
          JsonArrayBuilder orientationsJSON = Json.createArrayBuilder();

          for (Board.Orientation o : orientations) {
            orientationsJSON
              .add(o.toString());
          }

          a.add(
            Json
              .createObjectBuilder()
              .add("x", point.x)
              .add("y", point.y)
              .add("orientation", orientationsJSON)
          );
      }
      //
      // for (Point point : points.keySet()) {
      //   a.add(
      //     Json
      //       .createObjectBuilder()
      //       .add("x", point.x)
      //       .add("y", point.y)
      //   );
      // }

      System.out.println(a.build());
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
