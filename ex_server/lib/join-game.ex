defmodule ExServer.JoinGame do
  def execute(gid, player) do
    game = gid
      |> ExServer.Persistence.load

    new_player = %{
      "name": player,
      "score": 0,
      "numOfMeeples": 7
    }

    if game["Player2"] != %{} do
      throw "The game is full!"
    end

    game = (
      if game["Player1"] == %{} do
        %{ game | "Player1" => new_player }
      else
        %{ game | "Player2" => new_player }
      end
    )

    game
      |> ExServer.Persistence.save(gid)
  end
end
