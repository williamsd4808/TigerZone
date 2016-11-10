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

  POST(route, data) {
    const json_data = encodeURIComponent(JSON.stringify(data));

    return execPromise(`${this.location}${route} ${json_data}`);
  }

  LISTEN(route, out, err) {
    const location = this.location;

    const endpoint = exec(`${location}${route}`);

    endpoint.stdout.on('data', (data) => out(JSON.parse(data)));
    endpoint.stderr.on('data', (data) => err(JSON.parse(data)));

    return this;
  }
}

class TigerZone extends Server {
  new_game() {
    return this
      .GET('/new-game')
      .then(JSON.parse);
  }

  draw_card() {
    return this
      .GET('/draw-card')
      .then(JSON.parse);
  }

  get_moves(card) {
    return this
      .POST('/get-moves', card)
      .then(JSON.parse);
  }

  place_card(card, location) {
    return this
      .POST('/place-card', { card: card, location: location })
      .then(JSON.parse);
  }

  place_meeple(board, location) {
    return this
      .POST('/place-meeple', { board: board, location: location })
      .then(JSON.parse);
  }

  watch_board(out, err) {
    return this
      .LISTEN('/watch-board', out, err);
  }
}

module.exports = TigerZone;
