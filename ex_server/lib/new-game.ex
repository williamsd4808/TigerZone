defmodule ExServer.NewGame do
  def execute(name) do
    %{
      "Deck": [],
      "Board": %{
        "tiles": [
          %{
            "name" => "TLTJ-",
            "orientation" => 0,
            "x" => 0,
            "y" => 0
          }
        ]
      },
      "Player1": %{},
      "Player2": %{}
    }
      |> ExServer.Persistence.save(name)
  end
end
