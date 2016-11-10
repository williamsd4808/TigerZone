Example usage

```javascript
const tigerzone = new TigerZone({ location: 'mock-server' });

tigerzone
  .new_game()
  .then(() => tigerzone.draw_card())
  .then((card) => [card, tigerzone.get_moves(card)])
  .then(([card, locations]) => [card, locations[0]])
  .then(([card, location]) => tigerzone.place_card(card, location))
  .then((board) => tigerzone.place_meeple(board, { x: 1, y: 1 }))
  .then((data) => console.log(data))
  .catch((data) => console.log(data));
```
