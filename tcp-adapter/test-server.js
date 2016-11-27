var net = require('net');
const process = require('process');

var server = net.createServer();
server.on('connection', handleConnection);

server.listen(9000, function() {
  console.log('server listening to %j', server.address());
});

function handleConnection(conn) {
  var remoteAddress = conn.remoteAddress + ':' + conn.remotePort;
  console.log('new client connection from %s', remoteAddress);

  conn.setEncoding('utf8');

  conn.on('data', onConnData);
  conn.once('close', onConnClose);
  conn.on('error', onConnError);

  process.stdin.on('readable', function() {
    const data = String(process.stdin.read());

    if (data === 'null') {
      return false;
    }

    conn.write(data);
  });

  function onConnData(d) {
    console.log('connection data from %s: %j', remoteAddress, d);
  }

  function onConnClose() {
    console.log('connection from %s closed', remoteAddress);
  }

  function onConnError(err) {
    console.log('Connection %s error: %s', remoteAddress, err.message);
  }
}
