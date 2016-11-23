package ApiEndpoints;

import GameState.Board;
import GameState.Deck;
import GameState.Engine;

import java.util.ArrayList;

public class NewGame {
    public static void main(String[] args) {

        String savedEngine = args[0]; // The name of the saved engine state

        BaseApiEndpoint endpoint = new BaseMutableApiEndpoint() {

            protected void doExecute(Engine engine) {

                engine.setPlayers(new ArrayList<>());
                engine.setDeck(new Deck(System.currentTimeMillis()));
                engine.setBoard(new Board());

            }

        };

        endpoint.execute(savedEngine);

    }

}
