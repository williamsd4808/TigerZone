function selectRandom(items) {
  return items[Math.floor(Math.random()*items.length)];
}

module.exports = {
  'queryTile': function(tile, moves) {
    const location = selectRandom(moves);

    const orientation = selectRandom(location.orientation);

    return {
      x: location.x,
      y: location.y,
      orientation: orientation
    };
  }
};
