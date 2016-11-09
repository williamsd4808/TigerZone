package GameState;

import java.util.List;

public class Deck {
	private List<Tile> deck;

	public Deck(List<Tile> deck) {
		this.deck = deck;
	}

	public Tile drawTile() {
		if(this.deck.isEmpty()) {
			return null;
		}
		return this.deck.remove(0);
	}
}