const remote = require('electron').remote;
const argv = remote.getCurrentWindow().argv;
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

  board.innerHTML = grid;

  [].slice.apply(board.querySelectorAll(".cell"))
    .forEach((column) => column.addEventListener('click', click_cell));
}

function occupy_cell(x, y) {
  const cell = board
    .children[y]
    .children[x];

  cell.setAttribute('data-x', x);
  cell.setAttribute('data-y', y);

  cell.classList.add("occupied");
  cell.classList.remove("empty");
  cell.classList.remove("potential");
}

function potential_move(x, y) {
  const cell = board
    .children[y]
    .children[x];

  cell.setAttribute('data-x', x);
  cell.setAttribute('data-y', y);

  cell.classList.add("potential");
  cell.classList.remove("empty");
  cell.classList.remove("occupied");
}

function click_cell() {
  [ x, y ] = [ this.dataset.x, this.dataset.y ];

  if (this.classList.contains("potential")) {
    occupy_cell(x, y);

    tigerzone
      .place_card(active_card, { x: x, y: y })
      .then(data => console.log(data));
  }
}

function new_game() {
  tigerzone
    .new_game()
    .then(update_board)
    .catch((data) => console.log(data));
}

function update_board(data) {
  return board_element.textContent = JSON.stringify(data, null, '  ');
}

function display_active_card(data) {
  const url = tileLookup(data.id);

  active_card = data;
  active_card.url = url;

  active_card_element.style.backgroundImage = `url(${url})`;

  return data;
}

function display_moves(moves) {
  console.log(moves);
  moves
    .forEach((move) => {
      potential_move(move.x, move.y);
    });
}

function game_loop() {
  // 1. Draw card
  // 2. Get moves

  tigerzone
    .draw_card()
    .then(display_active_card)
    .then((card) => tigerzone.get_moves(card))
    .then(display_moves)
}

// Create the game board
create_board(144);

// Populate the player element
player_element.textContent = player || 0;

// Initiate the server
const tigerzone = new TigerZone({ location: 'node mock-server' });

// Bind the new game creation to the new game button
new_game_button
  .addEventListener('click', new_game);

// Set up a watcher for the board
tigerzone
  .watch_board(update_board, (data) => console.log(data));

// Fire up the game loop
game_loop();
