const Promise = require('promise');
const execPromise = Promise.denodeify(require('child_process').exec);
const exec = require('child_process').exec;

const forcePlace = require('../../../modules/force-place.js');

const Record = require('immutable').Record;

const ServerRecord = Record({
  'location': 'server'
});

class Server extends ServerRecord {
  GET(exe, route) {
    return execPromise(`${exe} ${this.location}${route}`);
  }

  POST(exe, route, args) {
    const json_data = args.join(" ");

    return execPromise(`${exe} ${this.location}${route} ${json_data}`);
  }

  LISTEN(exe, route, game, out, err) {
    const location = this.location;

    const endpoint = exec(`${exe} ${location}${route} ${game}`);

    endpoint.stdout.on('data', (data) => out(JSON.parse(data)));
    endpoint.stderr.on('data', (data) => err(data));

    return this;
  }
}

class TigerZone extends Server {
  new_game(name) {
    return this
      .POST('bash', '/new-game', [name]);
  }

  join_game(game, player) {
    return this
      .POST('bash', '/join-game', [game, player]);
  }

  get_moves(game, card) {
    return this
      .POST('bash', '/get-moves', [game, `"${card}"`])
      .then(JSON.parse);
  }

  place_tile(game, card, x, y, orientation) {
    return this
      .POST('bash', '/place-tile', [game, `"${card}"`, x, y, orientation]);
  }

  force_place_tile(game, card, x, y, orientation) {
    forcePlace(game, card, x, y, orientation);
  }

  place_meeple(board, location) {
    return this
      .POST('/place-meeple', { board: board, location: location })
      .then(JSON.parse);
  }

  watch_game(game, out, err) {
    return this
      .LISTEN('node', '/watch-game', game, out, err);
  }
}

module.exports = TigerZone;
