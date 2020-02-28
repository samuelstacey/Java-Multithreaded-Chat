import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {

	private ServerSocket in;
	// Array list to store the threads so I can print to them and kill them
	private ArrayList<Thread> threadList = new ArrayList<Thread>();
	private Thread t;

	public ChatServer(int port) {
		try {
			// Serversocket initialised in this constructor
			in = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Server socket could not be created, the port may be reserved");

		}
	}

	/*
	 * Method to loop through all threads and print a string to client consoles
	 * synchronised to prevent interruptions that call the same method
	 */
	public synchronized void sendToAll(String toSend) {
		for (Thread t : threadList) {
			((ServerThread) t).printString(toSend);
		}
	}

	/*
	 * Method to close server socket and kill all threads ready for program
	 * termination synchronised to prevent errors when socket is closed but threads
	 * are not terminated yet
	 */
	public synchronized void killThreads() {
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Could not close socket, the socket may already be closed");
		}
		for (Thread t : threadList) {
			t.interrupt();
		}
	}

	public void go() {
		try {
			System.out.println("Server listening");
			Socket s;
			// New thread started to check for server console input
			Thread t1 = new ServerInput(this);
			t1.start();
			while (true) {
				// New input is accepted
				s = in.accept();
				// When a new client connects it is given a thread
				t = new ServerThread(s, this);
				threadList.add(t); // Client thread added to thread list
				t.start();// Starts the thread
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("There was an issue accepting a connection to the server.");

		}
	}

	public static void main(String[] args) {
		int port = 14001; // default port initialised
		// Loop to find argument for port
		for (int i = 0; i < args.length - 1; i++) {
			if (args[i].equals("-ccp")) {
				try {
					// If a port is found then the port to be used is changed to that one
					port = Integer.parseInt(args[i + 1]);
				} catch (NumberFormatException e) {
					System.out.println("Invalid port");
				}
			} else {
				// outputted if an argument other than '-ccp' is entered
				System.out.println("Invalid argument");
			}
		}
		// Server started with port from above
		ChatServer server = new ChatServer(port);
		server.go();
	}
}
