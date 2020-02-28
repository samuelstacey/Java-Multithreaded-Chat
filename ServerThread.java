import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

//Class extends thread so that multithreaded functionality can be used
public class ServerThread extends Thread {

	private Socket socket;
	private ChatServer server;
	PrintWriter clientOut;

	// constructor to initialise socket and server in this class
	public ServerThread(Socket clientSocket, ChatServer chatServer) {
		socket = clientSocket;
		this.server = chatServer;
	}

	public void run() {
		try {
			// Input stream reader reads the data coming from the client
			InputStreamReader inpReader = new InputStreamReader(socket.getInputStream());
			// Used to put data input from client into a readable format
			BufferedReader clientIn = new BufferedReader(inpReader);
			// Used to write output data to the client
			clientOut = new PrintWriter(socket.getOutputStream(), true);

			// While loop used to take any input from the client
			while (true) {
				String userInput = clientIn.readLine();
				// The input is sent to all clients via the ChatServer class
				server.sendToAll(userInput);
			}
		} catch (IOException e1) {
			// Server output if client disconnects
			System.out.println("Client Disconnected");
		}
	}

	public void printString(String toPrint) {
		// Prints string when called by ChatServer class
		clientOut.println(toPrint);
		// used to force to print
		clientOut.flush();
	}

}
