const TigerZone = require('./modules/api-wrapper');
const tigerzone = new TigerZone({ location: 'server/ApiEndpoints' });

const tileLookup = require('./tiles.js');

const toggle = (elt) => {
  if (elt.classList.contains('hidden')) {
    elt.classList.remove('hidden');
    elt.classList.add('show');
  } else {
    elt.classList.add('hidden');
    elt.classList.remove('show');
  }
};

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
  const url = tileLookup(board.Deck.tiles[0].name);

  document.getElementById('tile-img').style.backgroundImage = `url(${url})`;

  return board;
}

const onBoardUpdate = (board) => {
  updatePlayerHUD(board);
  updateNextCardHUD(board);
  console.log(board);
};

document
  .getElementById('new-game-button')
  .addEventListener('click', () => toggle(document.getElementById('new-game-dialogue')));

document
  .getElementById('join-game-button')
  .addEventListener('click', () => toggle(document.getElementById('join-game-dialogue')));

document
  .getElementById('new-game-submit')
  .addEventListener('click', () => {
    const game = document.getElementById('new-game-id').value;
    const player = document.getElementById('new-game-player').value;

    tigerzone
      .new_game(game)
      .catch(data => console.log(data))

      .then(() => tigerzone.join_game(game, player))
      .catch(data => console.log(data))

      .then(() => tigerzone.watch_game(game, onBoardUpdate, data => console.log(data)))
      .catch(data => console.log(data))

      .then(() => toggle(document.getElementById('start-screen')))
      .then(() => toggle(document.getElementById('game-screen')));
  });

document
  .getElementById('join-game-submit')
  .addEventListener('click', () => {
    const game = document.getElementById('join-game-id').value;
    const player = document.getElementById('join-game-player').value;

    tigerzone
      .join_game(game, player)
      .catch(data => console.log(data))

      .then(() => tigerzone.watch_game(game, onBoardUpdate, (data) => console.log(data)))
      .catch(data => console.log(data))

      .then(() => toggle(document.getElementById('start-screen')))
      .then(() => toggle(document.getElementById('game-screen')));
  });
