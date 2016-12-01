import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Main {
	public enum Token {
		CONNECTED, AUTH, WAIT, 
	}
	public static void main(String[] args) throws IOException {
		if(args.length == 0) {
			System.out.println("<address> <port> <tournypass> <user> <pass>");
			return;
		}
		String address = args[0];
		int foo = Integer.parseInt("1234");
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
		String input, fromServer;
		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));			
		//Scanner scan = new Scanner(System.in);	
		try {
			fromServer = in.readLine();
			if(fromServer.startsWith("THIS")) {
				System.out.println("Connected");
			}
			while(true) {

				fromServer = in.readLine();
				if(fromServer.startsWith("HELLO!")) {
				}//do nothing
				else if(fromServer.startsWith("WELCOME")) {
					break;
				}
				else if(fromServer.startsWith("NEW")) {
					break;
				}
				else if(fromServer.startsWith("BEGIN")) {
					break;
				}
				else if(fromServer.startsWith("YOUR")) {
					break;
				}
				else if(fromServer.startsWith("STARTING")) {
					break;
				}
				else if(fromServer.startsWith("THE")) {
					break;
				}
				else if(fromServer.startsWith("MAKE")) {
					break;
				}
				else if(fromServer.startsWith("GAME")) {
					break;
				}									
				else if(fromServer.startsWith("END")) {
					break;
				}								
				else if(fromServer.startsWith("END")) {
					break;
				}									
				else if(fromServer.startsWith("END")) {
					break;
				}								
				else if(fromServer.startsWith("PLEASE")) {
					break;
				}											
				else if(fromServer.startsWith("THANK")) {
					break;
				}
				else {
					System.out.println("Invalid Buffer Data from Server or perhaps crash?");
					return;
				}
				//BASED OFF TOKEN DO SOMETHING

				
			}
			out.println("Bye.");
		}
		finally {
			in.close();			
			out.close();
			// buffer.close();
			socket.close();
		}
	}
}