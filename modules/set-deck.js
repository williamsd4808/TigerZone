const fs = require('fs');

module.exports = function(game, deck) {
  let board = require(`./../server/ApiEndpoints/SavedGames/${game}.json`);

  board.Deck.tiles = deck.map(name => ({ name: name }));

  const json = JSON.stringify(board);

  fs.writeFile(`server/ApiEndpoints/SavedGames/${game}.json`, json, 'utf8', (d) => console.log(d));
};
