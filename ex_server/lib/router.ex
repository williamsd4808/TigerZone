defmodule ExServer.Router do
  def main(argv) do
    case hd(argv) do
      "new-game" -> ExServer.NewGame.execute(Enum.at(argv, 1))
      "join-game" -> ExServer.JoinGame.execute(Enum.at(argv, 1), Enum.at(argv, 2))
    end
  end
end
