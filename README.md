# TigerZone
CEN3031 Project


## Electron client

The client now allows you to add tiles and view possible moves. It does not currently follow game rules.

![Demo pic](http://i.imgur.com/z9qSHDM.png)

## Java Server

### Building:

```
$ cd server/ApiEndpoints
server/ApiEndpoints/$ sh build.sh
```

### Running endpoints


**Creating a new game**
```
$ ./server/ApiEndpoints/new-game

> {"id":"x6pxgn3dm1","players":[],"turn":0,"board":[{"x":0,"y":0,"orientation":"NORTH","name":"Single bubble city with straight road"}]}
```

**Joining a game**
```
$ ./server/ApiEndpoints/join-game game_id name

> {"id":"x6pxgn3dm1","players":["James"],"turn":0,"board":[{"x":0,"y":0,"orientation":"NORTH","name":"Single bubble city with straight road"}]}
```
