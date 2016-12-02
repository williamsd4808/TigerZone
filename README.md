# TigerZone

## The Team

### Team Q

#### James Steele

https://github.com/Zubry

#### Austin Seber

https://github.com/aseber

#### Ricky Tran

https://github.com/rickydtran

#### Jay Wang

https://github.com/jw6

#### Patrick Bradley

#### Daniel Williams

https://github.com/williamsd4808

## Project structure

We decided to use client-server architecture, writing the gen server in Java, a GUI client written in javascript with Electron, a TCP adapter written in Node.js, and an AI also written in Node.js. Directories of interest in this project are `electron-client`, `server`, `tcp-adapter`, and `random-ai`.

This project should be runnable on Ubuntu 14.04 with node v6.3.1 and OpenJDK 8. There are no guarantees that any other system will work with other configurations, especially those running on Windows.

## Electron client

To run the Electron client, make sure that you have node installed:

```shell
TigerZone$ node -v
v6.3.1
```

If you get a response saying that node isn't installed, go install it.

Next, install electron-prebuilt globally

```shell
TigerZone$ npm install -g electron-prebuilt
```

You should be able to run the Electron client now without any issues since all dependencies are packaged with it.

```shell
Tigerzone$ electron electron-client
```

Now, you can create, join, and load games from the datastore. Remember to scroll down about halfway to view the game board. The client will update every time the datastore updates and automatically display valid moves. You can place and rotate tiles.

## Java Gen (Generic) Server

### Compilation

In order to compile the gen server, switch to the server/ApiEndpoints directory and run the build script, i.e.

```shell
TigerZone$ cd server/ApiEndpoints
ApiEndpoints$ bash build.sh
ApiEndpoints$ cd ../../
```
### Endpoints

We expose 4 endpoints in order to interact with the gen server. As Java can be a little verbose, we provide bash scripts for each endpoint for your convenience.

#### new-game

```shell
TigerZone$ bash server/ApiEndpoints/new-game game_id
```

#### join-game

```shell
TigerZone$ bash server/ApiEndpoints/join-game game_id player_id
```
```

#### get-moves

```shell
TigerZone$ bash server/ApiEndpoints/get-moves game_id
```

#### place-tile

```shell
TigerZone$ bash server/ApiEndpoints/place-tile game_id tile_id x y orientation
```

#### watch-game

Watch game allows clients to observe the game, and is written in Node.

```shell
TigerZone$ ./server/ApiEndpoints/watch-game game_id
```

## TCP adapter

To run the adapter, just run

```shell
TigerZone$ node main ip port tournament_password user password
```

We provide a test server, which runs on 127.0.0.1:9000. To use it, just send the server responses to stdin and it will echo them to the client.

```shell
TigerZone$ node tcp-adapter/test-server
```

To prove that the adapter works, we typically just pipe examples from the protocol to the test server.

## Testing

### Performance tests

```shell
TigerZone$ node perf
```

### Client tests

```shell
TigerZone$ node electron-tests
```

## Meeples/Tigers

We scale up to a 5x5 subgrid in order to solve the problem of jungles being errantly separated each other by a lake. We also introduce a "hole" subtile that acts as a blocker for the flood fill, allowing us to have us to have 2 jungles and 2 lakes instead of 1 jungle and 2 lakes or 2 jungles and 1 lake.

We also have to select 9 subtiles to act as our tiger tiles, and we chose those below.
![Demo pic](http://imgur.com/O4aHgMM.png)
