package ApiEndpoints;

import GameState.*;
import Utilities.*;

import java.io.*;
import java.net.*;

import java.awt.Point;
import java.util.*;

import static Utilities.MeepleUtilities.meepleLocations;

// import java.util.Scanner;
// import java.util.regex.Matcher;
// import java.util.regex.Pattern;

public class Client {
	public enum Token {
		// AUTH, CHALLENGE, ROUND, MATCH, MOVE, UNKNOWN;
		TPASS, AUTH, WELCOME, NEW, EOC, ROUND, EOR, OPPONENT, STILE, DECK, MOVE, GAME, GAMEOVER, UNKNOWN;
	}
	public static void main(String[] args) throws IOException {
		if(args.length == 0) {
			System.out.println("<address> <port> <tournypass> <user> <pass>");
			return;
		}
		String address = args[0];
		int port = Integer.parseInt(args[1]);
		String tpass = args[2];
		String uname = args[3];
		String upass = args[4];
		System.out.println("Attempting to connect to " + address + "@" + port);
		Socket socket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			socket = new Socket(address, port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch(IOException e) {
			System.err.println("Could not make connection");
			System.exit(1);
		}
		//Begin parsing server data
		boolean done = false;
		String input, fromServer;
		String pid1, pid2, gid;
		pid1 = "";
		pid2 = "";
		gid = "";
		int cid, rounds, rid, moveCount;
		moveCount = 0;
		cid = 0;
		rounds = 0;
		rid = 0;
		Engine game1 = new Engine();
		Engine game2 = new Engine();
		// Player player1 = new Player("ricky");
		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));			
		//Scanner scan = new Scanner(System.in);	
		try {
			while(done != true) {
				Token token = Token.UNKNOWN;				
				fromServer = in.readLine();
				System.out.println(fromServer); //UNCOMMENT THIS LINE TO SEE WHAT'S COMING FROM SERVER
				token = getToken(fromServer);
				switch(token) {
					case TPASS:
						out.println("JOIN " + tpass);
						break;
					case AUTH:
						out.println("I AM " + uname + " " + upass);
						break;
					case WELCOME:
						//GET PID
						System.out.println("CONNECTED TO SERVER!");
    					String[] sp = fromServer.split(" ");
    					pid1 = sp[1];
    					// System.out.println(pid1); //test
    					//SPIN LOCK TILL WAIT TOKEN IS RECEIVED
    					while(true) {
    						token = Token.UNKNOWN;
    						fromServer = in.readLine();
							// System.out.println(fromServer);    						
    						token = getToken(fromServer);
    						if(token == Token.NEW) {
    							String[] sp2 = fromServer.split(" ");
    							cid = Integer.parseInt(sp2[2]);
    							rounds = Integer.parseInt(sp2[6]);
    							//test parsing data stuff
    							// System.out.println(cid + " " + rounds);
    							break;
    						}
    					}
    					break;
    				case EOC:
    					//chances are 1 challenge really do nothing
    					break;
    				//INSTANTIATE TWO ENGINES (GAMES) LOAD AI AS US (PID1) LOAD OPPONENT AS PLAYER. THEIR MOVE WILL BE SENT FROM THE SERVER VIA GAME	
					case ROUND:
						String sp3[] = fromServer.split(" ");
						rid = Integer.parseInt(sp3[2]);
						//CREATE TWO NEW GAMES 
						game1.newGame(1000);
						game2.newGame(1000);		
						break;
					case EOR:
						//increment counter. if less than equal to rounds then wait
						if(rid == rounds) {
							//spinlock till next match
	    					while(true) {
	    						token = Token.UNKNOWN;
	    						fromServer = in.readLine();
								// System.out.println(fromServer);    						
	    						token = getToken(fromServer);
	    						if(token == Token.ROUND) {
									String sp4[] = fromServer.split(" ");
									rid = Integer.parseInt(sp4[2]);
	    							//test parsing data stuff
	    							// System.out.println(rid + " " + rounds);
	    							break;
	    						}
	    						else if(token == Token.EOC) {
	    							System.out.println(fromServer);
	    							break;
	    						}	    						
	    					}
	    					break;							
						}
					case OPPONENT:
						String sp5[] = fromServer.split(" ");
						pid2 = sp5[4];
						// System.out.println(fromServer);
						//add player to each game
						game1.playerJoin(pid1);
						game1.playerJoin(pid2);

						game2.playerJoin(pid2);
						game2.playerJoin(pid1);
						break;
					case STILE:
						//DO NOTHING. Board already does it on new game
						//I GUESS I CAN START THE GAME
						break;
					case DECK:
						//COPY DECK
						String sp7[] = fromServer.split(" ");
						int numOfTiles = Integer.parseInt(sp7[2]);
						// System.out.println(fromServer); //
						// Pattern pattern = Pattern.compile("[ (.*?) ]");
						// Matcher matcher = pattern.matcher(fromServer);
						// String word = "";
						// while(matcher.find()) {
						// 	word = matcher.group(1);

						// }
						String getTiles = fromServer.substring(fromServer.indexOf("[") + 2, fromServer.indexOf("]"));							
						String sp6[] = getTiles.split(" ");
					// int numOfTiles = sp6.length(); //0-8
						// for(int i = 0; i < numOfTiles; i++) {
						// 	System.out.print(sp6[i] + " ");
						// }
						// System.out.println();

						//sp6 contains string array of tiles in respective order
						break;
					case MOVE:
						//do valid move from our AI;
						//GET AVAILABLE MOVES. PICK ONE RANDOMLY AND PLACE TILE
						//MAKE YOUR MOVE IN GAME <gid> WITHIN <time move > SECOND: MOVE <#> PLACE <tile>					
						String sp8[] = fromServer.split(" ");
						gid = sp8[5];
						moveCount = Integer.parseInt(sp8[10]);
						String currTile = sp8[12];
						Tile tile = new Tile(currTile);
						int x = 0;
						int y = 0;
						int orient = 0;
						String placeMeeple = " NONE";
						if(currTile.equals("JJJJX")) {
							placeMeeple = " TIGER";
						}
						Map<Point, Collection<Board.Orientation>> points = new HashMap<>();
						if(gid.equals("1")) {
							points = BoardUtilities.getValidAdjacentPoints(new Tile(currTile), game1.board);
						}
						else if(gid.equals("2")) {
							points = BoardUtilities.getValidAdjacentPoints(new Tile(currTile), game2.board);							
						}
						Map.Entry<Point, Collection<Board.Orientation>>[] randomMoves = points.entrySet().toArray(new Map.Entry[0]);
						Random rand = new Random();

						// call repeatedly
						Map.Entry<Point, Collection<Board.Orientation>> randomMove = randomMoves[rand.nextInt(randomMoves.length)];

						Point randomPoint = randomMove.getKey();
						Collection<Board.Orientation> orientations = randomMove.getValue();
						x = randomPoint.x;
						y = randomPoint.y;
						Board.Orientation randomOrientation = getRandomOrientation(orientations);
						orient = randomOrientation.ordinal();
						//GET MOVE SET FROM GID RETURN X Y ORIENTATION AND MEEPLE PLACEMENT HERE
						if(placeMeeple.equals(" TIGER")){
							out.println("GAME " + gid + " MOVE " + moveCount + " PLACE " + currTile + " AT " + x + " " + y + " " + (orient*90) + placeMeeple + " " + 5);
							if(gid.equals("1")) {
								game1.board.addTile(randomPoint, tile, Board.Orientation.values()[orient]);
							}
							else if(gid.equals("2")) {
								game2.board.addTile(randomPoint, tile, Board.Orientation.values()[orient]);
								
							}							
							System.out.println("GAME " + gid + " MOVE " + moveCount + " PLACE " + currTile + " AT " + x + " " + y + " " + (orient*90) + placeMeeple + " " + 5);							
						}
						else {
							out.println("GAME " + gid + " MOVE " + moveCount + " PLACE " + currTile + " AT " + x + " " + y + " " + (orient*90) + placeMeeple);
							if(gid.equals("1")) {
								game1.board.addTile(randomPoint, tile, Board.Orientation.values()[orient]);
							}
							else if(gid.equals("2")) {
								game2.board.addTile(randomPoint, tile, Board.Orientation.values()[orient]);
							}
							System.out.println("GAME " + gid + " MOVE " + moveCount + " PLACE " + currTile + " AT " + x + " " + y + " " + (orient*90)  + placeMeeple);							
						}
						break;
					case GAME:
						//translate game data;
						//we dont really care about us so only do something
						//when PID != US
						String sp9[] = fromServer.split(" ");
						gid = sp9[1];
						String type = sp9[2];
						if(type.equals("OVER")) {
							break;
						}
						moveCount = Integer.parseInt(sp9[3]);
						String who = sp9[5];
						String typeMove = sp9[6];
						// String whatTile = sp9[7];
						if(who.equals(pid2)) {
							if(typeMove.equals("FORFEITED:")) {
								//END GAME OTHER PLAYER
								//EITHER CLOSE GAME/ENGINE OR JUST DO NOTHING SINCE IT GETS REINSTAIATED
								String message = fromServer.substring(fromServer.indexOf(":")) + 1;
								// System.out.println(pid2 + " FORFEITED: " + message);

							}
							else if(typeMove.equals("PLACE")) {
								Tile whatTile = new Tile(sp9[7]);
								int x2 = Integer.parseInt(sp9[9]);
								int y2 = Integer.parseInt(sp9[10]);
								int orient2 = Integer.parseInt(sp9[11])/90;								
								Point newPoint = new Point(x2, y2);
								if(gid.equals("1")) {
									game1.board.addTile(newPoint, whatTile, Board.Orientation.values()[orient2]);
								}
								else if(gid.equals("2")) {
									game2.board.addTile(newPoint, whatTile, Board.Orientation.values()[orient2]);
								}								
							}
							else if(typeMove.equals("TILE")) {
								//CASE WHERE UNPLACABLE OR GET SOMETHING
								//DO NOTHING I SUPPOSE
							}	
						}
						if(who.equals(pid1)) {
							//ALL WE CARE IS IF WE FORFEIT AND WHY
							if(typeMove.equals("FORFEITED:")) {
								String message2 = fromServer.substring(fromServer.indexOf(":")) + 1;
								// System.out.println(pid1 + " FORFEITED: " + message2);
							}
						}
						break;
					case GAMEOVER:
						//EXITS CLIENT WHEN SERVER TERMINATES
						done = true;
						break;
				}	
			}
		}
		finally {
			in.close();			
			out.close();
			// buffer.close();
			socket.close();
		}
	}
	public static Token getToken(String fromServer) {
		
		if(fromServer.startsWith("THIS")) {
			return Token.TPASS;
		}
		else if(fromServer.startsWith("HELLO!")) {
			return Token.AUTH;
		}
		else if(fromServer.startsWith("WELCOME")) {
			return Token.WELCOME;
		}
		else if(fromServer.startsWith("NEW")) {
			return Token.NEW;
		}
		else if(fromServer.startsWith("BEGIN")) {
			return Token.ROUND;
		}
		else if(fromServer.startsWith("YOUR")) {
			return Token.OPPONENT;
		}
		else if(fromServer.startsWith("STARTING")) {
			return Token.STILE;
		}
		else if(fromServer.startsWith("THE")) {
			return Token.DECK;
		}
		else if(fromServer.startsWith("MAKE")) {
			return Token.MOVE;
		}
		else if(fromServer.startsWith("GAME")) {
			return Token.GAME;
		}									
		else if(fromServer.startsWith("END OF ROUND")) {
			return Token.EOR;
		}											
		else if(fromServer.startsWith("END OF CHALLENGES")) {
			return Token.EOC;
		}							
		else if(fromServer.startsWith("THANK")) {
			return Token.GAMEOVER;
		}
		else {
			return Token.UNKNOWN;
		}		
	}
	public static <T> T getRandomOrientation(Collection<T> coll) {
    	int num = (int) (Math.random() * coll.size());
    	for(T t: coll) if (--num < 0) return t;
    	throw new AssertionError();
	}
}