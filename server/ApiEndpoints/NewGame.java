package ApiEndpoints;

import GameState.Board;
import GameState.Deck;
import GameState.Engine;

import java.util.ArrayList;

public class NewGame {
    public static void main(String[] args) {

        String savedEngine = args[0]; // The name of the saved engine state

        BaseApiEndpoint endpoint = new BaseApiEndpoint() {

            public final void execute(String saveName) {

                Engine engine = new Engine();
                engine.newGame(System.currentTimeMillis());
                Engine.toJson(saveName, engine);

            }

        };

        endpoint.execute(savedEngine);

    }

}
