defmodule ExServer.Persistence do
  def load(name) do
    "server/ApiEndpoints/SavedGames/#{name}.json"
      |> File.read!
      |> Poison.Parser.parse!
  end

  def save(data, name) do
    data = Poison.encode!(data)

    "server/ApiEndpoints/SavedGames/#{name}.json"
      |> File.write!(data)
  end
end
