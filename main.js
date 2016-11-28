// TCP connection
const net = require('net');

// argv -- App params
const argv = require('process').argv;

// Allow us to talk to the TCP server without handling raw responses
const TCPAdapter = require('./tcp-adapter');

// API wrapper for the actual TZ game
const TigerZone = require('./electron-client/modules/api-wrapper');
const tigerzone = new TigerZone({ location: 'server/ApiEndpoints' });

// Hook into the random AI
const AI = require('./random-ai');

// Open a connection to the TCP server
const client = new net.Socket();
client.setEncoding('utf-8');

// Initialize the communication layer
const tcpAdapter = new TCPAdapter(client);

// Load in the command line args
const ip = argv[2];
const port = parseInt(argv[3], 10);
const tournament = argv[4];
const username = argv[5];
const password = argv[6];

// This is a cheaty method that allows us to force-set the deck
const setDeck = require('./modules/set-deck.js');

// Connect to the TCP server
client.connect(port, ip, function() {
	console.log('Connected');
});

client.on('error', err => console.log(err));

// This will get called every time we receive a response (res) from the TCP server
client.on('data', (res) => {
  // Parse the server response
  const message = TCPAdapter.parse(res);

  // We need to handle each possible response from the server
  switch (message.type) {
    case 'connected':
      // Tell the TCP server that we want to join the tournament
      tcpAdapter.join(tournament);
      break;
    case 'greeting':
      // Authenticate with the TCP server
      tcpAdapter.authenticate(username, password);
      break;
    case 'waiting':
      console.log('Waiting for other player to join...');
      break;
    case 'new-challenge':
      console.log(`We will be playing ${message.rounds} rounds in challenge ${message.cid}`);
      break;
    case 'begin-round':
			// Bug: joining a game does not work
      tigerzone
        .new_game('A')
        .catch(data => console.log(data))
        .then(() => tigerzone.join_game('A', username))
        .catch(data => console.log(data));

      tigerzone
        .new_game('B')
        .catch(data => console.log(data))
        .then(() => tigerzone.join_game('B', username));

      console.log(`Beginning round ${message.rid}/${message.rounds}`);
      break;
    case 'opponent':
      tigerzone
        .join_game('A', message.pid);

      tigerzone
        .join_game('B', message.pid);
      console.log(`${message.pid} has joined`);
      break;
    case 'starting-tile':
      // Do nothing?
      break;
    case 'remaining-tiles':
      // Load deck
      const deck = message.tiles;

      setDeck('A', deck);
      setDeck('B', deck);
      break;
    case 'planning':
      // Do nothing?
      break;
    case 'make-move':
      // Query AI and place tile
      tigerzone
        .get_moves(message.gid, message.tile)
        .then((moves) => {
          console.log(moves);

          if (moves.length === 0) {
            return tcpAdapter.unplaceable_tile(message.gid, message.tile);
          }

          const { x, y, orientation } = AI.queryTile(message.tile, moves);
          console.log(x, y, orientation, message.gid);
          tcpAdapter.place_tile(message.gid, message.move_count, message.tile, x, y, orientation);

          return tigerzone
            .place_tile(message.gid, message.tile, x, y, orientation);
        })
        .then((data) => console.log(data))
        .catch((data) => data);
      break;
    case 'move':
      // Add move on behalf of opponent
      if (message.move.type !== 'place-tile' || message.pid === username) {
        break;
      }

      tigerzone
        .place_tile(message.gid, message.move.tile, message.move.x, message.move.y, message.move.orientation)
        .catch((data) => data);
      break;
    case 'forfeit':
      // Do nothing?
      break;
    case 'game-over':
      // Do nothing?
      break;
    case 'round-end':
      // Do nothing?
      break;
    case 'challenge-end':
      // Do nothing?
      break;
    case 'next-challenge':
      // Do nothing?
      break;
    case 'thank-you':
      // Do nothing?
      break;
  }
});

client.on('close', function() {
	console.log('Connection closed');
});
