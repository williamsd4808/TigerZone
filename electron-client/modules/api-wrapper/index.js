const Promise = require('promise');
const execPromise = Promise.denodeify(require('child_process').exec);
const exec = require('child_process').exec;

const Record = require('immutable').Record;

const ServerRecord = Record({
  'location': 'server'
});

class Server extends ServerRecord {
  GET(route) {
    return execPromise(`${this.location}${route}`);
  }

  POST(route, args) {
    const json_data = args.join(" ");

    return execPromise(`${this.location}${route} ${json_data}`);
  }

  LISTEN(route, game, out, err) {
    const location = this.location;

    const endpoint = exec(`${location}${route} ${game}`);

    endpoint.stdout.on('data', (data) => out(JSON.parse(data)));
    endpoint.stderr.on('data', (data) => err(data));

    return this;
  }
}

class TigerZone extends Server {
  new_game(name) {
    return this
      .POST('/new-game', [name])
      .then(JSON.parse);
  }

  join_game(game, player) {
    return this
      .POST('/join-game', [game, player])
      .then(JSON.parse);
  }

  get_moves(game, card) {
    return this
      .POST('/get-moves', [game, card])
      .then(JSON.parse);
  }

  place_tile(game, card, location) {
    return this
      .POST('/place-tile', [game, card, location.x, location.y, location.orientation])
      .then(JSON.parse);
  }

  place_meeple(board, location) {
    return this
      .POST('/place-meeple', { board: board, location: location })
      .then(JSON.parse);
  }

  watch_game(game, out, err) {
    console.log(game, out, err);
    return this
      .LISTEN('/watch-game', game, out, err);
  }
}

module.exports = TigerZone;
