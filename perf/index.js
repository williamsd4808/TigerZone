const TigerZone = require('./../electron-client/modules/api-wrapper');
const tigerzone = new TigerZone({ location: 'server/ApiEndpoints' });
const exec = require('child_process').exec;
const execFile = require('child_process').execFile;
const fs = require('fs');


console.time('tigerzone-exec');
  tigerzone
    .new_game('perf-test')
    .then(data => console.timeEnd('tigerzone-exec'));



console.time('tigerzone-exec-bash');
  exec(`bash server/ApiEndpoints/new-game perf-test`, (error, stdout, stderr) => {
    if (error) {
      console.error(`exec error: ${error}`);
      return;
    }

    console.timeEnd('tigerzone-exec-bash');
  });



console.time('tigerzone-exec-java');
exec(`java -classpath "lib/javax.json-1.0.jar:server" ApiEndpoints.NewGame perf-test`, (error, stdout, stderr) => {
  if (error) {
    console.error(`exec error: ${error}`);
    return;
  }
  console.timeEnd('tigerzone-exec-java');
});



console.time('tigerzone-exec-elixir');
exec(`ex_server/ex_server new-game perf-test`, (error, stdout, stderr) => {
  if (error) {
    console.error(`exec error: ${error}`);
    return;
  }
  console.timeEnd('tigerzone-exec-elixir');
});



console.time('hdd read/write');
let board = require(`./../server/ApiEndpoints/SavedGames/perf-test.json`);
board.Deck.tiles = [];
const json = JSON.stringify(board);
fs.writeFile(`server/ApiEndpoints/SavedGames/perf-test.json`, json, 'utf8', (d) => console.timeEnd('hdd read/write'));



// console.time('Java interface');
// const java = require('java');
// const System = java.import('java.lang.System');
// System.out.println('Hello World');
// console.timeEnd('Java interface');
