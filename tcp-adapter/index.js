module.exports = class TCPAdapter {
  constructor (client) {
    this.client = client;
  }

  send(message) {
    console.log(`[sent]: ${message}`);
    this.client.write(`${message}\r\n`);
  }

  join(tournament) {
    this.send(`JOIN ${tournament}`);
  }

  authenticate(username, password) {
    this.send(`I AM ${username} ${password}`);
  }

  place_tile(gid, move_count, tile, x, y, orientation) {
    this.send(`GAME ${gid} MOVE ${move_count} PLACE ${tile} AT ${x} ${y} ${(orientation * 90)} NONE`);
  }

  place_tiger(gid, move_count, tile, x, y, orientation, tigerzone) {
    this.send(`GAME ${gid} MOVE ${move_count} PLACE ${tile} AT ${x} ${y} ${(orientation * 90)} TIGER ${tigerzone}`);
  }

  unplaceable_tile(gid, tile) {
    this.send(`GAME ${gid} TILE ${tile} UNPLACEABLE PASS`);
  }

  static parse(message) {
    const tokens = message.split(' ');

    // THIS IS SPARTA!
    if (tokens[0] === 'THIS') {
      return { 'type': 'connected' };
    }

    // HELLO!
    if (tokens[0].trim() === 'HELLO!') {
      return { 'type': 'greeting' };
    }

    // WELCOME <pid> PLEASE WAIT FOR THE NEXT CHALLENGE
    if (tokens[0] === 'WELCOME') {
      return {
        'type': 'waiting',
        'pid': tokens[1]
      };
    }

    // NEW CHALLENGE <cid> YOU WILL PLAY <rounds> MATCH
    if (tokens[0] === 'NEW') {
      return {
        'type': 'new-challenge',
        'cid': parseInt(tokens[2], 10),
        'rounds': parseInt(tokens[6], 10)
      };
    }

    // BEGIN ROUND <rid> OF <rounds>
    if (tokens[0] === 'BEGIN') {
      return {
        'type': 'begin-round',
        'rid': parseInt(tokens[2], 10),
        'rounds': parseInt(tokens[4], 10)
      };
    }

    // YOUR OPPONENT IS PLAYER <pid>
    if (tokens[0] === 'YOUR') {
      if (tokens[4].includes('\r\n')) {
        tokens[4] = tokens[4].split('\r\n')[0];
      }

      return {
        'type': 'opponent',
        'pid': tokens[4].trim()
      };
    }

    // STARTING TILE IS <tile> AT <x> <y> <orientation>
    if (tokens[0] === 'STARTING') {
      return {
        'type': 'starting-tile',
        'tile': tokens[3],
        'x': parseInt(tokens[5], 10),
        'y': parseInt(tokens[6], 10),
        'orientation': (4 - parseInt(tokens[7], 10) / 90) % 4
      };
    }

    // THE REMAINING <number_tiles> TILES ARE [ <tiles> ]
    // THE REMAINING 6 TILES ARE [ TLTTP LJTJ- JLJL- JJTJX JLTTB TLLT- ]
    if (tokens[0] === 'THE') {
      return {
        'type': 'remaining-tiles',
        'number_tiles': parseInt(tokens[2], 10),
        'tiles': tokens.slice(6, -1)
      };
    }

    // MATCH BEGINS IN <planning_time> SECONDS
    if (tokens[0] === 'MATCH') {
      return {
        'type': 'planning',
        'planning_time': parseInt(tokens[3], 10)
      };
    }

    // MAKE YOUR MOVE IN GAME <gid> WITHIN <move_time> SECONDS: MOVE <move_count> PLACE <tile>
    if (tokens[0] === 'MAKE') {
      return {
        'type': 'make-move',
        'gid': tokens[5],
        'move_time': parseInt(tokens[7], 10),
        'move_count': parseInt(tokens[10], 10),
        'tile': tokens[12].trim()
      };
    }

    // GAME <gid> MOVE <move_count> PLAYER <pid> <move>
    if (tokens[0] === 'GAME' && !tokens.includes('FORFEITED') && !tokens.includes('OVER')) {
      return {
        'type': 'move',
        'gid': tokens[1],
        'move_count': parseInt(tokens[3], 10),
        'pid': tokens[5],
        'move': this.parse_move(tokens.slice(6))
      };
    }

    // GAME <gid> MOVE <move_count> PLAYER <pid> FORFEITED: INVALID MEEPLE PLACEMENT
    if (tokens[0] === 'GAME' && tokens.includes('FORFEITED') && !tokens.includes('OVER')) {
      return {
        'type': 'forfeit',
        'gid': tokens[1],
        'move_count': parseInt(tokens[3], 10),
        'pid': tokens[5],
        'reason': tokens.slice(7).join(' ').toLowerCase()
      };
    }

    // GAME <gid> OVER PLAYER <pid> <score> PLAYER <pid> <score>
    if (tokens[0] === 'GAME' && !tokens.includes('FORFEITED') && tokens.includes('OVER')) {
      return {
        'type': 'game-over',
        'gid': tokens[1],
        'players': [
          {
            'pid': tokens[4],
            'score': parseInt(tokens[5], 10)
          },
          {
            'pid': tokens[7],
            'score': parseInt(tokens[8], 10)
          }
        ]
      };
    }

    // END OF ROUND <rid> OF <rounds>
    if (tokens[0] === 'END' && tokens[2] === 'ROUND') {
      return {
        'type': 'round-end',
        'rid': parseInt(tokens[3], 10),
        'rounds': parseInt(tokens[5], 10)
      };
    }

    // END OF CHALLENGES
    if (tokens[0] === 'END' && tokens[2] === 'CHALLENGES') {
      return {
        'type': 'challenge-end'
      };
    }

    // PLEASE WAIT FOR THE NEXT CHALLENGE TO BEGIN
    if (tokens[0] === 'PLEASE') {
      return {
        'type': 'next-challenge'
      };
    }

    // THANK YOU FOR PLAYING! GOODBYE
    if (tokens[0] === 'THANK') {
      return {
        'type': 'thank-you'
      };
    }
  }

  static parse_move(tokens) {
    // PLACED <tile> AT <x> <y> <orientation> NONE
    if (tokens[0] === 'PLACED') {
      let meeple = 'none';

      if (tokens[6] === 'TIGER') {
        meeple = parseInt(tokens[7], 10);
      }

      return {
        'type': 'place-tile',
        'tile': tokens[1],
        'x': parseInt(tokens[3], 10),
        'y': parseInt(tokens[4], 10),
        'orientation': (4 - parseInt(tokens[5], 10) / 90) % 4,
        'meeple': meeple
      };
    }

    // TILE <tile> UNPLACEABLE PASSED
    if (tokens[0] === 'PLACED') {
      return {
        'type': 'unplaced-tile',
        'tile': tokens[1]
      };
    }

    return {
      'type': 'unknown'
    };
  }
}
