package ApiEndpoints;

import GameState.Engine;
import GameState.Player;

public class JoinGame {

    public static void main(String[] args) {

        String savedEngine = args[0]; // The name of the saved engine state
        String newPlayerName = args[1]; // The name of the player to add to the game

        BaseApiEndpoint endpoint = new BaseMutableApiEndpoint() {

          protected void doExecute(Engine engine) {

              if (engine.players.size() > 1) {

                  System.err.println("\"error\":\"The player cannot be added to the game as the game is full\"");
                  return;

              }

              Player player = new Player(newPlayerName);
              engine.players.add(player);

          }

      };

      endpoint.execute(savedEngine);

    }

}
