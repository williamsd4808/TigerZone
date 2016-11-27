if [ "$(expr substr $(uname -s) 1 10)" == "MINGW64_NT" ]; then
  javac -cp ".;../../lib/javax.json-1.0.jar" -sourcepath ".;../" Feature.java
  javac -cp ".;../../lib/javax.json-1.0.jar" -sourcepath ".;../" Meeple.java
  javac -cp ".;../../lib/javax.json-1.0.jar" -sourcepath ".;../" Tile.java
  javac -cp ".;../../lib/javax.json-1.0.jar" -sourcepath ".;../Utilities/" -sourcepath ".:../" Deck.java
  javac -cp ".;../../lib/javax.json-1.0.jar" -sourcepath ".;../" Player.java
  javac -cp ".;../../lib/javax.json-1.0.jar" -sourcepath ".;../" Board.java
  javac -cp ".;../../lib/javax.json-1.0.jar" -sourcepath ".;../" Engine.java
else
  javac -cp ".:../../lib/javax.json-1.0.jar" -sourcepath ".:../" Feature.java
  javac -cp ".:../../lib/javax.json-1.0.jar" -sourcepath ".:../" Meeple.java
  javac -cp ".:../../lib/javax.json-1.0.jar" -sourcepath ".:../Utilities/" -sourcepath ".:../" Deck.java
  javac -cp ".:../../lib/javax.json-1.0.jar" -sourcepath ".:../" Player.java
  javac -cp ".:../../lib/javax.json-1.0.jar" -sourcepath ".:../" Tile.java
  javac -cp ".:../../lib/javax.json-1.0.jar" -sourcepath ".:../" Board.java
  javac -cp ".:../../lib/javax.json-1.0.jar" -sourcepath ".:../" Engine.java

  chmod +x new-game
  chmod +x join-game
  chmod +x get-moves
  chmod +x place-tile
  chmod +x watch-game
fi
