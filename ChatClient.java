import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {

	// Socket variable for establishing a connection to the server
	private Socket server;
	private Thread t;

	// Constructor that takes port and IP address of the server to connect to
	public ChatClient(String address, int port) {
		// Tries to set up connection to server and declare a thread for reading server input
		try {
			t = new ClientRead(address, port);
			server = new Socket(address, port);
		} catch (UnknownHostException e) {
			System.out.println("Could not connect to server, please check server details.");
		} catch (IOException e) {
			System.out.println("Could not connect to server, please check server details.");
		}
	}

	public void go() {
		try {
			// Set up a buffered reader to take user input from the console
			BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
			// Printwriter set up to send to server
			PrintWriter serverOut = new PrintWriter(server.getOutputStream(), true);

			// start thread for reading output from server and printing it to console
			t.start();

			while (true) { // while loop to take user input from console and send to server
				String userInput = userIn.readLine();
				serverOut.println(userInput);
			}
		} catch (IOException e) {
			System.out.println("IO error caused by server connection or console input");
			e.printStackTrace();
		} finally {
			try {
				server.close();
			} catch (IOException e) {
				System.out.println("Could not close connection to server");
			} catch (NullPointerException e) {
				System.out.println("Could not close connection to server, the connection may already be closed");
			}
		}
	}

	public static void main(String[] args) {
		// variables for port and address with default values
		int port = 14001;
		String addr = "localhost";
		// loop through arguments to find valid arguments
		for (int i = 0; i < args.length - 1; i++) {
			// if statement to check for a port argument
			if (args[i].equals("-ccp")) {
				try {
					port = Integer.parseInt(args[i + 1]);
				} catch (NumberFormatException e) {
					System.out.println("Invalid port");
				}
			}
			// if statement to check for an address argument
			else if (args[i].equals("-cca")) {
				addr = args[i + 1];
			} else {
				System.out.println("Invalid argument");
			}
		}
		// Instantiates chat server with parameters gathered above or defaults
		new ChatClient(addr, port).go();
	}
}
