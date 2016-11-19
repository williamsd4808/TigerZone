const process = require('process');

//This needs to to be able to send valid meeple placements to the client
//Dependent on meeple implementation as well. Will probably have to flood-fill each time this is invoked
//for each feature type unless flood-filled features are stored as full entities eventually

//Should be invoked right after the tile is placed. This means after the game-server board is updated. We
//must be ssending back to the game server our choice of tile placement, and that choice will need to be stored
//to the end that it can be used to determine possible meeple placement and send us back our list of options

//The current sample datastore isn't recording the actual subgrid of the tiles. We also don't know if meeples 
//will even be placed on the sub-grids of tiles. 

const board = require('./sample-datastore.json');

