const fs = require('fs');

module.exports = function(game, tile, x, y, orientation) {
  let board = require(`./../server/ApiEndpoints/SavedGames/${game}.json`);

  board.Board.tiles.push({
    'x': x,
    'y': y,
    'orientation': orientation,
    'name': tile
  });

  const json = JSON.stringify(board);

  fs.writeFileSync(`server/ApiEndpoints/SavedGames/${game}.json`, json);
};
