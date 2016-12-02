if [ "$(expr substr $(uname -s) 1 10)" == "MINGW64_NT" ]; then
  javac -cp ".;../../lib/javax.json-1.0.jar" -sourcepath ".;../" NewGame.java
  javac -cp ".;../../lib/javax.json-1.0.jar" -sourcepath ".;../" JoinGame.java
  javac -cp ".;../../lib/javax.json-1.0.jar" -sourcepath ".;../" GetMoves.java
  javac -cp ".;../../lib/javax.json-1.0.jar" -sourcepath ".;../" PlaceTile.java
  javac -cp ".;../../lib/javax.json-1.0.jar" -sourcepath ".;../" Start.java
  javac -cp ".;../../lib/javax.json-1.0.jar" -sourcepath ".;../" Client.java  
else
  javac -cp ".:../../lib/javax.json-1.0.jar" -sourcepath ".:../" NewGame.java
  javac -cp ".:../../lib/javax.json-1.0.jar" -sourcepath ".:../" JoinGame.java
  javac -cp ".:../../lib/javax.json-1.0.jar" -sourcepath ".:../" GetMoves.java
  javac -cp ".:../../lib/javax.json-1.0.jar" -sourcepath ".:../" PlaceTile.java
  javac -cp ".:../../lib/javax.json-1.0.jar" -sourcepath ".:../" Start.java
  javac -cp ".:../../lib/javax.json-1.0.jar" -sourcepath ".:../" Client.java  


  chmod +x new-game
  chmod +x join-game
  chmod +x get-moves
  chmod +x place-tile
  chmod +x watch-game
fi
