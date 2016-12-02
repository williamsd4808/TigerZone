const TigerZone = require('./modules/api-wrapper');
const tigerzone = new TigerZone({ location: 'server/ApiEndpoints' });

const tileLookup = require('./tiles.js');

let game, activeCard;

const toggle = (elt) => {
  if (elt.classList.contains('hidden')) {
    elt.classList.remove('hidden');
    elt.classList.add('show');
  } else {
    elt.classList.add('hidden');
    elt.classList.remove('show');
  }
};

const createBoard = (n) => {
  const boardElement = document.getElementById('board');

  const cell = '<div class="cell empty"></div>';

  const cells = new Array(n)
    .fill(cell)
    .join('\n');

  const col = `<div class="col">${cells}</div>`;

  const grid = new Array(n)
    .fill(col)
    .join('\n');

  boardElement.innerHTML = grid;

  [].slice.apply(boardElement.querySelectorAll(".cell"))
    .forEach((column) => column.addEventListener('click', onCellClick));
};

const onCellClick = (e) => {
  console.log(e.target.classList);
  if (!e.target.classList.contains('potential')) {
    return false;
  }

  const orientation = parseInt(document.getElementById('tile-img').dataset.orientation, 10);
  const x = parseInt(e.target.dataset.x, 10);
  const y = parseInt(e.target.dataset.y, 10);

  tigerzone
    .place_tile(game, activeCard, x, y, orientation)
    .then(data => console.log(data))
    .then(data => console.log(data));
}

const updatePlayerHUD = (board) => {
  if (board.Player1) {
    document.getElementById('name1').textContent = board.Player1.name;
    document.getElementById('score1').textContent = board.Player1.score;
    document.getElementById('meeples1').textContent = board.Player1.numOfMeeples;
  }

  if (board.Player2) {
    document.getElementById('name2').textContent = board.Player2.name;
    document.getElementById('score2').textContent = board.Player2.score;
    document.getElementById('meeples2').textContent = board.Player2.numOfMeeples;
  }

  return board;
};

const updateNextCardHUD = (board) => {
  if (board.Deck.tiles.length === 0) {
    return board;
  }

  activeCard = board.Deck.tiles[0].name;
  const url = tileLookup(activeCard);

  document.getElementById('tile-img').style.backgroundImage = `url(${url})`;

  return board;
};

const occupy = (cell, tile) => {
  cell.setAttribute('data-x', tile.x);
  cell.setAttribute('data-y', tile.y);
  cell.setAttribute('data-orientation', tile.orientation);

  cell.classList.add("occupied");
  cell.classList.remove("empty");
  cell.classList.remove("potential");

  const url = tileLookup(tile.name)
  cell.style.backgroundImage = `url(${url})`;

  return tile;
};

const displayPotentialMoves = (moves) => {
  const boardElement = document.getElementById('board');

  [].slice.apply(boardElement.querySelectorAll(".potential"))
    .forEach((cell) => cell.classList.remove('potential'));

  moves
    .forEach((tile) => {
      const cell = boardElement
        .children[tile.x + 72]
        .children[-tile.y + 72];

      cell.setAttribute('data-x', tile.x);
      cell.setAttribute('data-y', tile.y);
      cell.setAttribute('data-orientation', tile.orientation);

      cell.classList.add("potential");
      cell.classList.remove("empty");
      cell.classList.remove("occupied");
    });
}

const updateBoard = (board) => {
  const boardElement = document.getElementById('board');

  board
    .Board
    .tiles
    .map(tile => occupy(boardElement.children[tile.x + 72].children[-tile.y + 72], tile));
};

const getMoves = (board) => {
  if (board.Deck.tiles.length === 0) {
    return board;
  }

  const nextTile = board.Deck.tiles[0].name;

  tigerzone
    .get_moves(game, nextTile)
    .then(moves => displayPotentialMoves(moves))
    .catch(data => console.log(data));
}

const onGameUpdate = (board) => {
  console.log(board);
  updatePlayerHUD(board);
  updateNextCardHUD(board);
  updateBoard(board);
  getMoves(board);
};

document
  .addEventListener('keypress', (e) => {
    if (e.keyCode === 32) {
      e.preventDefault();
      const tileImg = document.getElementById('tile-img');
      tileImg.dataset.orientation = (parseInt(tileImg.dataset.orientation, 10) + 1) % 4;
    }
  });

document
  .getElementById('new-game-button')
  .addEventListener('click', () => toggle(document.getElementById('new-game-dialogue')));

document
  .getElementById('join-game-button')
  .addEventListener('click', () => toggle(document.getElementById('join-game-dialogue')));

document
  .getElementById('load-game-button')
  .addEventListener('click', () => toggle(document.getElementById('load-game-dialogue')));

document
  .getElementById('new-game-submit')
  .addEventListener('click', () => {
    game = document.getElementById('new-game-id').value;
    const player = document.getElementById('new-game-player').value;

    createBoard(144);

    tigerzone
      .new_game(game)
      .catch(data => console.log(data))

      .then(() => tigerzone.join_game(game, player))
      .catch(data => console.log(data))

      .then(() => tigerzone.watch_game(game, onGameUpdate, data => console.log(data)))
      .catch(data => console.log(data))

      .then(() => toggle(document.getElementById('start-screen')))
      .then(() => toggle(document.getElementById('game-screen')));
  });

document
  .getElementById('join-game-submit')
  .addEventListener('click', () => {
    game = document.getElementById('join-game-id').value;
    const player = document.getElementById('join-game-player').value;

    createBoard(144);

    tigerzone
      .join_game(game, player)
      .catch(data => console.log(data))

      .then(() => tigerzone.watch_game(game, onGameUpdate, (data) => console.log(data)))
      .catch(data => console.log(data))

      .then(() => toggle(document.getElementById('start-screen')))
      .then(() => toggle(document.getElementById('game-screen')));
  });

document
  .getElementById('load-game-submit')
  .addEventListener('click', () => {
    game = document.getElementById('load-game-id').value;

    createBoard(144);

    toggle(document.getElementById('start-screen'));
    toggle(document.getElementById('game-screen'));

    tigerzone
      .watch_game(game, onGameUpdate, (data) => console.log(data));
  });
