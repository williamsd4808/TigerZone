import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Main {
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
		String pid1, pid2;
		int cid, rounds, rid;
		cid = 0;
		rounds = 0;
		rid = 0;
		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));			
		//Scanner scan = new Scanner(System.in);	
		try {
			while(done != true) {
				Token token = Token.UNKNOWN;				
				fromServer = in.readLine();
				System.out.println(fromServer);
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
    							// System.out.println(rid + " " + rounds);
    							break;
    						}
    					}
    					break;
    				case EOC:
    					//chances are 1 challenge really do nothing
    					break;
					case ROUND:
						String sp3[] = fromServer.split(" ");
						rid = Integer.parseInt(sp3[2]);
						//CREATE TWO NEW GAMES 
						break;
					case EOR:
						//increment counter. if less than equal to rounds then wait
						rid++;
						if(rid > rounds) {
							//spinlock till next match
	    					while(true) {
	    						token = Token.UNKNOWN;
	    						fromServer = in.readLine();
								System.out.println(fromServer);    						
	    						token = getToken(fromServer);
	    						if(token == Token.ROUND) {
									String sp4[] = fromServer.split(" ");
									rid = Integer.parseInt(sp4[2]);
	    							//test parsing data stuff
	    							// System.out.println(rid + " " + rounds);
	    							break;
	    						}
	    					}
	    					break;							
						}
					case OPPONENT:
						String sp5[] = fromServer.split(" ");
						pid2 = sp5[4];
						//add player to each game
						break;
					case STILE:
						//DO NOTHING. Board already does it on new game
						break;
					case DECK:
						//COPY DECK TO THERE DECK.
						break;
					case MOVE:
						//do valid move from our AI;
						break;
					case GAME:
						//translate game data;
						break;
					case GAMEOVER:
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
		else if(fromServer.startsWith("END")) {
			return Token.EOR;
		}																		
		else if(fromServer.startsWith("THANK")) {
			return Token.GAMEOVER;
		}
		else {
			return Token.UNKNOWN;
		}		
	}
}