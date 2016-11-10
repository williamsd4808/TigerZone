const remote = require('electron').remote;
const argv = remote.getCurrentWindow().argv;
const player = parseInt(argv[2], 10);

const TigerZone = require('./modules/api-wrapper');

let board_element = document.getElementById('board');
let player_element = document.getElementById('player-id');
let new_game_button = document.getElementById('new-game');

player_element.textContent = player || 0;

const tigerzone = new TigerZone({ location: 'mock-server' });

tigerzone
  .watch_board(update_board, (data) => console.log(data));

new_game_button
  .addEventListener('click', new_game);

function new_game() {
  tigerzone
    .new_game()
    .then(update_board)
    .catch((data) => console.log(data));
}

function update_board(data) {
  return board_element.textContent = JSON.stringify(data, null, '  ');
}
