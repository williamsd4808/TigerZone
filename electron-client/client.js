const remote = require('electron').remote;
const argv = remote.getCurrentWindow().argv;
const exec = require('child_process').exec;
const player = parseInt(argv[2], 10);

const tileLookup = require('./tiles.js');

const TigerZone = require('./modules/api-wrapper');

let board_element = document.getElementById('board');
let player_element = document.getElementById('player-id');
let new_game_button = document.getElementById('new-game');
let active_card_element = document.getElementById('active-card');

let active_card = {};

function create_board(n) {
  const cell = '<div class="cell empty"></div>';

  const cells = new Array(n)
    .fill(cell)
    .join('\n');

  const col = `<div class="col">${cells}</div>`;

  const grid = new Array(n)
    .fill(col)
    .join('\n');

  board_element.innerHTML = grid;

  [].slice.apply(board_element.querySelectorAll(".cell"))
    .forEach((column) => column.addEventListener('click', click_cell));
}

function occupy_cell(x, y, card) {
  const cell = board_element
    .children[y + 72]
    .children[x + 72];

  cell.setAttribute('data-x', x);
  cell.setAttribute('data-y', y);

  cell.classList.add("occupied");
  cell.classList.remove("empty");
  cell.classList.remove("potential");

  const url = tileLookup(card.id)
  cell.style.backgroundImage = `url(${url})`;
}

function potential_move(x, y, orientation) {
  const cell = board_element
    .children[y + 72]
    .children[x + 72];

  cell.setAttribute('data-x', x);
  cell.setAttribute('data-y', y);
  cell.setAttribute('data-orientation', orientation);

  cell.classList.add("potential");
  cell.classList.remove("empty");
  cell.classList.remove("occupied");
}

function click_cell() {
  [ x, y, orientation ] = [ this.dataset.x, this.dataset.y, this.dataset.orientation ];

  if (this.classList.contains("potential")) {
    tigerzone
      .place_card(active_card, { x: x, y: y, orientation: orientation })
      .then(game_loop)
      .then(data => console.log(data))
      .catch(data => console.log(data));
  }
}

function new_game() {
  tigerzone
    .new_game()
    .then(() => tigerzone.get_moves())
    .then(update_board)
    .catch((data) => console.log(data));
}

function update_board(data) {
  if (typeof data == "string") {
    data = JSON.parse(data);
  }
  console.log(data);

  data
    .board
    .forEach(({ x, y, tile }) => occupy_cell(x, y, tile));

  return data;
}

function display_active_card(data) {
  const url = tileLookup(data.id);

  active_card = data;
  active_card.url = url;

  active_card_element.style.backgroundImage = `url(${url})`;

  return data;
}

function display_moves(moves) {
  moves
    .forEach((move) => {
      potential_move(move.x, move.y, move.orientation);
    });

  return moves;
}

function game_loop() {
  // 1. Draw card
  // 2. Get moves

  tigerzone
    .draw_card()
    .then(display_active_card)
    .then(() => tigerzone.get_moves())
    .then(display_moves)
    .catch(data => console.log(data));
}

// Create the game board
create_board(144);

// Populate the player element
player_element.textContent = player || 0;

// Initiate the server
const tigerzone = new TigerZone({ location: 'node mock-server' });

exec("touch mock-server/sample-datastore.json");

// Bind the new game creation to the new game button
new_game_button
  .addEventListener('click', new_game);

// Set up a watcher for the board
tigerzone
  .watch_board(update_board, (data) => console.log(data));

// Fire up the game loop
game_loop();
