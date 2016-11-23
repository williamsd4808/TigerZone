package ApiEndpoints;

import javax.json.*;

import java.awt.*;
import java.util.*;

import GameState.Engine;
import Utilities.BoardUtilities;

import GameState.Board;
import GameState.Tile;

public class GetMoves {

    public static void main(String[] args) {

        String savedEngine = args[0]; // The name of the saved engine state
        String tileName = args[1]; // The name of the tile we intend to place on the board

        BaseApiEndpoint endpoint = new BaseImmutableApiEndpoint() {

            protected void doExecute(Engine engine) {

                Map<Point, Collection<Board.Orientation>> points = BoardUtilities.getValidAdjacentPoints(new Tile(tileName), engine.board);
                JsonArrayBuilder a = Json.createArrayBuilder();

                for (Map.Entry<Point, Collection<Board.Orientation>> entry : points.entrySet()) {
                    Point point = entry.getKey();
                    Collection<Board.Orientation> orientations = entry.getValue();
                    JsonArrayBuilder orientationsJSON = Json.createArrayBuilder();

                    for (Board.Orientation o : orientations) {

                        orientationsJSON
                                .add(o.ordinal());

                    }

                    a.add(
                            Json
                                    .createObjectBuilder()
                                    .add("x", point.x)
                                    .add("y", point.y)
                                    .add("orientation", orientationsJSON)
                    );
                }

                System.out.println(a.build());

            }

        };

        endpoint.execute(savedEngine);

    }

}
